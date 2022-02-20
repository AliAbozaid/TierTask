package app.tier.utils

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat

fun Context.isPermissionGranted(permissions: Array<String>): Boolean {
	return permissions.all {
		ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
	}
}

fun Context.dpValueToPx(dpValue: Int): Float = (this.resources.displayMetrics.density * dpValue)

fun Context.isLocationEnabled(): Boolean {
	if (isPermissionGranted(Permission.LOCATION.listOfPermissions).not()) {
		return false
	}
	return try {
		val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
		manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
	} catch (e: Exception) {
		false
	}
}