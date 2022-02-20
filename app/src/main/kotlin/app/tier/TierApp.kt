package app.tier

import android.app.Application
import app.tier.di.appModule
import app.tier.di.networkModule
import app.tier.map.data.di.dataModule
import app.tier.map.domain.di.domainModule
import app.tier.map.presentation.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TierApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@TierApp)
            modules(
                listOf(
                    appModule,
                    networkModule,
                    dataModule,
                    domainModule,
                    viewModelsModule
                )
            )
        }
    }
}
