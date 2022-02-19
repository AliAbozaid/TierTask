package app.tier.map.data.repository

import app.tier.map.BuildConfig
import app.tier.map.data.client.MapApi
import app.tier.map.data.mapper.mapToCurrent
import app.tier.map.domain.model.Current
import app.tier.map.domain.repo.MapRepository

class MapRepositoryImpl constructor(
    private val mapApi: MapApi
) : MapRepository {

    override suspend fun getTierVehicles(): List<Current> =
        mapApi.getTierVehicles(BuildConfig.PAGER_ID).data.current.map {
            it.mapToCurrent()
        }
}