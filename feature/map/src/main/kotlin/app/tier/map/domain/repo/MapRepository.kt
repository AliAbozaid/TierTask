package app.tier.map.domain.repo

import app.tier.map.domain.model.Current

interface MapRepository {
    suspend fun getTierVehicles(): List<Current>
}
