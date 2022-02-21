package app.tier.map.presentation.cluster

import app.tier.map.domain.model.Current
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
        .build()
