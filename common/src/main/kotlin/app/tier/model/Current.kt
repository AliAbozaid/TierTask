package app.tier.model

import android.os.Parcelable
import app.tier.utils.Constant
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlin.math.roundToInt
import kotlinx.parcelize.Parcelize

@Parcelize
data class Current(
    val id: String,
    val vehicleId: String,
    val hardwareId: String,
    val zoneId: String,
    val resolution: String,
    val resolvedBy: String,
    val resolvedAt: String,
    val battery: Int,
    val state: String,
    val model: String,
    val fleetBirdId: Int,
    val position: LatLng,
    val batteryStatus: BatteryStatus,
) : Parcelable {
    fun getTitle(currentLocation: LatLng?): String =
        if (currentLocation == null) {
            String.format("%d %%", battery)
        } else {
            getDistance(position, currentLocation).orEmpty()
        }

    fun getDistance(position: LatLng, currentLocation: LatLng?): String? {
        if (currentLocation == null) return null
        val distance =
            SphericalUtil.computeDistanceBetween(position, currentLocation)
        return if (distance > Constant.Cluster.KM) {
            String.format("%.1f km", distance.div(Constant.Cluster.KM))
        } else {
            String.format("%d m", distance.roundToInt())
        }
    }
}
