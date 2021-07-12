package org.massnet.api

import org.junit.jupiter.api.Test


object ApiModelsTest {
    private val gson = ModelSerializer.GSON
    private val rawResults: List<Pair<String, String>> = listOf(
        Pair(
            """
            {
                "height": "176989",
                "target": "b173f7b71"
            }
        """.trimIndent(), BestBlock::class.qualifiedName!!
        ),
        Pair(
            """
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
        """.trimIndent(), ClientStatus::class.qualifiedName!!
        ),
        Pair(
            """
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
        """.trimIndent(), Wallets::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "passphrase":"123456",
                "remarks":"init-2",
                "bit_size":192
            }
        """.trimIndent(), CreateWalletRequest::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "wallet_id": "ac10nge8pha03mdp32ndhtxr7lmscc4s0lkg9eee2j",
                "mnemonic": "tribe belt hand odor beauty pelican switch pluck toe pigeon zero future acoustic enemy panda twice endless motion",
                "version": 1
            }
        """.trimIndent(), CreateWalletResponse::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "wallet_id":"ac10jv5xfkywm9fu2elcjyqyq4gyz6yu6jzm7fq8fz"
            }
        """.trimIndent(), UseWalletRequest::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "chain_id": "e931abb77f2568f752a29ed28d442558764a5961ed773df7188430a0e0f7cf18",
                "wallet_id": "ac10jv5xfkywm9fu2elcjyqyq4gyz6yu6jzm7fq8fz",
                "type": 1,
                "total_balance": "48994.88593426",
                "external_key_count": 5,
                "internal_key_count": 0,
                "remarks": "init"
            }
        """.trimIndent(), UseWalletResponse::class.qualifiedName!!
        ),
        Pair(
            """
            {
            	"keystore": "{\"remarks\":\"init\",\"crypto\":{\"cipher\":\"Stream cipher\",\"entropyEnc\":\"408998f673619fafe25a820588f12c0b9fed25a0ec2fad33128abc62644cd9d80c5e9f2f1f23df1862058ff7622bb097185c45f6b59697ec\",\"kdf\":\"scrypt\",\"privParams\":\"9d5d2f6de075ed1f8c46d590a823c67bcbdb25159ba3caf50426c27b575821a95daa891a93be42c900f40c1c6f1ae72c19cf3ffbefe45bb3b67643988a517cb2000004000000000008000000000000000100000000000000\",\"cryptoKeyEntropyEnc\":\"8b5d8cf78697d88c7a9e3143862c8db45b7a9729e5976df99ef586c7ebfd3b35a3ab2d82b606eaa9ca1f7c7b0bf21a585e87aec423e48c1e4d0d45745b5a7d4ae5c1c688c2cd9ca1\"},\"hdPath\":{\"Purpose\":44,\"Coin\":297,\"Account\":1,\"ExternalChildNum\":5,\"InternalChildNum\":0}}",
            	"passphrase": "111111"
            }
        """.trimIndent(), ImportWalletRequest::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "ok": true,
                "wallet_id": "ac10jv5xfkywm9fu2elcjyqyq4gyz6yu6jzm7fq8fz",
                "type": 1,
                "version": 0,
                "remarks": "init"
            }
        """.trimIndent(), ImportWalletResponse::class.qualifiedName!!
        ),
        Pair(
            """
            {
            	"mnemonic":"tribe belt hand odor beauty pelican switch pluck toe pigeon zero future acoustic enemy panda twice endless motion",
            	"passphrase":"123456",
                "remarks":"e.g."
            }
        """.trimIndent(), ImportMnemonicRequest::class.qualifiedName!!
        ),
        Pair(
            """
            {
            	"wallet_id":"ac10nge8pha03mdp32ndhtxr7lmscc4s0lkg9eee2j",
            	"passphrase":"123456"
            }
        """.trimIndent(), WalletRequest::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "keystore": "{\"remarks\":\"init-2\",\"crypto\":{\"cipher\":\"Stream cipher\",\"entropyEnc\":\"8e5d6c3fba1bd23a75fd545287f41828a0f7d1c75c8e3166cbc266d0ffb95997764ecc631b995c3b4696aaf7c58c6e887fc0b89ebf4ccfd0f3f82d4c33913650\",\"kdf\":\"scrypt\",\"privParams\":\"551147d50b72305cf0769f3f524e67a9ebda3fb256aaedb53c43dc5b24e99c2bb2c39425fa4fe08afafda88eb2a096e3395c499bae8aafe4bc6436ee70c0a150000004000000000008000000000000000100000000000000\",\"cryptoKeyEntropyEnc\":\"61089855ec95a5f0214506aefcd2f633ef330a774698d1e8a465dc86f68146c13dd95eb562012a8601aed6f8c3803d4283bd8b8ecd2613629a272c5911a5449aa002254c147ff3c2\"},\"hdPath\":{\"Purpose\":44,\"Coin\":297,\"Account\":1,\"ExternalChildNum\":0,\"InternalChildNum\":0}}"
            }
        """.trimIndent(), ExportWalletResponse::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "ok": true
            }
        """.trimIndent(), RemoveWalletResponse::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "mnemonic": "tribe belt hand odor beauty pelican switch pluck toe pigeon zero future acoustic enemy panda twice endless motion"
            }
        """.trimIndent(), WalletMnemonic::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "required_confirmations":1,
                "detail":true
            }
        """.trimIndent(), GetWalletBalanceRequest::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "wallet_id": "ac10jv5xfkywm9fu2elcjyqyq4gyz6yu6jzm7fq8fz",
                "total": "48994.88593426",
                "detail": {
                    "spendable": "48994.77943826",
                    "withdrawable_staking": "0",
                    "withdrawable_binding": "0.106496"
                }
            }
        """.trimIndent(), WalletBalance::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "version":0
            }
        """.trimIndent(), CreateAddressRequest::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "address": "ms1qqc7773md3ux8wkha6td2q9vcxfae39xvuzgj063q4l2mwymp2h0aqunux9z"
            }
        """.trimIndent(), CreateAddressResponse::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "details": [
                    {
                        "address": "ms1qqc7773md3ux8wkha6td2q9vcxfae39xvuzgj063q4l2mwymp2h0aqunux9z",
                        "version": 0,
                        "used": false,
                        "std_address": ""
                    }
                ]
            }
        """.trimIndent(), AddressDetails::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "required_confirmations":1,
                "addresses":[ "ms1qqehh47s0hvzrqqjl77ayj78yytstjkrsltcna343p8yg7ndskvveql4z3vl"]
            }
        """.trimIndent(), GetAddressBalanceRequest::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "balances": [
                    {
                        "address": "ms1qqehh47s0hvzrqqjl77ayj78yytstjkrsltcna343p8yg7ndskvveql4z3vl",
                        "total": "0.053248",
                        "spendable": "0",
                        "withdrawable_staking": "0",
                        "withdrawable_binding": "0.053248"
                    }
                ]
            }
        """.trimIndent(), AddressBalances::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "is_valid": true,
                "is_mine": true,
                "address": "ms1qqc7773md3ux8wkha6td2q9vcxfae39xvuzgj063q4l2mwymp2h0aqunux9z",
                "version": 0
            }
        """.trimIndent(), ValidateAddressResponse::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "addresses":["ms1qqehh47s0hvzrqqjl77ayj78yytstjkrsltcna343p8yg7ndskvveql4z3vl"]
            }
        """.trimIndent(), AddressesRequest::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "address_utxos": [
                    {
                        "address": "ms1qqehh47s0hvzrqqjl77ayj78yytstjkrsltcna343p8yg7ndskvveql4z3vl",
                        "utxos": [
                            {
                                "tx_id": "9e4c191a29a4eb018d7904ca1cd0d6f1568356426f0a4a1c5f388c91b768d80e",
                                "vout": 0,
                                "amount": "0.026624",
                                "block_height": "117649",
                                "maturity": 0,
                                "confirmations": 59412,
                                "spent_by_unmined": false
                            },
                            {
                                "tx_id": "9e4c191a29a4eb018d7904ca1cd0d6f1568356426f0a4a1c5f388c91b768d80e",
                                "vout": 1,
                                "amount": "0.026624",
                                "block_height": "117649",
                                "maturity": 0,
                                "confirmations": 59412,
                                "spent_by_unmined": false
                            }
                        ]
                    }
                ]
            }
        """.trimIndent(), AddressUtxos::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "hex":"080112a4010a280a24093e146ba1a03af113ac38692ba28363419125549bcf62b757021c03b039fd3d0db1001124847304402200c32d95597e7f9df8463d4260fdba320dd3f972182jd8wckc766b71b27fc02206da7e97a66ba1a0f36960fe86a38e19ffad809065b23a141c91affb20ef45dd801122551210356830b4780dc5f5463aa91eeaa6508f93698e039fce8cf17db363ba99afd790451ae19ffffffffffffffff1a2908909fd32f1222002076c83de3af1270125e4cf2db9b2b6c80d63d1043d9d0a57875193ad9d55783ef1a2708904e122200203498e06bb5508a12320488872db0f3ed19f8903a6a16b6f9b1a2708cc7b1222002041c47db9db9b076bd6c29e1461b46c7d26b44b08abc38f297d8b47db9dbec0c38"
            }
        """.trimIndent(), HexData::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "tx_id": "a8758742862e69028f9fe7b30aa2a17b77d794102ce76733216828006b215cee",
                "version": 1,
                "lock_time": "0",
                "size": 272,
                "vin": [
                    {
                        "tx_id": "003520971b85f661e4f0a0840f93d0d224dc0593fd241f910972ebf4b5cbb090",
                        "vout": 0,
                        "sequence": "18446744073709551615",
                        "witness": [
                            "4730440220213a3196a6043a8dd8565dfe04df3465b33fd8bc057f1d547ba579542f4450f602207b33e25bd4192a85d24eb2a7a843e13425789f0a4e16fadfeaaea05ab651a07501",
                            "51210356830b4780dc5f5463aa91eeaa6508f93698e039fce8cf17db363ba99afd790451ae"
                        ]
                    }
                ],
                "vout": [
                    {
                        "value": "20.02",
                        "n": 0,
                        "type": 3,
                        "script_asm": "0 76c83de3af1270125e4cf2db9b2b6c80d63d1043d9d0a57875193ad9d55783ef f5000304050607080102030405060708000203040020",
                        "script_hex": "002076c83de3af1270125e4cf2db9b2b6c80d63d1043d9d0a57875193ad9d55783ef16f5000304050607080102030405060708000203040020",
                        "recipient_address": "ms1qqwmyrmca0zfcpyhjv7tdek2mvsrtr6yzrm8g227r4ryadn42hs0hst2gvut",
                        "staking_address": "",
                        "binding_target": "18W8DkbtU6i8advRSkUaSocZqkE2JnDDZEbGH:MASS:32"
                    },
                    {
                        "value": "171.9799",
                        "n": 1,
                        "type": 1,
                        "script_asm": "0 0c315878dffef12a9f2c9dfde6a68b43c0fd2ffe63f94e7cf3459411d3866907",
                        "script_hex": "00200c315878dffef12a9f2c9dfde6a68b43c0fd2ffe63f94e7cf3459411d3866907",
                        "recipient_address": "ms1qqpsc4s7xllmcj48evnh77df5tg0q06tl7v0u5ul8ngk2pr5uxdyrspx4x5g",
                        "staking_address": "",
                        "binding_target": ""
                    }
                ],
                "payload_hex": "",
                "payload_decode": ""
            }
        """.trimIndent(), DecodedTransaction::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "inputs":[
                    {
                        "tx_id": "0054de1e7262dd1238df8283fc2cc940a038502bfb6b03ee7a761b82816f63d2",
                        "vout": 9
                    },{
                        "tx_id": "00d52e0ff62c35c4c7b66e163261fd00278c5815b55375ff75552b4e4ee82db1",
                        "vout": 9
                    }
                ],
                "amounts":{
                    "ms1qqc7773md3ux8wkha6td2q9vcxfae39xvuzgj063q4l2mwymp2h0aqunux9z":"200.00000001"
                }
            }
        """.trimIndent(), CreateRawTransactionRequest::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "amounts":{
                    "ms1qqc7773md3ux8wkha6td2q9vcxfae39xvuzgj063q4l2mwymp2h0aqunux9z":"100.00000001"
                },
                "fee": "0.0001"
            }
        """.trimIndent(), AutoCreateTransactionRequest::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "raw_tx":"080112330a280a240912dd62721ede54001140c92cfc8382df3819ee036bfb2b5038a021d2636f81821b767a100919ffffffffffffffff12330a280a2409c4352cf60f2ed5001100fd6132166eb6c719ff7553b515588c2721b12de84e4e2b5575100919ffffffffffffffff1a2a088190dfc04a12220020c7bde8edb1e18eeb5fba5b5402b3064f7312999c1224fd4415fab6e26c2abbfa",
                "passphrase": "111111"
            }
        """.trimIndent(), SignRawTransactionRequest::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "hex": "080112a4010a280a240912dd62721ede54001140c92cfc8382df3819ee036bfb2b5038a021d2636f81821b767a10091248473044022003161aa740d89984ef995103735bc6f6a0e0db76bb4eb224914bb797cf9df9ab02202765b0dd7ecb4bf5835e1a1bdce6686b26b3f6e37977668aaefdfa9a29e0a5f4011225512103d0cd7443a5e8dcc030793bea363fe328c84d2daf75f0f2db17d36c07642777b151ae19ffffffffffffffff12a4010a280a2409c4352cf60f2ed5001100fd6132166eb6c719ff7553b515588c2721b12de84e4e2b55751009124847304402205f3a8d2ea86971a7cebba0a07aeb93372732bcefc4e566e8d29009a8cc5598720220759fd2b87292cc9633f16e151d6d34e28dfbfde6b35fa329177b985f80388c14011225512103d0cd7443a5e8dcc030793bea363fe328c84d2daf75f0f2db17d36c07642777b151ae19ffffffffffffffff1a2a088190dfc04a12220020c7bde8edb1e18eeb5fba5b5402b3064f7312999c1224fd4415fab6e26c2abbfa",
                "complete": true
            }
        """.trimIndent(), SignRawTransactionResponse::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "fee": "0.0001"
            }
        """.trimIndent(), TransactionFee::class.qualifiedName!!
        ),
        Pair(
            """
            {
               "tx_id": "b7f7cab1dcb748987aa5694a6c021828cbf18f07154991467417dbe4f98e9707"
            }
        """.trimIndent(), TransactionId::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "hex": "080112a2010a260a240961f6851b9720350011d2d0930f84a0f0e419911f24fd9305dc242190b0cbb5f4eb7209124847304402207977ec8fe14e83993a7712af3c7330ba35dfe4d17977a7c005d82dbacea48c9d0220646ad239ccf5967698a834e08a54f80d950ed31140c4773347e8dc58c425bcee01122551210356830b4780dc5f5463aa91eeaa6508f93698e039fce8cf17db363ba99afd790451ae19ffffffffffffffff1a3f08a0d5b5a0251237002076c83de3af1270125e4cf2db9b2b6c80d63d1043d9d0a57875193ad9d55783ef14f5000204050607080102030405060708000203041a2a08d0dceca222122200200c315878dffef12a9f2c9dfde6a68b43c0fd2ffe63f94e7cf3459411d3866907",
                "tx_id": "c512cd335ac08e33c47bdf955e056475acbd96b13f96627d8069aaa8a6f3eab8",
                "version": 1,
                "lock_time": "0",
                "block": {
                    "height": "3311",
                    "block_hash": "5acdf5ca4bf6042d3fbb33aa489073c0558f1407083af39819c6eb0c81327efb",
                    "timestamp": "1624690416"
                },
                "vin": [
                    {
                        "value": "192",
                        "n": 0,
                        "type": 1,
                        "redeem_detail": {
                            "tx_id": "003520971b85f661e4f0a0840f93d0d224dc0593fd241f910972ebf4b5cbb090",
                            "vout": 0,
                            "sequence": "18446744073709551615",
                            "witness": [
                                "47304402207977ec8fe14e83993a7712af3c7330ba35dfe4d17977a7c005d82dbacea48c9d0220646ad239ccf5967698a834e08a54f80d950ed31140c4773347e8dc58c425bcee01",
                                "51210356830b4780dc5f5463aa91eeaa6508f93698e039fce8cf17db363ba99afd790451ae"
                            ],
                            "from_address": "ms1qqpsc4s7xllmcj48evnh77df5tg0q06tl7v0u5ul8ngk2pr5uxdyrspx4x5g",
                            "staking_address": "",
                            "binding_target": ""
                        }
                    }
                ],
                "vout": [
                    {
                        "value": "100.001",
                        "n": 0,
                        "type": 3,
                        "script_detail": {
                            "asm": "0 76c83de3af1270125e4cf2db9b2b6c80d63d1043d9d0a57875193ad9d55783ef f500020405060708010203040506070800020304",
                            "hex": "002076c83de3af1270125e4cf2db9b2b6c80d63d1043d9d0a57875193ad9d55783ef14f500020405060708010203040506070800020304",
                            "req_sigs": 1,
                            "recipient_address": "ms1qqwmyrmca0zfcpyhjv7tdek2mvsrtr6yzrm8g227r4ryadn42hs0hst2gvut",
                            "staking_address": "",
                            "binding_target": "1PLSZYeBhp6UW1MXa2DpGjdvtyXBKjdaGt:MASS:0"
                        }
                    },
                    {
                        "value": "91.9989",
                        "n": 1,
                        "type": 1,
                        "script_detail": {
                            "asm": "0 0c315878dffef12a9f2c9dfde6a68b43c0fd2ffe63f94e7cf3459411d3866907",
                            "hex": "00200c315878dffef12a9f2c9dfde6a68b43c0fd2ffe63f94e7cf3459411d3866907",
                            "req_sigs": 1,
                            "recipient_address": "ms1qqpsc4s7xllmcj48evnh77df5tg0q06tl7v0u5ul8ngk2pr5uxdyrspx4x5g",
                            "staking_address": "",
                            "binding_target": ""
                        }
                    }
                ],
                "payload": "",
                "confirmations": "381",
                "size": 270,
                "fee": "0.0001",
                "status": 1,
                "coinbase": false
            }
        """.trimIndent(), RawTransaction::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "code": 1,
                "status": "confirmed"
            }
        """.trimIndent(), TransactionStatus::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "from_address":"ms1qqgzwwzt77zuw4sf8uqugqj5w6cpk6lalas4svx6jac20kjrhywx3qnshys8",
                "staking_address":"ms1qp3fjnfxx3v2pja3gkatyrc3nzvfw52p08w4xnnuap47ey4wfg7xtq5yrwrx",
                "amount": "3000",
                "frozen_period": 65000
            }
        """.trimIndent(), CreateStakingTransactionRequest::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "txs": [
                    {
                        "tx_id": "383e5e934e20fedc7ca077a9bb789c4831ae4d6af9cae4e164c1b9741976e38c",
                        "status": 1,
                        "block_height": "177102",
                        "utxo": {
                            "tx_id": "383e5e934e20fedc7ca077a9bb789c4831ae4d6af9cae4e164c1b9741976e38c",
                            "vout": 0,
                            "address": "ms1qp3fjnfxx3v2pja3gkatyrc3nzvfw52p08w4xnnuap47ey4wfg7xtq5yrwrx",
                            "amount": "3000",
                            "frozen_period": 65000
                        }
                    }
                ],
                "weights": {}
            }
        """.trimIndent(), StakingHistory::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "details": [
                    {
                        "rank": 0,
                        "amount": "3000",
                        "weight": 19493100000000000,
                        "address": "ms1qp3fjnfxx3v2pja3gkatyrc3nzvfw52p08w4xnnuap47ey4wfg7xtq5yrwrx",
                        "profit": "61.79441473"
                    },
                    {
                        "rank": 1,
                        "amount": "2049",
                        "weight": 2309427900000000,
                        "address": "ms1qpv24szcpxphktpea9pd6caer9j4nf66jepp3qldtp6ahdu7ldxsnsp28g3u",
                        "profit": "42.20558526"
                    }
                ]
            }
        """.trimIndent(), BlockStakingReward::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "histories": [
                    {
                        "tx_id": "b7f7cab1dcb748987aa5694a6c021828cbf18f07154991467417dbe4f98e9707",
                        "block_height": "177083",
                        "inputs": [
                            {
                                "tx_id": "0054de1e7262dd1238df8283fc2cc940a038502bfb6b03ee7a761b82816f63d2",
                                "index": "9"
                            },
                            {
                                "tx_id": "00d52e0ff62c35c4c7b66e163261fd00278c5815b55375ff75552b4e4ee82db1",
                                "index": "9"
                            }
                        ],
                        "outputs": [
                            {
                                "address": "ms1qqc7773md3ux8wkha6td2q9vcxfae39xvuzgj063q4l2mwymp2h0aqunux9z",
                                "amount": "200.00000001"
                            }
                        ],
                        "from_addresses": [
                            "ms1qqgzwwzt77zuw4sf8uqugqj5w6cpk6lalas4svx6jac20kjrhywx3qnshys8"
                        ]
                    }
                ]
            }
        """.trimIndent(), TransactionHistory::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "result": {
                    "146hGPwfYRDde6tJ6trbyhkSoPwt69AqyZ": {
                        "target_type": "MASS",
                        "target_size": 0,
                        "amount": "0 MASS"
                    },
                    "14LQhx7dGPFyfRS7rYv4uKVdKjoyAJejcVVqw": {
                        "target_type": "MASS",
                        "target_size": 34,
                        "amount": "4.5776367 MASS"
                    },
                    "18gsEwbYu65Qjwz4dUtKpYqfyYawQF8yga": {
                        "target_type": "MASS",
                        "target_size": 0,
                        "amount": "100.002 MASS"
                    },
                    "1EgzSkV7vJ7xhC5g38ULLPoMBhHVW38VZN": {
                        "target_type": "MASS",
                        "target_size": 0,
                        "amount": "0 MASS"
                    }
                }
            }
        """.trimIndent(), TargetBindingResult::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "histories": [
                    {
                        "tx_id": "fe7104abbc30b56ec62c092966eccdabaec7034bf87ae1eb653572ad648902e9",
                        "status": 1,
                        "block_height": "3322",
                        "utxo": {
                            "tx_id": "fe7104abbc30b56ec62c092966eccdabaec7034bf87ae1eb653572ad648902e9",
                            "vout": 0,
                            "holder_address": "ms1qqwmyrmca0zfcpyhjv7tdek2mvsrtr6yzrm8g227r4ryadn42hs0hst2gvut",
                            "amount": "100.003",
                            "binding_target": "1PLSZYeBhp6UW1MXaBtCqesJ5oQq4YEoyc",
                            "target_type": "MASS",
                            "target_size": 0
                        },
                        "from_addresses": [
                            "ms1qqpsc4s7xllmcj48evnh77df5tg0q06tl7v0u5ul8ngk2pr5uxdyrspx4x5g"
                        ]
                    }
                ]
            }
        """.trimIndent(), BindingHistory::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "outputs":[{
                    "holder_address":"ms1qq2hr9cfgrrjekah9uy2nwsgpv5dtmckzh3vls6zgcv572k5hm5u8sd9nzjv",
                    "binding_address":"146hGPwfYRDde6tJ6trbyhkSoPwt69AqyZ",
                    "amount":"2.5"
                }],
                "from_address":"ms1qqgzwwzt77zuw4sf8uqugqj5w6cpk6lalas4svx6jac20kjrhywx3qnshys8",
                "fee": "0.001"
            }
            """.trimIndent(), CreateBindingTransactionRequest::class.qualifiedName!!
        ),
        Pair(
            """
                {
                    "hash": "a2e014a87e7388261dd46ad3cb8ba5560d054881cf3acb22b0d3ba832cebb989",
                    "chain_id": "5433524b370b149007ba1d06225b5d8e53137a041869834cff5860b02bebc5c7",
                    "version": "1",
                    "height": "10000",
                    "confirmations": "740805",
                    "time": "1567702158",
                    "previous_hash": "391f0842ad2fa8b18c47a84c9f688cd79685c61358ce3643f5b6fc979655494c",
                    "next_hash": "c31b9ad4bb038d259219ae2dc8ccbba947984e30dc12f7ae2c364ea2b89cb233",
                    "transaction_root": "2644e75e1e07dca71eff356f51fff406c637791aaac58910edb42c80b50d4390",
                    "witness_root": "2644e75e1e07dca71eff356f51fff406c637791aaac58910edb42c80b50d4390",
                    "proposal_root": "9663440551fdcd6ada50b1fa1b0003d19bc7944955820b54ab569eb9a7ab7999",
                    "target": "9ae99aa3e8b7",
                    "quality": "14438790b1cdd",
                    "challenge": "65b5c71764830dfe47a873765cb827da51aae1c38bcfceeb1ad49e1431688d76",
                    "public_key": "02c35b466747bc743d6cb9b4186fc32792959ab0835001173af1c3d0370dcdb02e",
                    "proof": {
                        "x": "0e78dbc5",
                        "x_prime": "b7585fbc",
                        "bit_length": 32
                    },
                    "block_signature": {
                        "r": "7455a07660426c8c427747a9f56a6dfdf694771b7206fa44507d2bbd736b9768",
                        "s": "7995979d051afeec267fba2e878ca0e320890952a65e8257f0c18dcc294da506"
                    },
                    "ban_list": [],
                    "proposal_area": {
                        "punishment_area": [],
                        "other_area": []
                    },
                    "raw_tx": [
                        {
                            "txid": "2644e75e1e07dca71eff356f51fff406c637791aaac58910edb42c80b50d4390",
                            "version": 1,
                            "lock_time": "0",
                            "vin": [],
                            "vout": [
                                {
                                    "value": "50.20401505",
                                    "n": 0,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 2924a0e1557fd282588aa1fb493ae37b9eb45d50b7e8bb6902f3763753e871b8",
                                        "hex": "00202924a0e1557fd282588aa1fb493ae37b9eb45d50b7e8bb6902f3763753e871b8",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qq9yj2pc240lfgyky258a5jwhr0w0tgh2skl5tk6gz7dmrw5lgwxuqf4dltz"
                                        ]
                                    }
                                },
                                {
                                    "value": "49.79348849",
                                    "n": 1,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 e5125049d6929dc5f42b3a1c58c0a7a5ca1d12c2db6b734d6eee129705290f3a",
                                        "hex": "0020e5125049d6929dc5f42b3a1c58c0a7a5ca1d12c2db6b734d6eee129705290f3a",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqu5f9qjwkj2wutapt8gw93s985h9p6ykzmd4hxntwacffwpffpuaq499cf5"
                                        ]
                                    }
                                },
                                {
                                    "value": "49.19004188",
                                    "n": 2,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 8a6ac1d090a01262f1bdc25d075541214b1e067a295fb96d3bbe9382e8a09e7c",
                                        "hex": "00208a6ac1d090a01262f1bdc25d075541214b1e067a295fb96d3bbe9382e8a09e7c",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qq3f4vr5ys5qfx9udacfwsw42py993upn6990mjmfmh6fc969qne7q007qgg"
                                        ]
                                    }
                                },
                                {
                                    "value": "48.7008844",
                                    "n": 3,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 2e86cb356002b17f8e4f9c627efa0d3987d4cd609f2c74a0e56355e89ba7248a",
                                        "hex": "00202e86cb356002b17f8e4f9c627efa0d3987d4cd609f2c74a0e56355e89ba7248a",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qq96rvkdtqq2chlrj0n338a7sd8xrafntqnuk8fg89vd273xa8yj9qf9efyg"
                                        ]
                                    }
                                },
                                {
                                    "value": "45.71473799",
                                    "n": 4,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 2e90cfed85f9bc5cd0f2ca15ea421bf9328b4a99bafefc2dec1ad54873caf9c0",
                                        "hex": "00202e90cfed85f9bc5cd0f2ca15ea421bf9328b4a99bafefc2dec1ad54873caf9c0",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qq96gvlmv9lx79e58jeg275ssmlyegkj5ehtl0ct0vrt25su72l8qqw9q548"
                                        ]
                                    }
                                },
                                {
                                    "value": "41.10751455",
                                    "n": 5,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 db6b1546b7e8bf1819894d767c119acbe78466b7dd80e02fc572439804ab594c",
                                        "hex": "0020db6b1546b7e8bf1819894d767c119acbe78466b7dd80e02fc572439804ab594c",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqmd43234hazl3sxvff4m8cyv6e0ncge4hmkqwqt79wfpesp9tt9xqanrnr2"
                                        ]
                                    }
                                },
                                {
                                    "value": "37.14671044",
                                    "n": 6,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 ec74c6742eeb505fccb98d08a178b2253d981a4b199f929155e88a6ad16f43fc",
                                        "hex": "0020ec74c6742eeb505fccb98d08a178b2253d981a4b199f929155e88a6ad16f43fc",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqa36vvapwadg9ln9e35y2z79jy57esxjtrx0e9y24az9x45t0g07qcfv2qr"
                                        ]
                                    }
                                },
                                {
                                    "value": "36.57252184",
                                    "n": 7,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 0776d270ba9842944c32cade52a2d8991870c056cecb7c7a206a837048193fc4",
                                        "hex": "00200776d270ba9842944c32cade52a2d8991870c056cecb7c7a206a837048193fc4",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqqamdyu96nppfgnpjet099gkcnyv8pszkem9hc73qd2phqjqe8lzq8y9tn2"
                                        ]
                                    }
                                },
                                {
                                    "value": "36.38965923",
                                    "n": 8,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 66367d146b1165ebd6bae3c52d408e44dff27e05d38ebf1f1b2655f595427f36",
                                        "hex": "002066367d146b1165ebd6bae3c52d408e44dff27e05d38ebf1f1b2655f595427f36",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqvcm869rtz9j7h446u0zj6sywgn0lyls96w8t78cmye2lt92z0umqx5a4mq"
                                        ]
                                    }
                                },
                                {
                                    "value": "35.57774925",
                                    "n": 9,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 5c69278b61b94c2ba3bfc6f84cd2d18279fd63f94438f4d9c6f8be9c2c09b101",
                                        "hex": "00205c69278b61b94c2ba3bfc6f84cd2d18279fd63f94438f4d9c6f8be9c2c09b101",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqt35j0zmph9xzhgalcmuye5k3sful6clegsu0fkwxlzlfctqfkyqslxkr4y"
                                        ]
                                    }
                                },
                                {
                                    "value": "34.54000394",
                                    "n": 10,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 ac03c642537f7ced93edaf9dad2a0348257315ad2cbfd59f03a360c0d70f1dd2",
                                        "hex": "0020ac03c642537f7ced93edaf9dad2a0348257315ad2cbfd59f03a360c0d70f1dd2",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qq4spuvsjn0a7wmyld47w662srfqjhx9dd9jlat8cr5dsvp4c0rhfqr90q0h"
                                        ]
                                    }
                                },
                                {
                                    "value": "29.30830469",
                                    "n": 11,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 716b35f9e36fb8b9a9fbf3cfe0f1fb952f61277f00888e3039537a2d6807219b",
                                        "hex": "0020716b35f9e36fb8b9a9fbf3cfe0f1fb952f61277f00888e3039537a2d6807219b",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqw94nt70rd7utn20m7087pu0mj5hkzfmlqzyguvpe2daz66q8yxdsljzngn"
                                        ]
                                    }
                                },
                                {
                                    "value": "28.87421812",
                                    "n": 12,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 f9a8490dd2c9832f5d3c8535da81617ae00d60727cb5a2d688c29a17481c17ff",
                                        "hex": "0020f9a8490dd2c9832f5d3c8535da81617ae00d60727cb5a2d688c29a17481c17ff",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqlx5yjrwjexpj7hfus56a4qtp0tsq6crj0j66945gc2dpwjquzllsvatggv"
                                        ]
                                    }
                                },
                                {
                                    "value": "28.80086095",
                                    "n": 13,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 ecd9869679694103ec60e0084d9362a666b03f8d47c3e89c237b61854b2b7da7",
                                        "hex": "0020ecd9869679694103ec60e0084d9362a666b03f8d47c3e89c237b61854b2b7da7",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqanvcd9ned9qs8mrquqyymymz5entq0udglp738pr0dsc2jet0knsszzp3c"
                                        ]
                                    }
                                },
                                {
                                    "value": "28.06621042",
                                    "n": 14,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 1d4d5cca10a52431dad17a705c27b26a1818756e111493c10a26a7062efe7b13",
                                        "hex": "00201d4d5cca10a52431dad17a705c27b26a1818756e111493c10a26a7062efe7b13",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqr4x4ejss55jrrkk30fc9cfajdgvpsatwzy2f8sg2y6nsvth70vfsmywrzc"
                                        ]
                                    }
                                },
                                {
                                    "value": "27.42939138",
                                    "n": 15,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 598804cada1384f3b41448c5867ce91a8745dabc3736efcdaeea4d108808f954",
                                        "hex": "0020598804cada1384f3b41448c5867ce91a8745dabc3736efcdaeea4d108808f954",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqtxyqfjk6zwz08dq5frzcvl8fr2r5tk4uxumwlndwafx3pzqgl92q9z78n3"
                                        ]
                                    }
                                },
                                {
                                    "value": "27.42847707",
                                    "n": 16,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 a41b202370bbfee42959880602021b96226648d1d92d1b3e2d784e0ea40bc526",
                                        "hex": "0020a41b202370bbfee42959880602021b96226648d1d92d1b3e2d784e0ea40bc526",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qq5sdjqgmsh0lwg22e3qrqyqsmjc3xvjx3myk3k03d0p8qafqtc5nq3hxzsq"
                                        ]
                                    }
                                },
                                {
                                    "value": "26.38707451",
                                    "n": 17,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 cef84c79e45e6374766f2cd0e64a24146641db983cfd8454436263a113280781",
                                        "hex": "0020cef84c79e45e6374766f2cd0e64a24146641db983cfd8454436263a113280781",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqemuyc70yte3hgan09ngwvj3yz3nyrkuc8n7cg4zrvf36zyegq7qs9npxv9"
                                        ]
                                    }
                                },
                                {
                                    "value": "23.30201771",
                                    "n": 18,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 884c5bc44925ce47b1c6782b140ef7b03ec2a62d5d0c5f8704ac1be38b9bb3d6",
                                        "hex": "0020884c5bc44925ce47b1c6782b140ef7b03ec2a62d5d0c5f8704ac1be38b9bb3d6",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qq3px9h3zfyh8y0vwx0q43grhhkqlv9f3dt5x9lpcy4sd78zumk0tq6c0cz7"
                                        ]
                                    }
                                },
                                {
                                    "value": "20.2611771",
                                    "n": 19,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 a2f9f2583b6ce426925a798fc523dceb1650555e7edc68e19b1edf68e5ca1fb7",
                                        "hex": "0020a2f9f2583b6ce426925a798fc523dceb1650555e7edc68e19b1edf68e5ca1fb7",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qq5tulykpmdnjzdyj60x8u2g7uavt9q4270mwx3cvmrm0k3ew2r7mscl3252"
                                        ]
                                    }
                                },
                                {
                                    "value": "19.18960221",
                                    "n": 20,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 0ff26d43dbb689d88f2d1ff69c4e10fab553c2cdd544417566849ff9fd22edf0",
                                        "hex": "00200ff26d43dbb689d88f2d1ff69c4e10fab553c2cdd544417566849ff9fd22edf0",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqplex6s7mk6ya3redrlmfcnssl2648skd64zyzatxsj0lnlfzahcqcs225x"
                                        ]
                                    }
                                },
                                {
                                    "value": "17.4999517",
                                    "n": 21,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 8da3b8464cb706d554538307050185e1bf72e6744cbbaef0e5369dce63941d23",
                                        "hex": "00208da3b8464cb706d554538307050185e1bf72e6744cbbaef0e5369dce63941d23",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qq3k3ms3jvkurd24znsvrs2qv9uxlh9en5fja6au89x6wuucu5r53s24q7yt"
                                        ]
                                    }
                                },
                                {
                                    "value": "15.43360422",
                                    "n": 22,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 58798602c98693eac3d038f135c39f1a704b13ab78ea1ecd494a986de7a3b296",
                                        "hex": "002058798602c98693eac3d038f135c39f1a704b13ab78ea1ecd494a986de7a3b296",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqtpucvqkfs6f74s7s8rcntsulrfcykyat0r4pan2ff2vxmeark2tq0te9r9"
                                        ]
                                    }
                                },
                                {
                                    "value": "14.26328352",
                                    "n": 23,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 d893b43ea69e22a63c41312339be312c743cf8f8afd493034923f83b3e741683",
                                        "hex": "0020d893b43ea69e22a63c41312339be312c743cf8f8afd493034923f83b3e741683",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqmzfmg04xnc32v0zpxy3nn033936re78c4l2fxq6fy0urk0n5z6psd2nc0w"
                                        ]
                                    }
                                },
                                {
                                    "value": "12.80038264",
                                    "n": 24,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 101911496c4d8d76da791b232b0e2a5cef51cb1f57cc38ea91237213653921c3",
                                        "hex": "0020101911496c4d8d76da791b232b0e2a5cef51cb1f57cc38ea91237213653921c3",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqzqv3zjtvfkxhdknerv3jkr32tnh4rjcl2lxr3653ydepxefey8psmy9cz5"
                                        ]
                                    }
                                },
                                {
                                    "value": "10.97175655",
                                    "n": 25,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 4fb1654024bd62151e51e953f3b76f5401be17906a970b8236ca063190c90e70",
                                        "hex": "00204fb1654024bd62151e51e953f3b76f5401be17906a970b8236ca063190c90e70",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqf7ck2spyh43p28j3a9fl8dm02sqmu9usd2tshq3kegrrryxfpecq9386vl"
                                        ]
                                    }
                                },
                                {
                                    "value": "9.59114385",
                                    "n": 26,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 5f4200521ea94fd86f9b9abdb2d771711703ea95c29e14b5702a137ead6ddd96",
                                        "hex": "00205f4200521ea94fd86f9b9abdb2d771711703ea95c29e14b5702a137ead6ddd96",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqtapqq5s7498asmumn27m94m3wyts8654c20pfdts9gfhattdmktqp2j7jt"
                                        ]
                                    }
                                },
                                {
                                    "value": "9.18701748",
                                    "n": 27,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 db98a0a191a92a6b1d1fc3196dcaf1b56a8d57262c0fcd7ff3dfdab41c789a7f",
                                        "hex": "0020db98a0a191a92a6b1d1fc3196dcaf1b56a8d57262c0fcd7ff3dfdab41c789a7f",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqmwv2pgv34y4xk8glcvvkmjh3k44g64ex9s8u6llnmldtg8rcnfls20k8mt"
                                        ]
                                    }
                                },
                                {
                                    "value": "9.14313046",
                                    "n": 28,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 82adcc674fc9cadfbfe7b9f37e97654437b5951f6df14417500dfe0474ee7aa6",
                                        "hex": "002082adcc674fc9cadfbfe7b9f37e97654437b5951f6df14417500dfe0474ee7aa6",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqs2kuce60e89dl0l8h8eha9m9gsmmt9gldhc5g96sphlqga8w02nq86lq99"
                                        ]
                                    }
                                },
                                {
                                    "value": "9.1250682",
                                    "n": 29,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 330fbdb6b46f700795bb0b23ccaa99bdb0f978d010aa092dbc88587560475784",
                                        "hex": "0020330fbdb6b46f700795bb0b23ccaa99bdb0f978d010aa092dbc88587560475784",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qqxv8mmd45dacq09dmpv3ue25ehkc0j7xszz4qjtdu3pv82cz827zq0lwdmu"
                                        ]
                                    }
                                },
                                {
                                    "value": "192.00000016",
                                    "n": 30,
                                    "type": 1,
                                    "script_detail": {
                                        "asm": "0 f36de9ba772fa487f30e0f3d5b9b09c3ed778f6dfc475a7d66fc481ecec055f0",
                                        "hex": "0020f36de9ba772fa487f30e0f3d5b9b09c3ed778f6dfc475a7d66fc481ecec055f0",
                                        "req_sigs": 1,
                                        "addresses": [
                                            "ms1qq7dk7nwnh97jg0ucwpu74hxcfc0kh0rmdl3r45ltxl3ypankq2hcqsntrlq"
                                        ]
                                    }
                                }
                            ],
                            "payload": "10270000000000001e000000",
                            "confirmations": "1",
                            "size": 1370,
                            "fee": "0",
                            "status": 1,
                            "type": 4
                        }
                    ],
                    "size": 2707,
                    "time_utc": "2019-09-05T16:49:18Z",
                    "tx_count": 1
                }
            """.trimIndent(), Block::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "height": "3691",
                "total_binding": "0 MASS",
                "binding_price_mass_bitlength": {
                    "32": "0.91552734 MASS",
                    "34": "4.5776367 MASS",
                    "36": "18.3105468 MASS",
                    "38": "73.2421872 MASS",
                    "40": "292.9687488 MASS"
                },
                "binding_price_chia_k": {
                    "32": "3.66210936 MASS",
                    "33": "7.32421872 MASS",
                    "34": "15.56396478 MASS",
                    "35": "32.0434569 MASS",
                    "36": "66.83349582 MASS",
                    "37": "137.329101 MASS",
                    "38": "281.98242072 MASS",
                    "39": "578.61327888 MASS",
                    "40": "1186.52343264 MASS"
                }
            }
        """.trimIndent(), NetworkBinding::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "pool_pubkeys": ["8919b3715c0e8998c5d2f36f1236c7ab0d44b8285644effe2ee0d9f54a6dadf0efc6bbd0917371b2e9462186ac99c948", "7719b3715c0e8998c5d2f36f1236c7ab0d44b8285644effe2ee0d9f54a6dadf0efc6bbd0917371b2e9462186ac99c948","97d5be5d8612daf12a1658afe2ed2b8e708bb1d4128d0f31d71fa1272eff3ee66a4edec12aaae0e4f0a3d4421e2624c4"]
            }
        """.trimIndent(), PoolPks::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "result": {
                    "8919b3715c0e8998c5d2f36f1236c7ab0d44b8285644effe2ee0d9f54a6dadf0efc6bbd0917371b2e9462186ac99c948": {
                        "nonce": 6,
                        "coinbase": "ms1qq2gyvfzkhdpnafyhedcm3syvla5ntzhdz2zw69nf65v5yw35zy2ysc7s6vt"
                    },
                    "7719b3715c0e8998c5d2f36f1236c7ab0d44b8285644effe2ee0d9f54a6dadf0efc6bbd0917371b2e9462186ac99c948": {
                        "nonce": 0,
                        "coinbase": ""
                    },
                    "97d5be5d8612daf12a1658afe2ed2b8e708bb1d4128d0f31d71fa1272eff3ee66a4edec12aaae0e4f0a3d4421e2624c4": {
                        "nonce": 9,
                        "coinbase": ""
                    }
                }
            }
        """.trimIndent(), PoolPkCoinbase::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "from_address":"ms1qqpsc4s7xllmcj48evnh77df5tg0q06tl7v0u5ul8ngk2pr5uxdyrspx4x5g",
                "payload": "00018919b3715c0e8998c5d2f36f1236c7ab0d44b8285644effe2ee0d9f54a6dadf0efc6bbd0917371b2e9462186ac99c94886188f7aeadf4c58662869557a9dd3ccba6f04ccad4841d7cf1dfae08024f844356b9b41b183a27e1794a6a52fbc2cca08fa6d1ae6c63bff3e01637a4cc74a0f14e0e09a63d4d9728c8506ee376baf5d6af961344691272cf4da079b439470365208c48ad76867d492f96e3718119fed26b15da2509da2cd3aa3284746822289"
            }
        """.trimIndent(), AddressPayload::class.qualifiedName!!
        ),
    )

    @Test
    fun `parsing API models`() {
        for ((json, className) in rawResults) {
            println(gson.fromJson(json, Class.forName(className)))
        }
    }
}
