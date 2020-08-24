package org.massnet.signer

import org.bitcoinj.core.Sha256Hash
import org.bitcoinj.script.Script
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.nio.ByteBuffer

object Hash {
    fun hashPrevOutputs(tx: Proto.Tx): ByteArray {
        val buf = ByteBuffer.allocate((256 + 4) * tx.txInList.size)
        for (txIn in tx.txInList) {
            val prevOut = txIn.previousOutPoint
            buf.putLong(prevOut.hash.s0)
            buf.putLong(prevOut.hash.s1)
            buf.putLong(prevOut.hash.s2)
            buf.putLong(prevOut.hash.s3)
            buf.putInt(prevOut.index)
        }
        return Sha256Hash.hashTwice(buf.array())
    }

    fun hashSequences(tx: Proto.Tx): ByteArray {
        val buf = ByteBuffer.allocate((8) * tx.txInList.size)
        for (txIn in tx.txInList) {
            buf.putLong(txIn.sequence)
        }
        return Sha256Hash.hashTwice(buf.array())
    }

    fun hashOutputs(tx: Proto.Tx): ByteArray {
        val size = tx.txOutList.map { 8 + it.pkScript.size() }.sum()
        val buf = ByteBuffer.allocate(size)
        for (txOut in tx.txOutList) {
            buf.putLong(txOut.value)
            buf.put(txOut.pkScript.toByteArray())
        }
        return Sha256Hash.hashTwice(buf.array())
    }

    fun Proto.Hash.toBytes(): ByteArray {
        val arr = ByteBuffer.allocate(256)
        arr.putLong(this.s0)
        arr.putLong(this.s1)
        arr.putLong(this.s2)
        arr.putLong(this.s3)
        return arr.array()
    }

    fun hashWitnessSignature(tx: Proto.Tx, index: Int, value: Long, script: Script, hashType: HashType, sigHashes: TxSigHashes): Sha256Hash {

        assert(index < tx.txInList.size && index >= 0)
        // we only support this now
        assert(hashType == HashType.SigHashAll)

        val bout = ByteArrayOutputStream()
        val out = DataOutputStream(bout)

        // version
        out.writeInt(sigHashes.version)

        // not anyone can pay
        out.write(sigHashes.prevOuts)

        // sighash all
        out.write(sigHashes.sequences)

        // put input
        val txIn = tx.txInList[index]

        // write outpoint
        out.write(txIn.previousOutPoint.hash.toBytes())
        out.write(txIn.previousOutPoint.index)

        // write script
        out.write(script.program)

        // write amount, sequence
        out.writeLong(value)
        out.writeLong(txIn.sequence)

        // write payload
        out.write(tx.payload.toByteArray())

        // write outputs
        out.write(sigHashes.outputs)

        // write locktime & type
        out.writeLong(sigHashes.lockTime)

        // write hash type
        out.write(hashType.value)

        return Sha256Hash.twiceOf(bout.toByteArray())
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

