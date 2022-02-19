package app.tier.map.data.client

import app.tier.map.data.entity.MapEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface MapApi {

    @GET("b/{pageID}}")
    suspend fun getTierVehicles(@Path("pageID") pageID: String): MapEntity
}
