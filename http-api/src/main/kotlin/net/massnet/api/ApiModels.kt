package net.massnet.api

import com.google.gson.annotations.SerializedName

data class BestBlock(
    val height: Int,
    val target: String
)

data class ClientStatus(
    @SerializedName("peer_listening") val peerListening: Boolean,
    val syncing: Boolean,
    @SerializedName("chain_id") val chainId: String,
    @SerializedName("local_best_height") val localBestHeight: Int,
    @SerializedName("known_best_height") val knownBestHeight: Int,
    @SerializedName("wallet_sync_height") val walletSyncHeight: Int,
    @SerializedName("peer_count") val peerCount: PeerCount,
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
        @SerializedName("wallet_id") val walletId: String,
        val type: Int,
        val version: Int,
        val remarks: String,
        val status: Int,
        @SerializedName("status_msg") val statusMsg: String
    )
}

data class CreateWalletRequest(
    var passphrase: String,
    var remarks: String? = null,
    @SerializedName("bit_size") var bitSize: Int? = null
)

data class CreateWalletResponse(
    @SerializedName("wallet_id") val walletId: String,
    val mnemonic: String,
    val version: Int
)

data class UseWalletRequest(
    @SerializedName("wallet_id") var walletId: String
)

data class UseWalletResponse(
    @SerializedName("chain_id") val chainId: String,
    @SerializedName("wallet_id") val walletId: String,
    val type: Int,
    val version: Int,
    @SerializedName("total_balance") val totalBalance: String,
    @SerializedName("external_key_count") val externalKeyCount: Int,
    @SerializedName("internal_key_count") val internalKeyCount: Int
)

data class ImportWalletRequest(
    var keystore: String,
    var passphrase: String
)

data class ImportMnemonicRequest(
    var mnemonic: String,
    var passphrase: String,
    var remarks: String? = null,
    @SerializedName("external_index") var externalIndex: Int? = null,
    @SerializedName("internal_index") var internalIndex: Int? = null
)

data class ImportWalletResponse(
    val ok: Boolean,
    @SerializedName("wallet_id") val walletId: String,
    val type: Int,
    val version: Int,
    val remarks: String
)

data class WalletRequest(
    @SerializedName("wallet_id") var walletId: String,
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
    @SerializedName("required_confirmations") var requiredConfirmations: Int,
    var detail: Boolean
)

data class WalletBalance(
    @SerializedName("wallet_id") var walletId: String,
    val total: String,
    val detail: WalletBalanceDetail
) {
    data class WalletBalanceDetail(
        val spendable: String,
        @SerializedName("withdrawable_staking") val withdrawableStaking: String,
        @SerializedName("withdrawable_binding") val withdrawableBinding: String
    )
}

