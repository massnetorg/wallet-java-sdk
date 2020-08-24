package org.massnet.signer

import org.massnet.signer.ByteUtils.hexToBytes
import org.massnet.signer.ByteUtils.toHexString
import org.bitcoinj.core.ECKey
import org.bitcoinj.script.Script
import org.junit.jupiter.api.Test

object ProtoTest {
    @Test
    fun testParseTransaction() {
        val hex =
            "080112a4010a280a24091fc29fe6a799515d11b457408abbafe0e719dc9bd41fd20f13752194f2946b8e501167100112484730440220696dabe88d38c361d7d8aca75ae03ad8bdbd3dbcfb351a4169baa7ef90d4ebfa022037be4e5e5e3f178fcfe0cdc494b48d2faa4ed3da0999b935fb81deeba80a57b9011225512102a7fbac3213b58b53f489a866e3ce0e3a2e19886a212ea06e7f61b9b111d82bca51ae19ffffffffffffffff12a4010a280a24092539f5b65a4f729b11debf45a8a4c3ae3919f9cdaf75aac8d31c21ba3764b4b1ed20de1001124847304402205878c67bce67b596ee873ea8531f01e6a7c636aabf0db84321835899c1395aa002201f59a8d84bb1edb1c118506db447a3fd2775dfb7dfd2798e475315c8bc10e4a80112255121024a288fcd821c308df932248be19430de8c0c202f689029510610a0cd9d8e847d51ae19ffffffffffffffff1a2a0898bfffc024122200200555f6e6d13d419a272c179a3643a999307d2db481c987b1d7093751724e9aa01a2708ac341222002052586f0c1017b3497a0745d6a5390cd26e34f3d3acb9f1a0d3da7c487e74e42b".hexToBytes()
        val transaction = Proto.Tx.parseFrom(hex)

        println(transaction)

        transaction.txOutList.forEach {
            val script = Script(it.pkScript.toByteArray())
            println("Out")
            println(script)
        }

        transaction.txInList.flatMap { t -> t.witnessList }.forEach {
            val script = Script(it.toByteArray())
            println("In")
            println(script)
        }
    }

    @Test
    fun testECKey() {
        val priv = "649ce9ec4cf87af806ded9fb95a4eafe55c2e045ba7a16ca7d3ea20a63ac582e"
        val key = ECKey.fromPrivate(priv.hexToBytes(), true)
        println(key.pubKey.toHexString())
    }

    @Test
    fun testSign() {
        val unsigned =
            "080112330a280a24091fc29fe6a799515d11b457408abbafe0e719dc9bd41fd20f13752194f2946b8e501167100119ffffffffffffffff12330a280a24092539f5b65a4f729b11debf45a8a4c3ae3919f9cdaf75aac8d31c21ba3764b4b1ed20de100119ffffffffffffffff1a2a0898bfffc024122200200555f6e6d13d419a272c179a3643a999307d2db481c987b1d7093751724e9aa01a2708ac341222002052586f0c1017b3497a0745d6a5390cd26e34f3d3acb9f1a0d3da7c487e74e42b"
        val priv = listOf(
            "649ce9ec4cf87af806ded9fb95a4eafe55c2e045ba7a16ca7d3ea20a63ac582e",
            "15a9d18c34531d4ac02116890ecd9fa623d4e6d2456769a28dd6206ea6bd9767"
        )
        val amounts = listOf<Long>(899999708, 799997080)
        val signed = Signer.signRawTransaction(unsigned, amounts, priv, HashType.SigHashAll)
        println(signed)
    }
}
