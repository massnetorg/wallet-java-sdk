package org.massnet.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import java.math.BigInteger

object ModelSerializer {
    @JvmStatic
    val GSON = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
}

data class ApiError(
    val error: String,
    val message: String,
    val code: Int,
    val details: List<Any>
)

data class BestBlock(
    val height: Int,
    val target: String
)

data class ClientStatus(
    val peerListening: Boolean,
    val syncing: Boolean,
    val chainId: String,
    val localBestHeight: Int,
    val knownBestHeight: Int,
    val walletSyncHeight: Int,
    val peerCount: PeerCount,
    val peers: PeerList
) {
    data class PeerCount(
        val total: Int,
        val outbound: Int,
        val inbound: Int
    )

    data class PeerInfo(
        val id: String,
        val address: String,
        val direction: String
    )

    data class PeerList(
        val outbound: List<PeerInfo>,
        val inbound: List<PeerInfo>,
        val other: List<PeerInfo>
    )
}

data class Wallets(
    val wallets: List<WalletSummary>
) {
    data class WalletSummary(
        val walletId: String,
        val type: Int,
        val version: Int,
        val remarks: String,
        val status: Int,
        val statusMsg: String
    )
}

data class CreateWalletRequest(
    var passphrase: String,
    var remarks: String? = null,
    var bitSize: Int? = null
)

data class CreateWalletResponse(
    val walletId: String,
    val mnemonic: String,
    val version: Int
)

data class UseWalletRequest(
    var walletId: String
)

data class UseWalletResponse(
    val chainId: String,
    val walletId: String,
    val type: Int,
    val version: Int,
    val totalBalance: String,
    val externalKeyCount: Int,
    val internalKeyCount: Int
)

data class ImportWalletRequest(
    var keystore: String,
    var passphrase: String
)

data class ImportMnemonicRequest(
    var mnemonic: String,
    var passphrase: String,
    var remarks: String? = null,
    var externalIndex: Int? = null,
    var internalIndex: Int? = null
)

data class ImportWalletResponse(
    val ok: Boolean,
    val walletId: String,
    val type: Int,
    val version: Int,
    val remarks: String
)

data class WalletRequest(
    var walletId: String,
    var passphrase: String
)

data class ExportWalletResponse(
    val keystore: String
)

data class RemoveWalletResponse(
    val ok: Boolean
)

data class WalletMnemonic(
    val mnemonic: String
)

data class GetWalletBalanceRequest(
    var requiredConfirmations: Int,
    var detail: Boolean
)

data class WalletBalance(
    var walletId: String,
    val total: String,
    val detail: WalletBalanceDetail
) {
    data class WalletBalanceDetail(
        val spendable: String,
        val withdrawableStaking: String,
        val withdrawableBinding: String
    )
}

data class CreateAddressRequest(
    var version: Int
)

data class CreateAddressResponse(
    val address: String
)

data class AddressDetails(
    val details: List<AddressDetail>
) {
    data class AddressDetail(
        val address: String,
        val version: Int,
        val used: Boolean,
        val stdAddress: String
    )
}

data class GetAddressBalanceRequest(
    var requiredConfirmations: Int,
    var addresses: List<String>
)

data class AddressBalances(
    val balances: List<AddressBalance>
) {
    data class AddressBalance(
        val address: String,
        val total: String,
        val spendable: String,
        val withdrawableStaking: String,
        val withdrawableBinding: String
    )
}

data class ValidateAddressResponse(
    val isValid: Boolean,
    val isMine: Boolean,
    val address: String,
    val version: Int
)

data class AddressesRequest(
    var addresses: List<String>
)

data class AddressUtxos(
    val addressUtxos: List<AddressUtxo>
) {
    data class AddressUtxo(
        val address: String,
        val utxos: List<Utxo>
    ) {
        data class Utxo(
            val txId: String,
            val vout: Int,
            val amount: String,
            val blockHeight: Int,
            val maturity: Int,
            val confirmations: Int,
            val spentByUnmined: Boolean
        )
    }
}

data class HexData(
    var hex: String
)

