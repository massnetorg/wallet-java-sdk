package org.massnet.signer

import com.google.protobuf.ByteString
import org.bitcoinj.core.ECKey
import org.bitcoinj.script.Script
import org.massnet.signer.ByteUtils.hexToBytes
import org.massnet.signer.ByteUtils.toHexString
import java.nio.ByteBuffer

enum class HashType(val value: Byte) {
    SigHashAll(0x1),
    SigHashNone(0x2),
    SigHashSingle(0x3),
    SigHashAnyOneCanPay(0x80.toByte()),
}

object Signer {

    private fun getSignatureScript(signature: ECKey.ECDSASignature, type: HashType): Script {
        val encoded = signature.encodeToDER();
        val size = encoded.size + 2
        val buf = ByteBuffer.allocate(size)
        buf.put((encoded.size + 1).toByte())
        buf.put(encoded)
        buf.put(type.value) // BIP-0062: 0x30 [total-length] 0x02 [R-length] [R] 0x02 [S-length] [S] [sighash-type]
        return Script(buf.array())
    }

    @JvmStatic
    fun signTransaction(tx: Proto.Tx, amounts: List<Long>, keys: List<ECKey>, type: HashType): Proto.Tx {
        // currently only hash all is supported
        assert(type == HashType.SigHashAll)
        // ensure input validity
        assert(tx.txInCount == amounts.size && tx.txInCount == keys.size)
        // make a copy
        val signedTx = tx.toBuilder()
        // signing hashes
        val sigHashes = TxSigHashes.fromTransaction(tx)
        // sign each input
        for (i in 0 until tx.txInCount) {
            val txIn = tx.txInList[i]
            val key = keys[i]
            val redeemScript = Address.getRedeemScript(key)
            val hash = Hash.hashWitnessSignature(tx, i, amounts[i], redeemScript, type, sigHashes)
            val signature = key.sign(hash)
            val signatureScript = getSignatureScript(signature, type)
            val witnesses =
                listOf(ByteString.copyFrom(signatureScript.program), ByteString.copyFrom(redeemScript.program))
            // replace txIn with witnesses set
            signedTx.setTxIn(i, txIn.toBuilder().addAllWitness(witnesses).build())
        }
        return signedTx.build()
    }

    @JvmStatic
    fun signTransaction(tx: Proto.Tx, amounts: List<Long>, privateKeys: List<String>, type: HashType): String {
        // create key pair
        val keys = privateKeys.map { ECKey.fromPrivate(it.hexToBytes(), true) }
        val signedTx = signTransaction(tx, amounts, keys, type)
        return signedTx.toByteArray().toHexString()
    }

    @JvmStatic
    fun signTransaction(tx: Transaction, amounts: List<Long>, privateKeys: List<String>, type: HashType): String {
        return signTransaction(tx.toProtoTx(), amounts, privateKeys, type)
    }

    @JvmStatic
    fun signRawTransaction(hex: String, amounts: List<Long>, privateKeys: List<String>, type: HashType): String {
        return signTransaction(Proto.Tx.parseFrom(hex.hexToBytes()), amounts, privateKeys, type)
    }
}
