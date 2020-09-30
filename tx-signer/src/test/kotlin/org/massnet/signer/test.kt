package org.massnet.signer

import org.bitcoinj.core.ECKey
import org.bitcoinj.script.Script
import org.junit.jupiter.api.Test
import org.massnet.signer.ByteUtils.hexToBytes
import org.massnet.signer.ByteUtils.toHexString
import org.massnet.signer.ByteUtils.toProtoHash
import java.security.SecureRandom

object ProtoTest {

    private const val unsignedTx = "080112330a280a24091fc29fe6a799515d11b457408abbafe0e719dc9bd41fd20f13752194f2946b8e501167100119ffffffffffffffff12330a280a24092539f5b65a4f729b11debf45a8a4c3ae3919f9cdaf75aac8d31c21ba3764b4b1ed20de100119ffffffffffffffff1a2a0898bfffc024122200200555f6e6d13d419a272c179a3643a999307d2db481c987b1d7093751724e9aa01a2708ac341222002052586f0c1017b3497a0745d6a5390cd26e34f3d3acb9f1a0d3da7c487e74e42b"
    private const val signedTx = "080112a4010a280a24091fc29fe6a799515d11b457408abbafe0e719dc9bd41fd20f13752194f2946b8e5011671001124847304402207c22fb7b6444514a61c9c657e682f03f57c02d65815f8e6ea63defb12f8ac9d10220016bf8ba7131ed557cb007a80cd2e56c8117c12992c99c54842be9724a1fd98a011225512102a7fbac3213b58b53f489a866e3ce0e3a2e19886a212ea06e7f61b9b111d82bca51ae19ffffffffffffffff12a4010a280a24092539f5b65a4f729b11debf45a8a4c3ae3919f9cdaf75aac8d31c21ba3764b4b1ed20de100112484730440220700f94262b1e91b5270a89b9d97376e2ea4780424d1f4cfbca409c38087f239b02200b13b3455e780c7886a3232263c73c3b7847acee3253aabdd2c0cc189b9a23850112255121024a288fcd821c308df932248be19430de8c0c202f689029510610a0cd9d8e847d51ae19ffffffffffffffff1a2a0898bfffc024122200200555f6e6d13d419a272c179a3643a999307d2db481c987b1d7093751724e9aa01a2708ac341222002052586f0c1017b3497a0745d6a5390cd26e34f3d3acb9f1a0d3da7c487e74e42b"

    @Test
    fun testParseTransaction() {

        val transaction = Proto.Tx.parseFrom(signedTx.hexToBytes())
        println(transaction)

        transaction.txOutList.forEach {
            val script = Script(it.pkScript.toByteArray())
            println("Out")
            println(script)
        }

        transaction.txInList.flatMap { t -> t.witnessList }.forEach { it ->
            val script = Script(it.toByteArray())
            println("In")
            println(script)
        }

        val txParsed = Transaction.fromProtoTx(transaction)
        println(txParsed.toProtoTx())

        println(txParsed.getHash())
    }

    @Test
    fun testECKey() {
        val priv = "649ce9ec4cf87af806ded9fb95a4eafe55c2e045ba7a16ca7d3ea20a63ac582e"
        val key = ECKey.fromPrivate(priv.hexToBytes(), true)
        println(key.pubKey.toHexString())
    }

    @Test
    fun testSign() {
        val priv = listOf(
            "649ce9ec4cf87af806ded9fb95a4eafe55c2e045ba7a16ca7d3ea20a63ac582e",
            "15a9d18c34531d4ac02116890ecd9fa623d4e6d2456769a28dd6206ea6bd9767"
        )
        val amounts = listOf(8999997080, 799997080)
        val signed = Signer.signRawTransaction(unsignedTx, amounts, priv, HashType.SigHashAll)
        require(signed == signedTx)
    }

    @Test
    fun testConvertTransactionToJson() {
        val json = Transaction.decodeRawToJson(signedTx)
        println(json)
    }

    @Test
    fun testConvertAddress() {
        val cases = listOf(
            Pair(
                "02a7fbac3213b58b53f489a866e3ce0e3a2e19886a212ea06e7f61b9b111d82bca",
                "ms1qq2fvx7rqsz7e5j7s8ght22wgv6fhrfu7n4julrgxnmf7ysln5us4s7w3yqj"
            ),
            Pair(
                "024a288fcd821c308df932248be19430de8c0c202f689029510610a0cd9d8e847d",
                "ms1qq746t0frr6q4pmam0yp93mv6xc2qvc2l44vqx6m0wdtjc3s4f2xhqsf4vrl"
            )
        )
        for ((pubKey, addr) in cases) {
            val key = ECKey.fromPublicOnly(pubKey.hexToBytes())
            assert(Address.fromPubKey(key).encodeToString() == addr)
        }
    }
    
    @Test
    fun testCreateAndValidateAddress() {
        assert(Address.validate("ggg") == null)
        assert(Address.validate("ms1qq2fvx7rqsz7e5j7s8ght22wgv6fhrfu7n4julrgxnmf7ysln5us4s7w3yqj") != null)
        val (addr, key) = Address.create(SecureRandom.getSeed(32), false)
        assert(Address.validate(addr) != null)
        assert(Address.fromPubKey(ECKey.fromPrivate(key.hexToBytes(), true), false).encodeToString() == addr)
    }

    @Test
    fun testCreateTransaction() {
        val hashStr = "807b26a916bcd6338a5b715f053d9f8bb6d8d188b7770df8a57447891f7341cd"
        assert(hashStr.toProtoHash().toBytes().toHexString() == hashStr)
    }
}
