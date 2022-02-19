package app.tier.map.data.mapper

import app.tier.map.data.entity.CurrentEntity
import app.tier.map.domain.model.Current

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
        latitude = latitude,
        longitude = longitude
    )
