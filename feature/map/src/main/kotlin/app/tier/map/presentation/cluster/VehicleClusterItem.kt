package app.tier.map.presentation.cluster

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class VehicleClusterItem constructor(
    builder: VehicleClusterItem.Builder,
    private val _position: LatLng,
    private val _title: String,
    private val _snippet: String,
    val id: String
) : ClusterItem {

    val vehicleId: String?
    val hardwareId: String?
    val zoneId: String?
    val resolution: String?
    val resolvedBy: String?
    val resolvedAt: String?
    val battery: Int?
    val state: String?
    val model: String?
    val distance: String?
    val fleetBirdId: Int?

    init {
        this.vehicleId = builder.vehicleId
        this.hardwareId = builder.hardwareId
        this.zoneId = builder.zoneId
        this.resolution = builder.resolution
        this.resolvedAt = builder.resolvedAt
        this.resolvedBy = builder.resolvedBy
        this.battery = builder.battery
        this.state = builder.state
        this.model = builder.model
        this.fleetBirdId = builder.fleetBirdId
        this.distance = builder.distance
    }

    class Builder constructor(
        val position: LatLng,
        val id: String,
        val title: String,
        val snippet: String
    ) {
        var vehicleId: String? = null
        var hardwareId: String? = null
        var zoneId: String? = null
        var resolution: String? = null
        var resolvedBy: String? = null
        var resolvedAt: String? = null
        var battery: Int? = null
        var state: String? = null
        var model: String? = null
        var distance: String? = null
        var fleetBirdId: Int? = null

        fun vehicleId(vehicleId: String) = apply { this.vehicleId = vehicleId }
        fun hardwareId(hardwareId: String) =
            apply { this.hardwareId = hardwareId }

        fun zoneId(zoneId: String) = apply { this.zoneId = title }
        fun resolution(resolution: String) =
            apply { this.resolution = resolution }

        fun resolvedBy(resolvedBy: String) =
            apply { this.resolvedBy = resolvedBy }

        fun resolvedAt(resolvedAt: String) =
            apply { this.resolvedAt = resolvedAt }

        fun battery(battery: Int) = apply { this.battery = battery }
        fun state(state: String) = apply { this.state = state }
        fun model(model: String) = apply { this.model = model }
        fun distance(distance: String?) = apply { this.distance = distance }
        fun fleetBirdId(fleetBirdId: Int) =
            apply { this.fleetBirdId = fleetBirdId }

        fun build() = VehicleClusterItem(
            builder = this,
            _position = position,
            _title = title,
            _snippet = snippet,
            id = id
        )
    }

    override fun getPosition(): LatLng = _position

    override fun getTitle(): String = _title

    override fun getSnippet(): String = _snippet
}
