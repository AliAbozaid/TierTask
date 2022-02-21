package app.tier.map.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentSender
import android.location.Location
import android.os.Looper
import app.tier.utils.ResourceUi
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes

class LocationRetriever(context: Context) {

    private val locationRequest = LocationRequest().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = INTERVAL
        fastestInterval = FAST_INTERVAL
    }

    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val builder: LocationSettingsRequest =
        LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()
    private val settingsClient = LocationServices.getSettingsClient(context)
    lateinit var startSettingActivity: (ResolvableApiException) -> Unit
    var onLocationFound: ((ResourceUi<Location>) -> Unit)? = null

    /**
     * This is the main method to start getting user location
     */
    fun requestUserLocation() {
        onLocationFound?.invoke(ResourceUi.loading())
        checkLocationSettings()
    }

    /**
     *
     */
    private fun checkLocationSettings() {
        settingsClient.checkLocationSettings(builder).addOnCompleteListener {
            try {
                it.getResult(ApiException::class.java)
                startGetUserLocation()
            } catch (exception: ApiException) {
                handleSettingApeException(exception)
            }
        }
    }

    private fun handleSettingApeException(exception: ApiException) {
        when (exception.statusCode) {
            LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                try {
                    startSettingActivity(exception as ResolvableApiException)
                } catch (e: IntentSender.SendIntentException) {
                    onRetrieveLocationFailed(e)
                } catch (e: ClassCastException) {
                    onRetrieveLocationFailed(e)
                }
            }
            LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> onRetrieveLocationFailed(
                Throwable(SETTINGS_CHANGE_UNAVAILABLE_MESSAGE)
            )
        }
    }

    fun onRetrieveLocationFailed(t: Throwable) {
        onLocationFound?.invoke(ResourceUi.error(t))
    }

    @SuppressLint("MissingPermission") // Already checked
    fun startGetUserLocation() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            if (it != null) {
                sendLocationAndStopUpdates(it)
            } else {
                Looper.myLooper()?.let { looper ->
                    fusedLocationProviderClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        looper
                    )
                }
            }
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.locations.firstOrNull()?.let { location ->
                sendLocationAndStopUpdates(location)
            }
        }
    }

    private fun sendLocationAndStopUpdates(location: Location) {
        removeLocationUpdates()
        onLocationFound?.invoke(ResourceUi.success(location))
    }

    private fun removeLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        const val INTERVAL = 20000L
        const val FAST_INTERVAL = 5000L
        private const val SETTINGS_CHANGE_UNAVAILABLE_MESSAGE =
            "settings change is not available"
    }
}
