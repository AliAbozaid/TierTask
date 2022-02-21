package app.tier.map.presentation.cluster

import android.content.Context
import app.tier.map.R
import app.tier.utils.bitmapDescriptorFromVector
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class MarkerClusterRenderer constructor(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<VehicleClusterItem>
) : DefaultClusterRenderer<VehicleClusterItem>(
    context, map, clusterManager
) {

    override fun onBeforeClusterItemRendered(
        item: VehicleClusterItem,
        markerOptions: MarkerOptions
    ) {
        val icon = context.bitmapDescriptorFromVector(
            R.drawable.ic_scooter,
            R.color.ic_marker_color
        )
        markerOptions
            .icon(icon)
            .title(item.title)
            .snippet(item.snippet)
            .position(
                LatLng(
                    item.position.latitude,
                    item.position.longitude
                )
            )
        super.onBeforeClusterItemRendered(item, markerOptions)
    }

    override fun shouldRenderAsCluster(cluster: Cluster<VehicleClusterItem>): Boolean {
        return cluster.size > 1
    }
}
