package app.tier.di

import android.content.Context
import app.tier.R
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor constructor(
    private val context: Context
) : Interceptor {

    //
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .addHeader(HEADER_CONTENT_TYPE_KEY, HEADER_CONTENT_TYPE_VALUE)
            .addHeader(HEADER_SECRET_KEY, context.getString(R.string.secret_key))

        val newRequest = request.build()
        return chain.proceed(newRequest)
    }

    companion object {
        const val HEADER_CONTENT_TYPE_KEY = "Content-Type"
        const val HEADER_SECRET_KEY = "secret-key"
        const val HEADER_CONTENT_TYPE_VALUE = "application/json; charset=utf-8"
    }
}
