package app.tier.map.presentation.cluster

import android.content.Context
import android.util.DisplayMetrics
import java.lang.Integer.min

class MapBoundMeasurements(
    private val context: Context
) {
    val width: Int
    val height: Int
    val padding: Int
    private val displayMetrics: DisplayMetrics =
        context.resources.displayMetrics

    init {
        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels
        val minMetric = min(width, height)
        padding = (minMetric * PADDING_VALUE).toInt()
    }
    companion object {
        private const val PADDING_VALUE = 0.10
    }
}
