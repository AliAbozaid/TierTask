package app.tier.map.data.repository

import app.tier.map.domain.model.Current

interface MapRepository {
    suspend fun getTierVehicles(): List<Current>
}
