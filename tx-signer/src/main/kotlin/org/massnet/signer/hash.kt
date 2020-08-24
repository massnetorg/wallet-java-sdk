package org.massnet.signer

import org.bitcoinj.core.Sha256Hash
import org.bitcoinj.script.Script
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.nio.ByteBuffer

object Hash {
    fun hashPrevOutputs(tx: net.massnet.signer.Proto.Tx): ByteArray {
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

    fun hashSequences(tx: net.massnet.signer.Proto.Tx): ByteArray {
        val buf = ByteBuffer.allocate((8) * tx.txInList.size)
        for (txIn in tx.txInList) {
            buf.putLong(txIn.sequence)
        }
        return Sha256Hash.hashTwice(buf.array())
    }

    fun hashOutputs(tx: net.massnet.signer.Proto.Tx): ByteArray {
        val size = tx.txOutList.map { tx -> 8 + tx.pkScript.size() }.sum()
        val buf = ByteBuffer.allocate(size)
        for (txOut in tx.txOutList) {
            buf.putLong(txOut.value)
            buf.put(txOut.pkScript.toByteArray())
        }
        return Sha256Hash.hashTwice(buf.array())
    }

    fun net.massnet.signer.Proto.Hash.toByteArray(): ByteArray {
        val arr = ByteBuffer.allocate(256)
        arr.putLong(this.s0)
        arr.putLong(this.s1)
        arr.putLong(this.s2)
        arr.putLong(this.s3)
        return arr.array()
    }

    enum class HashType(val value: Int) {
        SigHashAll(0x1),
        SigHashNone(0x2),
        SigHashSingle(0x3),
        SigHashAnyOneCanPay(0x80),
    }

    fun hashWitnessSignature(tx: net.massnet.signer.Proto.Tx, index: Int, value: Long, script: Script, hashType: HashType, sigHashes: TxSigHashes): ByteArray {

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
        out.write(txIn.previousOutPoint.hash.toByteArray())
        out.write(txIn.previousOutPoint.index)

        // write script
        // TODO:

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

        return Sha256Hash.hashTwice(bout.toByteArray())
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
        fun fromTransaction(tx: net.massnet.signer.Proto.Tx): TxSigHashes {
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

