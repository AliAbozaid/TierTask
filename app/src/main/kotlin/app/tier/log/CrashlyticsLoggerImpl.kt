package app.tier.log

import android.util.Log
import app.tier.utils.CrashlyticsLogger
import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException
import javax.net.ssl.SSLHandshakeException
import kotlinx.coroutines.CancellationException
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

class CrashlyticsLoggerImpl(
    private val crashlytics: FirebaseCrashlytics
) : CrashlyticsLogger {

    private val ignoredExceptions = listOf<Throwable>(
        CancellationException(),
        UnknownHostException(),
        ConnectException(),
        SocketTimeoutException(),
        HttpException(
            Response.error<Any>(
                INTERNAL_SERVER_ERROR,
                "".toResponseBody()
            )
        ),
        SSLException(""),
        SSLHandshakeException(""),
    )

    init {
        initSdks()
    }

    private fun initSdks() {
        crashlytics.setCrashlyticsCollectionEnabled(true)
    }

    override fun logException(throwable: Throwable?) {
        if (throwable == null) return
        if (ignoredExceptions.any { ignored ->
            ignored::class.java.isAssignableFrom(
                    throwable::class.java
                )
        }.not()
        ) {
            Log.e(CrashlyticsLoggerImpl::class.java.name, throwable.toString())
            crashlytics.recordException(throwable)
        }
    }

    companion object {
        private const val INTERNAL_SERVER_ERROR = 500
    }
}
