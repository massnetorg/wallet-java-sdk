package org.massnet.signer

import com.google.gson.*
import org.massnet.signer.ByteUtils.hexToBytes
import org.massnet.signer.ByteUtils.toHexString
import java.lang.reflect.Type
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.MessageDigest

object ByteUtils {
    private const val HEX_CHARS = "0123456789abcdef"
    private val HEX_CHARS_ARRAY = HEX_CHARS.toCharArray()

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

    fun ByteArray.toHexString(): String {
        val result = StringBuffer()
        forEach {
            result.append(it.toHexString())
        }
        return result.toString()
    }

    fun Byte.toHexString(): String {
        val octet = this.toInt()
        val firstIndex = (octet and 0xF0) ushr 4
        val secondIndex = octet and 0x0F
        return "${HEX_CHARS_ARRAY[firstIndex]}${HEX_CHARS_ARRAY[secondIndex]}"
    }

    fun String.toProtoHash(): Proto.Hash {
        require(this.length == 64)
        val builder = Proto.Hash.newBuilder()
        builder.s0 = java.lang.Long.parseUnsignedLong(this.substring(0,  16), 16)
        builder.s1 = java.lang.Long.parseUnsignedLong(this.substring(16, 32), 16)
        builder.s2 = java.lang.Long.parseUnsignedLong(this.substring(32, 48), 16)
        builder.s3 = java.lang.Long.parseUnsignedLong(this.substring(48, 64), 16)
        return builder.build()
    }

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

    val GSON by lazy {
        GsonBuilder()
            .setPrettyPrinting()
            .registerTypeHierarchyAdapter(ByteArray::class.java, ByteArrayTypeAdapter())
            .create()
    }
}
