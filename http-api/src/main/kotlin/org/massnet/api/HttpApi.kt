package org.massnet.api

import com.google.gson.JsonSyntaxException
import io.reactivex.rxjava3.core.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.*
import retrofit2.http.*
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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

    @POST("addresses/create")
    fun createAddress(@Body req: CreateAddressRequest): Single<CreateAddressResponse>

    @GET("addresses/{version}")
    fun getAddresses(@Path("version") version: Int): Single<AddressDetails>

    @POST("addresses/balance")
    fun getAddressBalance(@Body req: GetWalletBalanceRequest): Single<AddressBalances>

    @GET("addresses/{address}/validate")
    fun validateAddress(@Path("address") address: String): Single<ValidateAddressResponse>

    @POST("addresses/utxos")
    fun getUtxo(@Body req: AddressesRequest): Single<AddressUtxos>

    @POST("transactions/decode")
    fun decodeRawTransaction(@Body req: HexData): Single<DecodedTransaction>

    @POST("transactions/create")
    fun createRawTransaction(@Body req: CreateRawTransactionRequest): Single<HexData>

    @POST("transactions/create/auto")
    fun autoCreateTransaction(@Body req: AutoCreateTransactionRequest): Single<HexData>

    @POST("transactions/sign")
    fun signRawTransaction(@Body req: SignRawTransactionRequest): Single<SignRawTransactionResponse>

    @POST("transactions/fee")
    fun getTransactionFee(@Body req: CreateRawTransactionRequest): Single<TransactionFee>

    @POST("transactions/send")
    fun sendRawTransaction(@Body req: HexData): Single<TransactionId>

    @GET("transactions/{tx_id}/details")
    fun getRawTransaction(@Path("tx_id") transactionId: String): Single<TransactionId>

    @GET("transactions/{tx_id}/status")
    fun getTransactionStatus(@Path("tx_id") transactionId: String): Single<TransactionStatus>

    @POST("transactions/staking")
    fun createStakingTransaction(@Body req: CreateStakingTransactionRequest): Single<HexData>

    @GET("transactions/staking/history")
    fun getStakingHistory(): Single<StakingHistory> // excluding withdrawn

    @GET("transactions/staking/history/all")
    fun getAllStakingHistory(): Single<StakingHistory> // including withdrawn

    @GET("blocks/{height}/stakingreward")
    fun getBlockStakingReward(@Path("height") height: Int): Single<BlockStakingReward>

    @POST("transactions/history")
    fun transactionHistory(@Body req: TransactionHistoryRequest): Single<TransactionHistory>

    @POST("addresses/binding")
    fun getAddressBinding(@Body req: AddressesRequest): Single<AddressBinding>

    @GET("transactions/binding/history")
    fun getBindingHistory(): Single<BindingHistory> // excluding withdrawn

    @GET("transactions/binding/history/all")
    fun getAllBindingHistory(): Single<BindingHistory> // including withdrawn

    @POST("transactions/binding")
    fun createBindingTransaction(@Body req: CreateBindingTransactionRequest): Single<HexData>

    @POST("addresses/sk")
    fun getSk(@Body req: GetSkRequest): Single<Sk> // dangerous, for SDK only
}

class ApiException(
    val error: ApiError
): Exception("Wallet returns an error: $error")

object MassNetApiV1Impl {

    const val DEFAULT_ENDPOINT = "http://127.0.0.1:9688/v1/"
    const val DEFAULT_CONN_TIMEOUT = 300L
    const val DEFAULT_READ_TIMEOUT = 500L
    const val DEFAULT_WRITE_TIMEOUT = 500L
    const val DEFAULT_CALL_TIMEOUT = 5000L

    private fun createOkHttpClient(connTimeout: Long, readTimeout: Long,
                                   writeTimeout: Long, callTimeout: Long): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(connTimeout, TimeUnit.MILLISECONDS)
            .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
            .writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
            .callTimeout(callTimeout, TimeUnit.MILLISECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    private val interceptor by lazy {
        { chain: Interceptor.Chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            val body = response.body!!
            val json = body.string()
            if (response.code != 200) {
                // try to parse as ApiError data type
                val error = try {
                    ModelSerializer.GSON.fromJson(json, ApiError::class.java)
                } catch (e: JsonSyntaxException) {
                    ApiError("Unparsable error from wallet", json, -1, listOf(e, response))
                }
                throw ApiException(error)
            } else {
                response.newBuilder().body(json.toResponseBody(body.contentType())).build()
            }
        }
    }

    @JvmOverloads
    @JvmStatic
    fun create(baseUrl: String = DEFAULT_ENDPOINT,
               connTimeout: Long = DEFAULT_CONN_TIMEOUT, readTimeout: Long = DEFAULT_READ_TIMEOUT,
               writeTimeout: Long = DEFAULT_WRITE_TIMEOUT, callTimeout: Long = DEFAULT_CALL_TIMEOUT
    ): MassNetApiV1 {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(ModelSerializer.GSON))
            .client(createOkHttpClient(connTimeout, readTimeout, writeTimeout, callTimeout))
            .build().create(MassNetApiV1::class.java)
    }

    @JvmStatic
    val defaultService =
        create(DEFAULT_ENDPOINT)
}
