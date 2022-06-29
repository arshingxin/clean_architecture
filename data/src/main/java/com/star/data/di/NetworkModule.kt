package com.star.data.di

import android.content.SharedPreferences
import android.os.Build
import com.star.data.api.AppApi
import com.star.data.api.AppApiV2
import com.star.data.customconst.PrefsConst
import com.star.data.extension.report
import okhttp3.*
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext

val networkModule = module {
    single(named(NetworkModule::class.java.simpleName)) { provideNetworkModule(get(named(PrefsConst.App.NAME))) }

    single(named(AppApi::class.java.simpleName)) { (get(named(NetworkModule::class.java.simpleName)) as NetworkModule).appApi }

    single(named(AppApiV2::class.java.simpleName)) { (get(named(NetworkModule::class.java.simpleName)) as NetworkModule).appApiV2 }
}

private fun provideNetworkModule(appSharedPreferences: SharedPreferences): NetworkModule = NetworkModule(appSharedPreferences)

interface INetworkModule {
    val repoModule: RepoModule
    val interceptorModule: InterceptorModule
    val imageOkHttpClient: OkHttpClient
    val extOkHttpClient: OkHttpClient
    val apiOkHttpClient: OkHttpClient
    val apiRetrofit: Retrofit
    val apiRetrofitV2: Retrofit
    val apiStagingRetrofit: Retrofit
    val appApi: AppApi
    val appApiV2: AppApiV2
}

private const val KEEP_ALIVE_TIMEOUT = 60000L
private const val DINGDING_HOST = "https://oapi.dingtalk.com/robot/"
private const val MAX_PARALLEL_REQUESTS = 1
private const val MAX_PARALLEL_REQUESTS_PER_HOST = 1
private const val IDLE_CONNECTION_NUMBER = 5
private const val IMAGE_KEEP_ALIVE_TIMEOUT = 25000L
private val HOST = "https://api.imojing.cc/v1/"
val BASE_HOST = HOST
val STAGING_HOST = "https://api-staging.imojing.cc/v1/"
private val HOST_2 = "https://api.imojing.cc/v2/"
val BASE_HOST_2 = HOST_2

class NetworkModule(
    private val appSharedPreferences: SharedPreferences
) : INetworkModule {
    override val repoModule: RepoModule get() = RepoModule()

    override val interceptorModule: InterceptorModule get() = InterceptorModule(appSharedPreferences)

    // define OkHttp client for fresco loading image
    override val imageOkHttpClient: OkHttpClient
        get() {
            val client = OkHttpClient.Builder()
                .connectionPool(ConnectionPool(IDLE_CONNECTION_NUMBER, IMAGE_KEEP_ALIVE_TIMEOUT, TimeUnit.MILLISECONDS))
                .retryOnConnectionFailure(false)
                .addInterceptor(interceptorModule.logInterceptor)
            return enableTls12OnPreLollipop(client).build()
        }

    // define OkHttp client for external api
    override val extOkHttpClient: OkHttpClient
        get() {
            // we don't add header interceptor for external api
            val client = OkHttpClient.Builder()
                .connectionPool(ConnectionPool(IDLE_CONNECTION_NUMBER, KEEP_ALIVE_TIMEOUT, TimeUnit.MILLISECONDS))
                .retryOnConnectionFailure(false)
                .addInterceptor(interceptorModule.logInterceptor)
            return enableTls12OnPreLollipop(client).build()
        }


    // define OkHttp client for common api access
    override val apiOkHttpClient: OkHttpClient
        get() {
            // thread dispatcher
            val dispatcher = Dispatcher()
            dispatcher.maxRequests = MAX_PARALLEL_REQUESTS
            dispatcher.maxRequestsPerHost = MAX_PARALLEL_REQUESTS_PER_HOST
            val client = OkHttpClient.Builder()
                .connectionPool(ConnectionPool(IDLE_CONNECTION_NUMBER, KEEP_ALIVE_TIMEOUT, TimeUnit.MILLISECONDS))
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .retryOnConnectionFailure(false)
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .dispatcher(dispatcher)
                .addInterceptor(interceptorModule.headerInterceptor)
                .followRedirects(false)
                .addInterceptor(interceptorModule.logInterceptor) // enable log
            return enableTls12OnPreLollipop(client).build()
        }

    private fun enableTls12OnPreLollipop(client: OkHttpClient.Builder): OkHttpClient.Builder {
        if (Build.VERSION.SDK_INT in Build.VERSION_CODES.JELLY_BEAN until Build.VERSION_CODES.LOLLIPOP_MR1) {
            try {
                val cipherSuites: MutableList<CipherSuite> = ArrayList()
                cipherSuites.add(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA)
                cipherSuites.add(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA)
                val legacyTls: ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA)
                    .build()
                val sc = SSLContext.getInstance("TLSv1.2")
                sc.init(null, null, null)
                val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(TlsVersion.TLS_1_2).build()
                val specs: List<ConnectionSpec> = listOf(cs, legacyTls, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT)
                client.connectionSpecs(specs)
            } catch (exc: Exception) {
                exc.report(NetworkModule::class.java.simpleName)
            }
        }
        return client
    }

    private fun builder(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(apiOkHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(repoModule.moshi))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    override val apiRetrofit: Retrofit
        get() = builder(BASE_HOST)

    override val apiStagingRetrofit: Retrofit
        get() = builder(STAGING_HOST)

    override val apiRetrofitV2: Retrofit
        get() = builder(BASE_HOST_2)

    override val appApi: AppApi
        get() = apiRetrofit.create(AppApi::class.java)

    override val appApiV2: AppApiV2
        get() = apiRetrofitV2.create(AppApiV2::class.java)

}
