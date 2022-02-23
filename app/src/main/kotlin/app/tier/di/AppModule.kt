package app.tier.di

import app.tier.log.CrashlyticsLoggerImpl
import app.tier.map.presentation.LocationRetriever
import app.tier.utils.CrashlyticsLogger
import app.tier.utils.Dispatcher
import app.tier.utils.DispatcherImpl
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { DispatcherImpl() } bind Dispatcher::class
    single {
        CrashlyticsLoggerImpl(
            FirebaseCrashlytics.getInstance()
        )
    } bind CrashlyticsLogger::class
    single { LocationRetriever(androidContext()) }
}
