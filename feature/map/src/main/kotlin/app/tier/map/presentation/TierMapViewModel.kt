package app.tier.map.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.tier.map.domain.MapUseCase
import app.tier.map.domain.model.Current
import app.tier.utils.Dispatcher
import app.tier.utils.Resource
import app.tier.utils.ResourceUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TierMapViewModel constructor(
    private val mapUseCase: MapUseCase,
    private val dispatcher: Dispatcher
) : ViewModel() {

    private val _vehiclesStateFlow =
        MutableStateFlow<ResourceUi<List<Current>>>(ResourceUi.loading())
    val vehiclesStateFlow = _vehiclesStateFlow.asStateFlow()

    init {
        getTierVehicles()
    }

    private fun getTierVehicles() {
        viewModelScope.launch(dispatcher.io()) {
            when (val response = mapUseCase.getTierVehicles()) {
                is Resource.Success -> {
                    _vehiclesStateFlow.value = ResourceUi.success(response.data)
                }
                is Resource.Error -> {
                    _vehiclesStateFlow.value =
                        ResourceUi.error(response.throwable)
                }
            }
        }
    }
}
