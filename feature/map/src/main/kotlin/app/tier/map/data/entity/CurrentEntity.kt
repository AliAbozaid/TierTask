package app.tier.map.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentEntity(
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
    @Json(name = "fleetbirdId") val fleetBirdId: Int?,
    val latitude: Double?,
    val longitude: Double?
)