data class DecodedTransaction(
    val txId: String,
    val version: Int,
    val lockTime: Int,
    val size: Int,
    val vin: List<Vin>,
    val vout: List<Vout>,
    val payload: String
) {
    data class Vin(
        val txId: String,
        val vout: Int,
        val sequence: BigInteger,
        val witness: List<String>
    )

    data class Vout(
        val value: String,
        val n: Int,
        val type: Int,
        val scriptAsm: String,
        val scriptHex: String,
        val addresses: List<String>
    )
}

data class CreateRawTransactionRequest(
    var inputs: List<Input>,
    var amounts: Map<String, String>,
    var lockTime: Int? = null,
    var hasBinding: Boolean? = null
) {
    data class Input(
        var txId: String,
        var vout: Int
    )
}

data class AutoCreateTransactionRequest(
    var amounts: Map<String, String>,
    var lockTime: Int? = null,
    var fee: String? = null,
    var fromAddress: String? = null
)

data class SignRawTransactionRequest(
    var rawTx: String,
    var passphrase: String,
    var flags: String? = null
)

data class SignRawTransactionResponse(
    val hex: String,
    val complete: Boolean
)

data class TransactionFee(
    val fee: String
)

data class TransactionId(
    val txId: String
)

data class RawTransaction(
    val hex: String,
    val txId: String,
    val version: Int,
    val lockTime: Int,
    val block: BlockInfo,
    val vin: List<Vin>,
    val vout: List<Vout>,
    val payload: String,
    val confirmations: Int,
    val size: Int,
    val fee: String,
    val status: Int,
    val coinbase: Boolean
) {
    data class BlockInfo(
        val height: Int,
        val blockHash: String,
        val timestamp: Int
    )

    data class Vin(
        val value: String,
        val n: Int,
        val type: Int,
        val redeemDetail: RedeemDetail
    ) {
        data class RedeemDetail(
            val txId: String,
            val vout: Int,
            val sequence: BigInteger,
            val witness: List<String>,
            val addresses: List<String>
        )
    }

    data class Vout(
        val value: String,
        val n: Int,
        val type: Int,
        val scriptDetail: ScriptDetail
    ) {
        data class ScriptDetail(
            val asm: String,
            val hex: String,
            val reqSigs: Int,
            val addresses: List<String>
        )
    }
}

data class TransactionStatus(
    val code: Int,
    val status: String
)

data class CreateStakingTransactionRequest(
    var fromAddress: String,
    var stakingAddress: String,
    var amount: String,
    var frozenPeriod: Int,
    var fee: Int? = null
)

data class StakingHistory(
    val txs: List<History>,
    val weights: Map<String, String> // FIXME: don't know what it is
) {
    data class History(
        val txId: String,
        val status: Int,
        val blockHeight: BigInteger,
        val utxo: Utxo
    ) {
        data class Utxo(
            val txId: String,
            val vout: Int,
            val address: String,
            val amount: String,
            val frozenPeriod: Int
        )
    }
}

data class BlockStakingReward(
    val height: Int,
    val details: List<RewardDetail>
) {
    data class RewardDetail(
        val rank: Int,
        val amount: String,
        val weight: BigInteger,
        val address: String,
        val profit: String
    )
}

data class TransactionHistoryRequest(
    var count: Int? = null,
    var address: String? = null
)

data class TransactionHistory(
    val histories: List<TransactionHistoryDetail>
) {
    data class TransactionHistoryDetail(
        val txId: String,
        val blockHeight: Int,
        val inputs: List<Input>,
        val outputs: List<Output>,
        val fromAddresses: List<String>
    ) {
        data class Input(
            val txId: String,
            val index: Int
        )

        data class Output(
            val address: String,
            val amount: String
        )
    }
}

data class AddressBinding(
    val amounts: Map<String, String>
)

data class BindingHistory(
    val histories: List<History>
) {
    data class History(
        val txId: String,
        val status: Int,
        val blockHeight: BigInteger,
        val utxo: Utxo,
        val fromAddresses: List<String>
    ) {
        data class Utxo(
            val txId: String,
            val vout: Int,
            val holderAddress: String,
            val bindingAddress: String,
            val amount: String
        )
    }
}

data class CreateBindingTransactionRequest(
    var outputs: List<Output>,
    var fromAddress: String? = null,
    var fee: String? = null
) {
    data class Output(
        var holderAddress: String,
        var bindingAddress: String,
        var amount: String
    )
}

data class GetSkRequest(
    var address: String,
    var passphrase: String
)

data class Sk(
    val sk: String
)
