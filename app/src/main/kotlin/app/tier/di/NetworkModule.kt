package app.tier.di

import app.tier.common.BuildConfig
import app.tier.map.data.client.MapApi
import app.tier.utils.Constant
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

val networkModule = module {

    single {
        NetworkInterceptor(
            context = androidContext()
        )
    }

    single {
        val logging = HttpLoggingInterceptor.Logger { message ->
            Timber.tag("OkHttp").d(message)
        }
        val level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        HttpLoggingInterceptor(logging).apply { this.level = level }
    }

    single {
        val builder = OkHttpClient.Builder()
            .addInterceptor(get<NetworkInterceptor>())
            .writeTimeout(Constant.Network.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constant.Network.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(Constant.Network.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(get<HttpLoggingInterceptor>())
        }
        builder.build()
    }

    single<Converter.Factory> { MoshiConverterFactory.create(get()).withNullSerialization() }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(get())
            .build()
    }

    single {
        get<Retrofit>().create(MapApi::class.java)
    }
}
