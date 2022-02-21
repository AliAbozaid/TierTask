package app.tier.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import app.tier.common.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class BatteryStatus constructor(
    @DrawableRes val resIcon: Int,
) : Parcelable {
    BATTERY_100(R.drawable.battery_100),
    BATTERY_90(R.drawable.battery_90),
    BATTERY_80(R.drawable.battery_80),
    BATTERY_70(R.drawable.battery_70),
    BATTERY_60(R.drawable.battery_60),
    BATTERY_50(R.drawable.battery_50),
    BATTERY_40(R.drawable.battery_40),
    BATTERY_30(R.drawable.battery_30),
    BATTERY_20(R.drawable.battery_20),
    BATTERY_10(R.drawable.battery_10),
    BATTERY_0(R.drawable.battery_0);

    companion object {
        private const val BATTERY_VALUE_100 = 100
        private const val BATTERY_VALUE_95 = 95
        private const val BATTERY_VALUE_85 = 85
        private const val BATTERY_VALUE_75 = 75
        private const val BATTERY_VALUE_65 = 65
        private const val BATTERY_VALUE_55 = 55
        private const val BATTERY_VALUE_45 = 45
        private const val BATTERY_VALUE_35 = 35
        private const val BATTERY_VALUE_25 = 25
        private const val BATTERY_VALUE_15 = 15
        private const val BATTERY_VALUE_5 = 5
        private const val BATTERY_VALUE_0 = 0

        fun getBatterStatus(battery: Int): BatteryStatus =
            when (battery) {
                in BATTERY_VALUE_95..BATTERY_VALUE_100 -> {
                    BATTERY_100
                }
                in BATTERY_VALUE_85 until BATTERY_VALUE_95 -> {
                    BATTERY_90
                }
                in BATTERY_VALUE_75 until BATTERY_VALUE_85 -> {
                    BATTERY_80
                }
                in BATTERY_VALUE_65 until BATTERY_VALUE_75 -> {
                    BATTERY_70
                }
                in BATTERY_VALUE_55 until BATTERY_VALUE_65 -> {
                    BATTERY_60
                }
                in BATTERY_VALUE_45 until BATTERY_VALUE_55 -> {
                    BATTERY_50
                }
                in BATTERY_VALUE_35 until BATTERY_VALUE_45 -> {
                    BATTERY_40
                }
                in BATTERY_VALUE_25 until BATTERY_VALUE_35 -> {
                    BATTERY_30
                }
                in BATTERY_VALUE_15 until BATTERY_VALUE_25 -> {
                    BATTERY_20
                }
                in BATTERY_VALUE_5 until BATTERY_VALUE_15 -> {
                    BATTERY_10
                }
                in BATTERY_VALUE_0 until BATTERY_VALUE_5 -> {
                    BATTERY_0
                }
                else -> {
                    BATTERY_0
                }
            }
    }
}
