package app.tier.map.data.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataEntity(
    val current: List<CurrentEntity>,
    val stats: StatsEntity,
)
