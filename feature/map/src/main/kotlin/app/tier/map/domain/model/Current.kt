package app.tier.map.domain.model

data class Current(
    val id: String?,
    val vehicleId: String?,
    val hardwareId: String?,
    val zoneId: String?,
    val resolution: String?,
    val resolvedBy: String?,
    val resolvedAt: String?,
    val battery: Int?,
    val state: String?,
    val model: String?,
    val fleetBirdId: Int?,
    val latitude: Double?,
    val longitude: Double?
)