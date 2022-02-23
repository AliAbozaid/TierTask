package app.tier.map.data.mapper

import app.tier.map.data.entity.CurrentEntity
import app.tier.model.BatteryStatus
import app.tier.model.Scooter
import com.google.android.gms.maps.model.LatLng

fun CurrentEntity.mapToCurrent(): Scooter =
    Scooter(
        id = id.orEmpty(),
        vehicleId = vehicleId.orEmpty(),
        hardwareId = hardwareId.orEmpty(),
        zoneId = zoneId.orEmpty(),
        resolution = resolution.orEmpty(),
        resolvedBy = resolvedBy.orEmpty(),
        resolvedAt = resolvedAt.orEmpty(),
        battery = battery ?: 0,
        state = state.orEmpty(),
        model = model.orEmpty(),
        fleetBirdId = fleetBirdId ?: 0,
        position = LatLng(latitude ?: 0.0, longitude ?: 0.0),
        batteryStatus = BatteryStatus.getBatterStatus(battery ?: 0)
    )
