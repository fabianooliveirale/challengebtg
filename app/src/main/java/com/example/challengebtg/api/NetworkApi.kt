package com.example.challengebtg.api

import com.example.challengebtg.BuildConfig
import com.example.challengebtg.model.CurrencyListResponse
import com.example.challengebtg.model.QuotesResponse
import io.reactivex.rxjava3.core.Observable
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {
    @GET("list")
    fun currencyList(
        @Query("access_key") accessKey: String = BuildConfig.ACCESSKEY
    ): Observable<CurrencyListResponse>

    @GET("live")
    fun getQuotes(
        @Query("access_key") accessKey: String = BuildConfig.ACCESSKEY
    ): Observable<QuotesResponse>

    @GET("convert")
    fun convert(
        @Query("access_key") accessKey: String = BuildConfig.ACCESSKEY,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Float
    ): Observable<QuotesResponse>

    companion object Factory {
        fun public(): NetworkApi {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val interceptor = Interceptor { chain ->
                try {
                    return@Interceptor chain.proceed(
                        chain.request().newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .build()
                    )
                } catch (x: Throwable) {
                    throw x
                }
            }

            val httpCliente =
                HttpClientBuilderFactory.create(interceptor, logInterceptor = logInterceptor)

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.URLAPI)
                .client(httpCliente)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(NetworkApi::class.java)
        }
    }
}