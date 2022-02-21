package app.tier.map.presentation.cluster

import app.tier.model.BatteryStatus
import app.tier.model.Current
import com.google.android.gms.maps.model.LatLng

fun Current.toVehicleClusterItem(currentLocation: LatLng?): VehicleClusterItem =
    VehicleClusterItem.Builder(
        position = position,
        title = getTitle(currentLocation),
        snippet = "",
        id = id,
    )
        .vehicleId(vehicleId)
        .hardwareId(hardwareId)
        .zoneId(zoneId)
        .resolution(resolution)
        .resolvedBy(resolvedBy)
        .resolvedAt(resolvedAt)
        .battery(battery)
        .state(state)
        .model(model)
        .fleetBirdId(fleetBirdId)
        .distance(getDistance(position, currentLocation))
        .batteryStatus(batteryStatus)
        .build()

fun VehicleClusterItem.toScooter(): Current =
    Current(
        id = id,
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
        position = position,
        batteryStatus = batteryStatus ?: BatteryStatus.BATTERY_0
    )
