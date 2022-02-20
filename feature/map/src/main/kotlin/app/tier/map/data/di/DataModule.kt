package app.tier.map.data.di

import app.tier.map.data.repository.MapRepositoryImpl
import app.tier.map.domain.repo.MapRepository
import org.koin.dsl.module

val dataModule = module {

    single<MapRepository> {
        MapRepositoryImpl(
            mapApi = get()
        )
    }
}
