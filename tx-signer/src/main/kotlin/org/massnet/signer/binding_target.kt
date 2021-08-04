package org.massnet.signer

import org.bitcoinj.core.Base58

/**
 * Represent a binding target address, which encodes an 20-byte pubkey hash (along with metadata) with checked base58
 */
class BindingTarget(
    val version: Int,
    val hash: ByteArray,
    val type: Int,
    val size: Int
): CommonAddress() {

    init {
        require(version == 0) { "currently only support version 0" }
        require(hash.size == 20) { "hash must be ripemd160 (20 bytes)" }
        require(type == 1 || type == 2) { "invalid type $type (0 for MASS, 1 for Chia)" }
        require(size in 20..200) { "invalid size $size (must be in [20, 200])" }
    }

    override val scriptHash: ByteArray
        get() {
            val buf = hash.copyOf(hash.size + 2)
            buf[hash.size] = type.toByte()
            buf[hash.size + 1] = size.toByte()
            return buf
        }

    override val encodedString: String
        get() {
            // base58 of [version(1 byte) hash(20 byte) type(1 byte) size(1 byte) checksum(4 byte)]
            return Base58.encodeChecked(version, scriptHash)
        }

    companion object {

        @JvmStatic
        fun fromString(address: String): BindingTarget {
            val data = Base58.decodeChecked(address)
            require(data[0] == 0.toByte()) { "version (netID) must be 0" }
            return fromScriptHash(data.copyOfRange(1, data.size))
        }

        @JvmStatic
        fun fromScriptHash(scriptHash: ByteArray): BindingTarget {
            val hash = scriptHash.copyOfRange(0, 20)
            val type = scriptHash[hash.size].toInt()
            val size = scriptHash[hash.size + 1].toInt()
            return BindingTarget(0, hash, type, size)
        }

        @JvmStatic
        fun validate(addr: String): BindingTarget? {
            return try {
                fromString(addr)
            } catch (e: Exception) {
                null
            }
        }

    }
}
