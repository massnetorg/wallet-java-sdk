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
                "tx_id": "e5b35b35510149ee98c4865285141cb0149ee98c4563825fdfedf1757d9853ee",
                "version": 1,
                "lock_time": "0",
                "size": 333,
                "vin": [
                    {
                        "tx_id": "af03d3916639143e3436d0939286c33a70752bf6bc495512dbd093c18e033bc0",
                        "vout": 1,
                        "sequence": "18446744073709551615",
                        "witness": [
                            "47304402200c32d95597e7f9df8463d42623a14f9721e737e360dd55c766b71b27fc0245d7e97a66ba1a09f0fe86a38e19ffad809065b23a141c91affb20ef45dd801",
                            "51210356830b4780dc5f54a91eea99a508f93698e039fce8cf17dba913ba99afd790451ae"
                        ]
                    }
                ],
                "vout": [
                    {
                        "value": "99930000",
                        "n": 0,
                        "type": 1,
                        "script_asm": "0 76c83de3af1270125e4cf2db5e4cf2db96c80d63d1043d9d0a57875193ad9d55783ef",
                        "script_hex": "002076c83de3d0a70125e4cf2db9b2bdb966d0a3d9d0a57875193ad9d55783ef",
                        "addresses": [
                            "ms1qqwmyrmca0zfcpyhjvmcadek2mvsrtr6yzrm8g227r4ryadn42hs0htr6yz"
                        ]
                    }
                ],
                "payload": ""
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
                "hex": "080112a4010a280a240912dd62721ede54001140c92cfc8382df3819ee036bfb2b5038a021d2636f81821b767a10091248473044022003161aa740d89984ef995103735bc6f6a0e0db76bb4eb224914bb797cf9df9ab02202765b0dd7ecb4bf5835e1a1bdce6686b26b3f6e37977668aaefdfa9a29e0a5f4011225512103d0cd7443a5e8dcc030793bea363fe328c84d2daf75f0f2db17d36c07642777b151ae19ffffffffffffffff12a4010a280a2409c4352cf60f2ed5001100fd6132166eb6c719ff7553b515588c2721b12de84e4e2b55751009124847304402205f3a8d2ea86971a7cebba0a07aeb93372732bcefc4e566e8d29009a8cc5598720220759fd2b87292cc9633f16e151d6d34e28dfbfde6b35fa329177b985f80388c14011225512103d0cd7443a5e8dcc030793bea363fe328c84d2daf75f0f2db17d36c07642777b151ae19ffffffffffffffff1a2a088190dfc04a12220020c7bde8edb1e18eeb5fba5b5402b3064f7312999c1224fd4415fab6e26c2abbfa",
                "tx_id": "b7f7cab1dcb748987aa5694a6c021828cbf18f07154991467417dbe4f98e9707",
                "version": 1,
                "lock_time": "0",
                "block": {
                    "height": "177083",
                    "block_hash": "78bd7128f00f5186e18b5b2f692b9cb49fc0abcebb9e1bb86f06b818b0c6432a",
                    "timestamp": "1574849967"
                },
                "vin": [
                    {
                        "value": "104.00000007",
                        "n": 0,
                        "type": 1,
                        "redeem_detail": {
                            "tx_id": "0054de1e7262dd1238df8283fc2cc940a038502bfb6b03ee7a761b82816f63d2",
                            "vout": 9,
                            "sequence": "18446744073709551615",
                            "witness": [
                                "473044022003161aa740d89984ef995103735bc6f6a0e0db76bb4eb224914bb797cf9df9ab02202765b0dd7ecb4bf5835e1a1bdce6686b26b3f6e37977668aaefdfa9a29e0a5f401",
                                "512103d0cd7443a5e8dcc030793bea363fe328c84d2daf75f0f2db17d36c07642777b151ae"
                            ],
                            "addresses": [
                                "ms1qqgzwwzt77zuw4sf8uqugqj5w6cpk6lalas4svx6jac20kjrhywx3qnshys8"
                            ]
                        }
                    },
                    {
                        "value": "104.00000007",
                        "n": 1,
                        "type": 1,
                        "redeem_detail": {
                            "tx_id": "00d52e0ff62c35c4c7b66e163261fd00278c5815b55375ff75552b4e4ee82db1",
                            "vout": 9,
                            "sequence": "18446744073709551615",
                            "witness": [
                                "47304402205f3a8d2ea86971a7cebba0a07aeb93372732bcefc4e566e8d29009a8cc5598720220759fd2b87292cc9633f16e151d6d34e28dfbfde6b35fa329177b985f80388c1401",
                                "512103d0cd7443a5e8dcc030793bea363fe328c84d2daf75f0f2db17d36c07642777b151ae"
                            ],
                            "addresses": [
                                "ms1qqgzwwzt77zuw4sf8uqugqj5w6cpk6lalas4svx6jac20kjrhywx3qnshys8"
                            ]
                        }
                    }
                ],
                "vout": [
                    {
                        "value": "200.00000001",
                        "n": 0,
                        "type": 1,
                        "script_detail": {
                            "asm": "0 c7bde8edb1e18eeb5fba5b5402b3064f7312999c1224fd4415fab6e26c2abbfa",
                            "hex": "0020c7bde8edb1e18eeb5fba5b5402b3064f7312999c1224fd4415fab6e26c2abbfa",
                            "req_sigs": 1,
                            "addresses": [
                                "ms1qqc7773md3ux8wkha6td2q9vcxfae39xvuzgj063q4l2mwymp2h0aqunux9z"
                            ]
                        }
                    }
                ],
                "payload": "",
                "confirmations": "6",
                "size": 360,
                "fee": "8.00000013",
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
                "amounts": {
                    "146hGPwfYRDde6tJ6trbyhkSoPwt69AqyZ": "0.026624",
                    "1EgzSkV7vJ7xhC5g38ULLPoMBhHVW38VZN": "0.026624"
                }
            }
        """.trimIndent(), AddressBinding::class.qualifiedName!!
        ),
        Pair(
            """
            {
                "histories": [
                    {
                        "tx_id": "9e4c191a29a4eb018d7904ca1cd0d6f1568356426f0a4a1c5f388c91b768d80e",
                        "status": 1,
                        "block_height": "117649",
                        "utxo": {
                            "tx_id": "9e4c191a29a4eb018d7904ca1cd0d6f1568356426f0a4a1c5f388c91b768d80e",
                            "vout": 0,
                            "holder_address": "ms1qqehh47s0hvzrqqjl77ayj78yytstjkrsltcna343p8yg7ndskvveql4z3vl",
                            "binding_address": "146hGPwfYRDde6tJ6trbyhkSoPwt69AqyZ",
                            "amount": "0.026624"
                        },
                        "from_addresses": [
                            "ms1qq20yfsypqjuz305j2nhhu8khsj07mxfq2sa8ua685l2leayk02hrsk9kjvx"
                        ]
                    },
                    {
                        "tx_id": "9e4c191a29a4eb018d7904ca1cd0d6f1568356426f0a4a1c5f388c91b768d80e",
                        "status": 1,
                        "block_height": "117649",
                        "utxo": {
                            "tx_id": "9e4c191a29a4eb018d7904ca1cd0d6f1568356426f0a4a1c5f388c91b768d80e",
                            "vout": 1,
                            "holder_address": "ms1qqehh47s0hvzrqqjl77ayj78yytstjkrsltcna343p8yg7ndskvveql4z3vl",
                            "binding_address": "1EgzSkV7vJ7xhC5g38ULLPoMBhHVW38VZN",
                            "amount": "0.026624"
                        },
                        "from_addresses": [
                            "ms1qq20yfsypqjuz305j2nhhu8khsj07mxfq2sa8ua685l2leayk02hrsk9kjvx"
                        ]
                    },
                    {
                        "tx_id": "436a2d092493590d96b4782067326c9f04fe5b4e3602203cea920c100dffb66b",
                        "status": 1,
                        "block_height": "117292",
                        "utxo": {
                            "tx_id": "436a2d092493590d96b4782067326c9f04fe5b4e3602203cea920c100dffb66b",
                            "vout": 0,
                            "holder_address": "ms1qq93pq8kphrtax7m5t52km4x84rrvplty4ttpjz27y3ve6rmhhuqys7cr2s4",
                            "binding_address": "1HWyjsiqaMuHjdMJpSxDwQ9NG1Rrca4Jjx",
                            "amount": "0.026624"
                        },
                        "from_addresses": [
                            "ms1qq20yfsypqjuz305j2nhhu8khsj07mxfq2sa8ua685l2leayk02hrsk9kjvx"
                        ]
                    },
                    {
                        "tx_id": "436a2d092493590d96b4782067326c9f04fe5b4e3602203cea920c100dffb66b",
                        "status": 1,
                        "block_height": "117292",
                        "utxo": {
                            "tx_id": "436a2d092493590d96b4782067326c9f04fe5b4e3602203cea920c100dffb66b",
                            "vout": 1,
                            "holder_address": "ms1qq93pq8kphrtax7m5t52km4x84rrvplty4ttpjz27y3ve6rmhhuqys7cr2s4",
                            "binding_address": "1MaTXJGHeXxPmDtusRtESWkcdu9RhTiu65",
                            "amount": "0.026624"
                        },
                        "from_addresses": [
                            "ms1qq20yfsypqjuz305j2nhhu8khsj07mxfq2sa8ua685l2leayk02hrsk9kjvx"
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
        )
    )

    @Test
    fun `Test parsing API models`() {
        for ((json, className) in rawResults) {
            println(gson.fromJson(json, Class.forName(className)))
        }
    }
}
