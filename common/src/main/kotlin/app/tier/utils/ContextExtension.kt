package app.tier.utils

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.LocationManager
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

fun Context.isPermissionGranted(permissions: Array<String>): Boolean {
    return permissions.all {
        ContextCompat.checkSelfPermission(
            this,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }
}

fun Context.dpValueToPx(dpValue: Int): Float =
    (this.resources.displayMetrics.density * dpValue)

fun Context.isLocationEnabled(): Boolean {
    if (isPermissionGranted(Permission.LOCATION.listOfPermissions).not()) {
        return false
    }
    return try {
        val manager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    } catch (e: Exception) {
        false
    }
}

fun Context.bitmapDescriptorFromVector(
    resId: Int,
    resColor: Int
): BitmapDescriptor? {
    val drawable = ContextCompat.getDrawable(this, resId) ?: return null
    val tintColor = ContextCompat.getColor(this, resColor)
    drawable.setBounds(
        0,
        0,
        drawable.intrinsicWidth, drawable.intrinsicHeight
    )
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    DrawableCompat.setTint(drawable, tintColor)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
