package app.tier.map.domain.repo

import app.tier.model.Scooter

interface MapRepository {
    suspend fun getTierVehicles(): List<Scooter>
}
