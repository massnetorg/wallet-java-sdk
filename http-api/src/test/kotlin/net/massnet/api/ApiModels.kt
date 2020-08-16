package net.massnet.api

import com.google.gson.Gson
import org.junit.jupiter.api.Test

object ApiModelsTest {
    val gson = Gson()
    val rawResult: List<Pair<String, String>> = listOf(
        Pair("""
            {
                "height": "176989",
                "target": "b173f7b71"
            }
        """.trimIndent(), BestBlock::class.qualifiedName!!),
        Pair("""
            {
                "peer_listening": true,
                "syncing": false,
                "chain_id": "e931abb77f2568f752a29ed28d442558764a5961ed773df7188430a0e0f7cf18",
                "local_best_height": "176992",
                "known_best_height": "176992",
                "wallet_sync_height": "176992",
                "peer_count": {
                    "total": 2,
                    "outbound": 2,
                    "inbound": 0
                },
                "peers": {
                    "outbound": [
                        {
                            "id": "0A6AFB3678A1612296AA5FD4338AF9304EA8831455DDC014D3F554357BBBC2EE",
                            "address": "39.99.32.37:43453",
                            "direction": "outbound"
                        },
                        {
                            "id": "B3664A9AC4AF1DBB457BB82F2F856F25DDE1F9F226D51BCA94A7F71123839100",
                            "address": "39.104.206.48:43453",
                            "direction": "outbound"
                        }
                    ],
                    "inbound": [],
                    "other": []
                }
            }
        """.trimIndent(), ClientStatus::class.qualifiedName!!),
        Pair("""
            {
                "wallets": [
                    {
                        "wallet_id": "ac10jv5xfkywm9fu2elcjyqyq4gyz6yu6jzm7fq8fz",
                        "type": 1,
                        "remarks": "init",
                        "status": 0,
                        "status_msg": "ready"
                    },
                    {
                        "wallet_id": "ac10nge8pha03mdp32ndhtxr7lmscc4s0lkg9eee2j",
                        "type": 1,
                        "remarks": "init-2",
                        "status": 1,
                        "status_msg": "109830"
                    }
                ]
            }
        """.trimIndent(), Wallets::class.qualifiedName!!),
        Pair("""
            {
                "passphrase":"123456",
                "remarks":"init-2",
                "bit_size":192
            }
        """.trimIndent(), CreateWalletRequest::class.qualifiedName!!),
        Pair("""
            {
                "wallet_id": "ac10nge8pha03mdp32ndhtxr7lmscc4s0lkg9eee2j",
                "mnemonic": "tribe belt hand odor beauty pelican switch pluck toe pigeon zero future acoustic enemy panda twice endless motion",
                "version": 1
            }
        """.trimIndent(), CreateWalletResponse::class.qualifiedName!!),
        Pair("""
            {
                "wallet_id":"ac10jv5xfkywm9fu2elcjyqyq4gyz6yu6jzm7fq8fz"
            }
        """.trimIndent(), UseWalletRequest::class.qualifiedName!!),
        Pair("""
            {
                "chain_id": "e931abb77f2568f752a29ed28d442558764a5961ed773df7188430a0e0f7cf18",
                "wallet_id": "ac10jv5xfkywm9fu2elcjyqyq4gyz6yu6jzm7fq8fz",
                "type": 1,
                "total_balance": "48994.88593426",
                "external_key_count": 5,
                "internal_key_count": 0,
                "remarks": "init"
            }
        """.trimIndent(), UseWalletResponse::class.qualifiedName!!),
        Pair("""
            {
            	"keystore": "{\"remarks\":\"init\",\"crypto\":{\"cipher\":\"Stream cipher\",\"entropyEnc\":\"408998f673619fafe25a820588f12c0b9fed25a0ec2fad33128abc62644cd9d80c5e9f2f1f23df1862058ff7622bb097185c45f6b59697ec\",\"kdf\":\"scrypt\",\"privParams\":\"9d5d2f6de075ed1f8c46d590a823c67bcbdb25159ba3caf50426c27b575821a95daa891a93be42c900f40c1c6f1ae72c19cf3ffbefe45bb3b67643988a517cb2000004000000000008000000000000000100000000000000\",\"cryptoKeyEntropyEnc\":\"8b5d8cf78697d88c7a9e3143862c8db45b7a9729e5976df99ef586c7ebfd3b35a3ab2d82b606eaa9ca1f7c7b0bf21a585e87aec423e48c1e4d0d45745b5a7d4ae5c1c688c2cd9ca1\"},\"hdPath\":{\"Purpose\":44,\"Coin\":297,\"Account\":1,\"ExternalChildNum\":5,\"InternalChildNum\":0}}",
            	"passphrase": "111111"
            }
        """.trimIndent(), ImportWalletRequest::class.qualifiedName!!),
        Pair("""
            {
                "ok": true,
                "wallet_id": "ac10jv5xfkywm9fu2elcjyqyq4gyz6yu6jzm7fq8fz",
                "type": 1,
                "version": 0,
                "remarks": "init"
            }
        """.trimIndent(), ImportWalletResponse::class.qualifiedName!!),
        Pair("""
            {
            	"mnemonic":"tribe belt hand odor beauty pelican switch pluck toe pigeon zero future acoustic enemy panda twice endless motion",
            	"passphrase":"123456",
                "remarks":"e.g."
            }
        """.trimIndent(), ImportMnemonicRequest::class.qualifiedName!!),
        Pair("""
            {
            	"wallet_id":"ac10nge8pha03mdp32ndhtxr7lmscc4s0lkg9eee2j",
            	"passphrase":"123456"
            }
        """.trimIndent(), WalletRequest::class.qualifiedName!!),
        Pair("""
            {
                "keystore": "{\"remarks\":\"init-2\",\"crypto\":{\"cipher\":\"Stream cipher\",\"entropyEnc\":\"8e5d6c3fba1bd23a75fd545287f41828a0f7d1c75c8e3166cbc266d0ffb95997764ecc631b995c3b4696aaf7c58c6e887fc0b89ebf4ccfd0f3f82d4c33913650\",\"kdf\":\"scrypt\",\"privParams\":\"551147d50b72305cf0769f3f524e67a9ebda3fb256aaedb53c43dc5b24e99c2bb2c39425fa4fe08afafda88eb2a096e3395c499bae8aafe4bc6436ee70c0a150000004000000000008000000000000000100000000000000\",\"cryptoKeyEntropyEnc\":\"61089855ec95a5f0214506aefcd2f633ef330a774698d1e8a465dc86f68146c13dd95eb562012a8601aed6f8c3803d4283bd8b8ecd2613629a272c5911a5449aa002254c147ff3c2\"},\"hdPath\":{\"Purpose\":44,\"Coin\":297,\"Account\":1,\"ExternalChildNum\":0,\"InternalChildNum\":0}}"
            }
        """.trimIndent(), ExportWalletResponse::class.qualifiedName!!),
        Pair("""
            {
                "ok": true
            }
        """.trimIndent(), RemoveWalletResponse::class.qualifiedName!!),
        Pair("""
            {
                "mnemonic": "tribe belt hand odor beauty pelican switch pluck toe pigeon zero future acoustic enemy panda twice endless motion"
            }
        """.trimIndent(), WalletMnemonic::class.qualifiedName!!),
        Pair("""
            {
                "required_confirmations":1,
                "detail":true
            }
        """.trimIndent(), GetWalletBalanceRequest::class.qualifiedName!!),
        Pair("""
            {
                "wallet_id": "ac10jv5xfkywm9fu2elcjyqyq4gyz6yu6jzm7fq8fz",
                "total": "48994.88593426",
                "detail": {
                    "spendable": "48994.77943826",
                    "withdrawable_staking": "0",
                    "withdrawable_binding": "0.106496"
                }
            }
        """.trimIndent(), WalletBalance::class.qualifiedName!!)
    )

    @Test
    fun `Test parsing some models`() {
        for ((json, clazz) in rawResult) {
            println(gson.fromJson(json, Class.forName(clazz)))
        }

    }
}
