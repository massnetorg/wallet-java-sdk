package org.massnet.signer

import com.google.protobuf.ByteString
import org.massnet.signer.ByteUtils.hexToBytes
import org.massnet.signer.ByteUtils.toProtoHash

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
    }

    class Output(
        var value: Long,
        var pkScript: ByteArray
    ) {

        var address: String

        init {
            val scriptHash = pkScript.drop(2).toByteArray() // OP_0 OP_LEN ScriptHash
            address = Address.fromScriptHash(scriptHash).encodeToString()
        }

        fun toProtoTxOut(): Proto.TxOut {
            val builder = Proto.TxOut.newBuilder()
            builder.value = value
            builder.pkScript = ByteString.copyFrom(pkScript)
            return builder.build()
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
                val scriptHash = Utils.sha256.digest(input.witness[1])
                input.address = Address.fromScriptHash(scriptHash).encodeToString()
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

