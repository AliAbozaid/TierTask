package app.tier.di

import app.tier.utils.Dispatcher
import app.tier.utils.DispatcherImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { DispatcherImpl() } bind Dispatcher::class
}
