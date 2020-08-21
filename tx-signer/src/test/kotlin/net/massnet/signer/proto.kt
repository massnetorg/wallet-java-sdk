package net.massnet.signer

import net.massnet.signer.ByteUtils.hexToBytes
import net.massnet.signer.ByteUtils.toHexString
import org.bitcoinj.script.Script
import org.junit.jupiter.api.Test

object ProtoTest {
    @Test
    fun testParseTransaction() {
        val hex =
            "080112130a080a0010ffffffff0f19ffffffffffffffff1a2a088080a3c34712220020b992418b6e120502bc52a798e4f5ca199e86c0168d3b55bb4d4f9cf843090daa2a0c1e0000000000000000000000".hexToBytes()
        val transaction = Proto.Tx.parseFrom(hex)

        println(transaction)

        transaction.txOutList.forEach {
            val script = Script(it.pkScript.toByteArray())
            println("Out")
            println(script)
            println(script.program.toHexString())
        }

        transaction.txInList.flatMap { t -> t.witnessList }.forEach {
            val script = Script(it.toByteArray())
            println("In")
            println(script)
            println(script.program.toHexString())
        }
    }
}