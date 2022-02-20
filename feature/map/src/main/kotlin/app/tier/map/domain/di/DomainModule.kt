package app.tier.map.domain.di

import app.tier.map.domain.MapUseCase
import org.koin.dsl.module

val domainModule = module {
    single {
        MapUseCase(
            mapRepository = get()
        )
    }
}
