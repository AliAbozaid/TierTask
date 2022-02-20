package app.tier.map.data.mapper

import app.tier.map.data.entity.CurrentEntity
import app.tier.map.domain.model.Current
import com.google.android.gms.maps.model.LatLng

fun CurrentEntity.mapToCurrent(): Current =
    Current(
        id = id,
        vehicleId = vehicleId,
        hardwareId = hardwareId,
        zoneId = zoneId,
        resolution = resolution,
        resolvedBy = resolvedBy,
        resolvedAt = resolvedAt,
        battery = battery,
        state = state,
        model = model,
        fleetBirdId = fleetBirdId,
        position = LatLng(latitude ?: 0.0, longitude ?: 0.0)
    )
