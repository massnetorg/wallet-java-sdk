package org.massnet.signer

import org.bitcoinj.core.Base58
import org.bitcoinj.core.Bech32
import org.bitcoinj.core.ECKey
import org.bitcoinj.crypto.HDKeyDerivation
import org.bitcoinj.script.Script
import org.bitcoinj.script.ScriptOpCodes
import java.nio.ByteBuffer
import java.util.*
import kotlin.experimental.and

/**
 * The interface of common addresses
 */
abstract class CommonAddress {
    abstract val scriptHash: ByteArray
    abstract val encodedString: String
    @Deprecated("This API is kept only for compatibility", ReplaceWith("encodedString"), DeprecationLevel.WARNING)
    fun encodeToString(): String = encodedString

    companion object {
        fun fromString(addr: String): CommonAddress {
            if (addr.startsWith("ms")) {
                Address.validate(addr)?.let { return it }
            }
            try {
                return BindingTarget.fromString(addr)
            } catch (e: Exception) {
                // do nothing
            }
            throw IllegalArgumentException("cannot decode address $addr with Bech32 or base58")
        }
    }
}

/**
 * Represent a segwit P2WSH address, which encodes an 32-byte script hash with Bech32
 */
class Address(
    val version: Int,
    val extVersion: Int,
    override val scriptHash: ByteArray
): CommonAddress() {

    init {
        require(version == 0) { "currently only support version 0" }
        require(extVersion == 0 || extVersion == 1) { "currently only support ext version 0 (normal) / 1 (staking)" }
        require(scriptHash.size == 32) { "script hash must be SHA256 (32 bytes)" }
    }

    override val encodedString: String
        get() {
            val encodedScript = convertBits(scriptHash.asIterable(), 8, 5, true)
            val buf = ByteArray(2 + encodedScript.size)
            buf[0] = version.toByte()
            buf[1] = extVersion.toByte()
            System.arraycopy(encodedScript, 0, buf, 2, encodedScript.size)
            return Bech32.encode("ms", buf)
        }

    @Deprecated("This API is kept only for compatibility", ReplaceWith("ScriptUtils.getP2WSHOutputScript(this)"), DeprecationLevel.WARNING)
    fun getPkScript(): ByteArray {
        return ScriptUtils.getP2WSHOutputScript(this)
    }

    val isStaking = extVersion == 1

    companion object {

        private fun convertBits(data: Iterable<Byte>, fromBits: Int, toBits: Int, pad: Boolean): ByteArray {
            var acc = 0
            var bits = 0
            val maxValue = (1 shl toBits) - 1
            val ret: MutableList<Byte> = ArrayList()
            for (value in data) {
                val b: Short = value.toShort() and 0xff
                require(b in 0 until (1 shl fromBits))
                acc = (acc shl fromBits) or b.toInt()
                bits += fromBits
                while (bits >= toBits) {
                    bits -= toBits
                    ret.add(((acc shr bits) and maxValue).toByte())
                }
            }
            if (pad && bits > 0) {
                ret.add(((acc shl (toBits - bits)) and maxValue).toByte())
            } else{
                require(bits < fromBits && ((acc shl (toBits - bits)) and maxValue).toByte() == 0.toByte())
            }
            return ret.toByteArray()
        }

        fun getRedeemScript(key: ECKey): Script {
            // OP_1 <PUBKEY> OP_1 OP_CHECKMULTISIG
            val buf = ByteBuffer.allocate(37)
            buf.put(ScriptOpCodes.OP_1.toByte())
            buf.put(33) // length
            buf.put(key.pubKey) // pubkey
            buf.put(ScriptOpCodes.OP_1.toByte())
            buf.put(ScriptOpCodes.OP_CHECKMULTISIG.toByte())
            return Script(buf.array())
        }

        @JvmStatic
        fun fromScriptHash(hash: ByteArray, isStaking: Boolean = false): Address {
            return Address(0, if (isStaking) 1 else 0, hash)
        }

        @JvmStatic
        fun fromString(addr: String): Address {
            val pos = addr.lastIndexOf('1')
            require(pos > 1)
            val prefix = addr.slice(0..pos)
            require(prefix == "ms1")
            val decoded = Bech32.decode(addr)
            require(decoded.data.isNotEmpty())
            val version = decoded.data[0].toInt()
            val extVersion = decoded.data[1].toInt()
            val scriptHash = convertBits(decoded.data.drop(2), 5, 8, false)
            return Address(version, extVersion, scriptHash)
        }

        @JvmStatic
        @Synchronized
        fun fromPubKey(pubKey: ECKey, isStaking: Boolean = false): Address {
            val script = getRedeemScript(pubKey)
            val scriptHash = Utils.sha256(script.program)
            return fromScriptHash(scriptHash, isStaking)
        }

        @JvmStatic
        fun validate(addr: String): Address? {
            return try {
                fromString(addr)
            } catch (e: Exception) {
                null
            }
        }

        @JvmStatic
        fun create(seed: ByteArray, isStaking: Boolean = false): Pair<String, String> {
            if (seed.size < 16) {
                throw IllegalArgumentException("Seed must be at least 128 bits long")
            }
            // generate master key from seed
            val masterKey = HDKeyDerivation.createMasterPrivateKey(seed)
            // derive private key using BIP44 (m/44'/297'/1'/0/)
            val intermediateKey = masterKey.derive(44).derive(297).derive(1) // hardened
            val privateKey = HDKeyDerivation.deriveChildKey(intermediateKey, 0) // non-hardened
            // generate address with the sub key
            val key = ECKey.fromPrivate(privateKey.privKeyBytes, true)
            val addr = fromPubKey(key, isStaking)
            return Pair(addr.encodeToString(), key.privateKeyAsHex)
        }
    }
}
