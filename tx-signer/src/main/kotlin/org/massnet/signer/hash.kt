package org.massnet.signer

import org.bitcoinj.core.Sha256Hash
import org.bitcoinj.script.Script
import org.massnet.signer.ByteUtils.toHexString
import java.nio.ByteBuffer
import java.nio.ByteOrder

object Hash {
    fun hashPrevOutputs(tx: Proto.Tx): ByteArray {
        val buf = ByteBuffer.allocate((32 + 4) * tx.txInCount).order(ByteOrder.LITTLE_ENDIAN)
        for (txIn in tx.txInList) {
            val prevOut = txIn.previousOutPoint
            buf.put(prevOut.hash.toBytes())
            buf.putInt(prevOut.index)
        }
        return Sha256Hash.hashTwice(buf.array())
    }

    fun hashSequences(tx: Proto.Tx): ByteArray {
        val buf = ByteBuffer.allocate(8 * tx.txInCount).order(ByteOrder.LITTLE_ENDIAN)
        for (txIn in tx.txInList) {
            buf.putLong(txIn.sequence)
        }
        return Sha256Hash.hashTwice(buf.array())
    }

    fun hashOutputs(tx: Proto.Tx): ByteArray {
        val size = tx.txOutList.map { 8 + it.pkScript.size() }.sum()
        val buf = ByteBuffer.allocate(size).order(ByteOrder.LITTLE_ENDIAN)
        for (txOut in tx.txOutList) {
            buf.putLong(txOut.value)
            buf.put(txOut.pkScript.toByteArray())
        }
        return Sha256Hash.hashTwice(buf.array())
    }

    private fun Proto.Hash.toBytes(): ByteArray {
        val arr = ByteBuffer.allocate(32).order(ByteOrder.BIG_ENDIAN)
        arr.putLong(this.s0)
        arr.putLong(this.s1)
        arr.putLong(this.s2)
        arr.putLong(this.s3)
        return arr.array()
    }

    fun hashWitnessSignature(
        tx: Proto.Tx,
        index: Int,
        value: Long,
        script: Script,
        hashType: HashType,
        sigHashes: TxSigHashes
    ): Sha256Hash {

        assert(index < tx.txInList.size && index >= 0)
        // we only support this now
        assert(hashType == HashType.SigHashAll)

        val buf = ByteBuffer.allocate(1.shl(20)).order(ByteOrder.LITTLE_ENDIAN)

        // version
        buf.putInt(sigHashes.version)

        // not anyone can pay
        buf.put(sigHashes.prevOuts)

        // sighash all
        buf.put(sigHashes.sequences)

        // put input
        val txIn = tx.txInList[index]

        // write outpoint
        buf.put(txIn.previousOutPoint.hash.toBytes())
        buf.putInt(txIn.previousOutPoint.index)

        // write script
        buf.put(script.program)

        // write amount, sequence
        buf.putLong(value)
        buf.putLong(txIn.sequence)

        // write payload
        buf.put(tx.payload.toByteArray())

        // write outputs
        buf.put(sigHashes.outputs)

        // write locktime & type
        buf.putLong(sigHashes.lockTime)

        // write hash type
        buf.putInt(hashType.value.toInt())
        val length = buf.position()
        val raw = ByteArray(length)

        System.arraycopy(buf.array(), 0, raw, 0, length)
        println("Raw data to sign: ${raw.toHexString()}")
        return Sha256Hash.twiceOf(raw)
    }
}

class TxSigHashes(
    var prevOuts: ByteArray,
    var sequences: ByteArray,
    var outputs: ByteArray,
    var version: Int,
    var lockTime: Long
) {
    companion object {
        fun fromTransaction(tx: Proto.Tx): TxSigHashes {
            return TxSigHashes(
                Hash.hashPrevOutputs(tx),
                Hash.hashSequences(tx),
                Hash.hashOutputs(tx),
                tx.version,
                tx.lockTime
            )
        }
    }
}

