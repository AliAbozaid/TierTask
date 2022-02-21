package app.tier.map.presentation

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.tier.map.domain.MapUseCase
import app.tier.map.presentation.cluster.VehicleClusterItem
import app.tier.map.presentation.cluster.toVehicleClusterItem
import app.tier.utils.Dispatcher
import app.tier.utils.Resource
import app.tier.utils.ResourceUi
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TierMapViewModel constructor(
    private val mapUseCase: MapUseCase,
    private val dispatcher: Dispatcher
) : ViewModel() {

    private val _vehiclesStateFlow =
        MutableStateFlow<ResourceUi<List<VehicleClusterItem>>>(ResourceUi.loading())
    val vehiclesStateFlow = _vehiclesStateFlow.asStateFlow()
    var currentLocation: Location? = null

    init {
        getTierVehicles()
    }

    fun getTierVehicles() {
        viewModelScope.launch(dispatcher.io()) {
            when (val response = mapUseCase.getTierVehicles()) {
                is Resource.Success -> {
                    _vehiclesStateFlow.value = ResourceUi.success(
                        response.data.map {
                            it.toVehicleClusterItem(
                                currentLocation?.let { location ->
                                    LatLng(
                                        location.latitude,
                                        location.longitude,
                                    )
                                }
                            )
                        }
                    )
                }
                is Resource.Error -> {
                    _vehiclesStateFlow.value =
                        ResourceUi.error(response.throwable)
                }
            }
        }
    }
}
