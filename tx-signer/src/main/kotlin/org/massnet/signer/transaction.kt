package org.massnet.signer

import org.massnet.signer.ByteUtils.hexToBytes

class Transaction(
    var version: Int,
    var lockTime: Long,
    var vin: List<Input>,
    var vout: List<Output>
) {
    class Input(
        var hash: ByteArray,
        var index: Int,
        var sequence: Long,
        var witness: List<ByteArray>,
        var address: String? = null
    )

    class Output(
        var value: Long,
        var pkScript: ByteArray
    ) {
        lateinit var address: String
    }

    fun toJson(): String {
        return Utils.GSON.toJson(this)
    }

    companion object {

        private fun Proto.TxIn.toInput(): Input {
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

        private fun Proto.TxOut.toOutput(): Output {
            val output = Output(this.value, this.pkScript.toByteArray())
            val scriptHash = output.pkScript.drop(2).toByteArray() // OP_0 OP_LEN ScriptHash
            output.address = Address.fromScriptHash(scriptHash).encodeToString()
            return output
        }

        @JvmStatic
        fun fromProtoTx(tx: Proto.Tx): Transaction {
            return Transaction(
                tx.version,
                tx.lockTime,
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

