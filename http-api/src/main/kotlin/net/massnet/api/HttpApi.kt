package net.massnet.api

import io.reactivex.rxjava3.core.*
import retrofit2.*
import retrofit2.http.*
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface MassNetApiV1 {

    @GET("blocks/best")
    fun getBestBlock(): Single<BestBlock>

    @GET("client/status")
    fun getClientStatus(): Single<ClientStatus>

    @GET("wallets")
    fun wallets(): Single<Wallets>

    @POST("wallets/create")
    fun createWallet(@Body req: CreateWalletRequest): Single<CreateWalletResponse>

    @POST("wallets/use")
    fun useWallet(@Body req: UseWalletRequest): Single<UseWalletResponse>

    @POST("wallets/import")
    fun importWallet(@Body req: ImportWalletRequest): Single<ImportWalletResponse>

    @POST("wallets/import/mnemonic")
    fun importMnemonic(@Body req: ImportMnemonicRequest): Single<ImportWalletResponse>

    @POST("wallets/export")
    fun exportWallet(@Body req: WalletRequest): Single<ExportWalletResponse>

    @POST("wallets/remove")
    fun removeWallet(@Body req: WalletRequest): Single<RemoveWalletResponse>

    @POST("wallets/mnemonic")
    fun getWalletMnemonic(@Body req: WalletRequest): Single<WalletMnemonic>

    @POST("wallets/current/balance")
    fun getWalletBalance(@Body req: GetWalletBalanceRequest): Single<WalletBalance>
}

object MassNetApiV1Impl {
    @JvmOverloads
    @JvmStatic
    fun create(baseUrl: String = "https://127.0.0.1:9000/v1/"): MassNetApiV1 {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MassNetApiV1::class.java)
    }
}
