package app.tier.map.data.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StatsEntity(
    val open: Int,
    val assigned: Int,
    val resolved: Int
)
