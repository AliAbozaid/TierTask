package app.tier.map.domain

import app.tier.map.domain.model.Current
import app.tier.map.domain.repo.MapRepository
import app.tier.utils.Resource

class MapUseCase constructor(
    private val mapRepository: MapRepository
) {

    suspend fun getTierVehicles(): Resource<List<Current>> = try {
        val vehicles = mapRepository.getTierVehicles()
        Resource.Success(vehicles)
    } catch (e: Throwable) {
        Resource.Error(e)
    }
}
