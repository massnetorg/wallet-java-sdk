package org.massnet.signer

import com.google.protobuf.ByteString
import org.bitcoinj.core.Sha256Hash
import org.massnet.signer.ByteUtils.hexToBytes
import org.massnet.signer.ByteUtils.toHexString
import org.massnet.signer.ByteUtils.toProtoHash
import java.nio.ByteBuffer
import java.nio.ByteOrder

class Transaction(
    var version: Int,
    var lockTime: Long,
    var payload: ByteArray,
    var vin: List<Input>,
    var vout: List<Output>
) {
    class Input(
        var hash: ByteArray,
        var index: Int,
        var sequence: Long,
        var witness: List<ByteArray>,
        var address: String? = null
    ) {
        fun toProtoTxIn(): Proto.TxIn {
            val builder = Proto.TxIn.newBuilder()
            builder.sequence = sequence
            val outputBuilder = Proto.OutPoint.newBuilder()
            outputBuilder.hash = hash.toProtoHash()
            outputBuilder.index = index
            builder.previousOutPoint = outputBuilder.build()
            if (!this.witness.isNullOrEmpty()) {
                builder.addAllWitness(this.witness.map(ByteString::copyFrom))
            }
            return builder.build()
        }

        fun toBytes(includeWitness: Boolean): ByteArray {
            val noWitnessSize = hash.size + 4 + 8
            val size = if (includeWitness) noWitnessSize + witness.sumOf { w -> w.size } else noWitnessSize
            val buf = ByteBuffer.allocate(size).order(ByteOrder.LITTLE_ENDIAN)
            val hashBuf = ByteBuffer.wrap(hash).order(ByteOrder.BIG_ENDIAN).asLongBuffer().asReadOnlyBuffer()
            (0..3).forEach{ i -> buf.putLong(hashBuf[i]) }
            buf.putInt(index)
            if (includeWitness) {
                witness.forEach { w -> buf.put(w) }
            }
            buf.putLong(sequence)
            return buf.array()
        }
    }

    class Output(
        var value: Long,
        var pkScript: ByteArray
    ) {

        var address: String
        var isStaking: Boolean
        var isBinding = false
        var bindingTarget: String? = null
        var bindingType: Int? = null
        var bindingSize: Int? = null

        init {
            val (addr, target) = ScriptUtils.decodeOutputScript(pkScript)
            addr.let {
                address = it.encodedString
                isStaking = it.isStaking
            }
            target?.let {
                isBinding = true
                bindingTarget = it.encodedString
                bindingType = it.type
                bindingSize = it.size
            }
        }

        fun toProtoTxOut(): Proto.TxOut {
            val builder = Proto.TxOut.newBuilder()
            builder.value = value
            builder.pkScript = ByteString.copyFrom(pkScript)
            return builder.build()
        }

        fun toBytes(): ByteArray {
            val buf = ByteBuffer.allocate(8 + pkScript.size).order(ByteOrder.LITTLE_ENDIAN)
            buf.putLong(value)
            buf.put(pkScript)
            return buf.array()
        }
    }

    fun toProtoTx(): Proto.Tx {
        val builder = Proto.Tx.newBuilder()
        builder.version = version
        builder.lockTime = lockTime
        builder.payload = ByteString.copyFrom(payload)
        vin.forEach { v -> builder.addTxIn(v.toProtoTxIn()) }
        vout.forEach { v -> builder.addTxOut(v.toProtoTxOut()) }
        return builder.build()
    }

    fun toJson(): String {
        return Utils.GSON.toJson(this)
    }

    fun toBytes(includeWitness: Boolean): ByteArray {
        val buf = ByteBuffer.allocate(1.shl(20)).order(ByteOrder.LITTLE_ENDIAN)
        buf.putInt(version)
        vin.forEach { i -> buf.put(i.toBytes(includeWitness)) }
        vout.forEach { o -> buf.put(o.toBytes()) }
        buf.putLong(lockTime)
        buf.put(payload)
        return buf.array().sliceArray(0 until buf.position())
    }

    fun getHash(): String {
        val hash = Sha256Hash.twiceOf(toBytes(false))
        return hash.bytes.toHexString()
    }

    companion object {

        @JvmStatic
        fun Proto.TxIn.toInput(): Input {
            val input = Input(
                this.previousOutPoint.hash.toBytes(),
                this.previousOutPoint.index,
                this.sequence,
                this.witnessList.map { it.toByteArray() })
            if (input.witness.isNotEmpty()) {
                require(input.witness.size == 2)
                val scriptHash = Utils.sha256(input.witness[1])
                input.address = Address.fromScriptHash(scriptHash).encodedString
            }
            return input
        }

        @JvmStatic
        fun Proto.TxOut.toOutput(): Output {
            return Output(value, pkScript.toByteArray())
        }

        @JvmStatic
        fun fromProtoTx(tx: Proto.Tx): Transaction {
            return Transaction(
                tx.version,
                tx.lockTime,
                tx.payload.toByteArray(),
                tx.txInList.map { it.toInput() },
                tx.txOutList.map { it.toOutput() })
        }

        @JvmStatic
        fun decodeRawToJson(hex: String): String {
            val protoTx = Proto.Tx.parseFrom(hex.hexToBytes())
            val tx = fromProtoTx(protoTx)
            return tx.toJson()
        }
    }
}

