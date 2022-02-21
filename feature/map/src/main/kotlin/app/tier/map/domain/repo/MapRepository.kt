package app.tier.map.domain.repo

import app.tier.model.Current

interface MapRepository {
    suspend fun getTierVehicles(): List<Current>
}
