package app.tier.utils

import android.Manifest

enum class Permission(val listOfPermissions: Array<String>) {
	LOCATION(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)),
}