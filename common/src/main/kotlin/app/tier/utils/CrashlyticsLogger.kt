package app.tier.utils

interface CrashlyticsLogger {

    fun logException(throwable: Throwable?)
}
