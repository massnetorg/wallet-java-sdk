package org.massnet.signer

import com.google.gson.*
import org.bitcoinj.script.ScriptOpCodes
import org.massnet.signer.ByteUtils.hexToBytes
import org.massnet.signer.ByteUtils.toHexString
import java.lang.reflect.Type
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.MessageDigest


object ByteUtils {
    private const val HEX_CHARS = "0123456789abcdef"
    private val HEX_CHARS_ARRAY = HEX_CHARS.toCharArray()

    @JvmStatic
    fun String.hexToBytes(): ByteArray {
        if (length % 2 == 1) throw IllegalArgumentException("Hex string must have even length.")

        val result = ByteArray(length / 2)

        val str = this.lowercase()
        for (i in 0 until length step 2) {
            val firstIndex = HEX_CHARS.indexOf(str[i])
            val secondIndex = HEX_CHARS.indexOf(str[i + 1])
            require(!(firstIndex == -1 || secondIndex == -1))
            val octet = (firstIndex shl 4) or secondIndex
            result[i shr 1] = octet.toByte()
        }

        return result
    }

    @JvmStatic
    fun ByteArray.toHexString(): String {
        val result = StringBuffer()
        forEach {
            result.append(it.toHexString())
        }
        return result.toString()
    }

    @JvmStatic
    fun Byte.toHexString(): String {
        val octet = this.toInt()
        val firstIndex = (octet and 0xF0) ushr 4
        val secondIndex = octet and 0x0F
        return "${HEX_CHARS_ARRAY[firstIndex]}${HEX_CHARS_ARRAY[secondIndex]}"
    }

    @JvmStatic
    fun String.toProtoHash(): Proto.Hash {
        require(this.length == 64)
        val builder = Proto.Hash.newBuilder()
        builder.s0 = java.lang.Long.parseUnsignedLong(this.substring(0,  16), 16)
        builder.s1 = java.lang.Long.parseUnsignedLong(this.substring(16, 32), 16)
        builder.s2 = java.lang.Long.parseUnsignedLong(this.substring(32, 48), 16)
        builder.s3 = java.lang.Long.parseUnsignedLong(this.substring(48, 64), 16)
        return builder.build()
    }

    @JvmStatic
    fun ByteArray.toProtoHash(): Proto.Hash {
        return this.toHexString().toProtoHash()
    }
}

fun Proto.Hash.toBytes(): ByteArray {
    val arr = ByteBuffer.allocate(32).order(ByteOrder.BIG_ENDIAN)
    arr.putLong(this.s0)
    arr.putLong(this.s1)
    arr.putLong(this.s2)
    arr.putLong(this.s3)
    return arr.array()
}


object Utils {

    internal fun sha256(b: ByteArray): ByteArray {
        return MessageDigest.getInstance("SHA-256").digest(b)
    }

    private class ByteArrayTypeAdapter : JsonSerializer<ByteArray>, JsonDeserializer<ByteArray> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ByteArray {
            return json.asString.hexToBytes()
        }

        override fun serialize(src: ByteArray, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
            return JsonPrimitive(src.toHexString())
        }
    }

    @JvmStatic
    val GSON by lazy {
        GsonBuilder()
            .setPrettyPrinting()
            .registerTypeHierarchyAdapter(ByteArray::class.java, ByteArrayTypeAdapter())
            .create()
    }
}


object ScriptUtils {
    @JvmStatic
    fun getP2WSHOutputScript(address: Address): ByteArray {
        // OP_0 <REDEEM_SCRIPT_HASH>
        require(!address.isStaking)
        val buf = ByteBuffer.allocate(34)
        buf.put(ScriptOpCodes.OP_0.toByte())
        buf.put(32) // length
        buf.put(address.scriptHash) // script hash
        return buf.array()
    }

    @JvmStatic
    fun getP2SWSHOutputScript(address: Address): ByteArray {
        // OP_0 <REDEEM_SCRIPT_HASH> <FROZEN_PERIOD>
        require(address.isStaking)
        TODO("not implemented")
    }

    @JvmStatic
    fun getP2BWSHOutputScript(holder: Address, target: BindingTarget): ByteArray {
        // OP_0 <REDEEM_SCRIPT_HASH> <BINDING_TARGET_HASH>
        require(!holder.isStaking) { "holder cannot be a staking address" }
        val targetScript = target.scriptHash
        val buf = ByteBuffer.allocate(1 + 1 + 32 + 1 + targetScript.size)
        buf.put(ScriptOpCodes.OP_0.toByte())
        buf.put(32) // length
        buf.put(holder.scriptHash) // script hash
        assert(targetScript.size == 22)
        buf.put(targetScript.size.toByte()) // length
        buf.put(targetScript) // script hash
        return buf.array()
    }

    @JvmStatic
    fun decodeOutputScript(scriptHash: ByteArray, isStaking: Boolean = false): Pair<Address, BindingTarget?> {
        return if (scriptHash.size == 34) {
            val segwitScriptHash = scriptHash.copyOfRange(2, 34)
            Pair(Address.fromScriptHash(segwitScriptHash, isStaking), null)
        } else if (scriptHash.size == 57) {
            val segwitScriptHash = scriptHash.copyOfRange(2, 34)
            val bindingScriptHash = scriptHash.copyOfRange(35, 57)
            require(!isStaking) { "cannot use staking address in binding output" }
            Pair(Address.fromScriptHash(segwitScriptHash, false), BindingTarget.fromScriptHash(bindingScriptHash))
        } else {
            throw IllegalArgumentException("cannot decode script hash to address")
        }
    }
}
