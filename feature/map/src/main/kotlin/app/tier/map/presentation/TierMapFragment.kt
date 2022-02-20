package app.tier.map.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import app.tier.map.R
import app.tier.utils.ResourceUi
import app.tier.utils.launchAndRepeatOnStart
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class TierMapFragment : Fragment(R.layout.fragment_tier_map) {

    // 	private val binding by viewBinding(FragmentTierMapBinding::bind)
    private var googleMap: GoogleMap? = null
    private var mapFragment: SupportMapFragment? = null
    private val viewModel by viewModel<TierMapViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment?.getMapAsync {
            googleMap = it
        }
        bindObservers()
    }

    private fun bindObservers() {
        launchAndRepeatOnStart {
            viewModel.vehiclesStateFlow.collect { resource ->
                when (resource) {
                    ResourceUi.Loading -> {
                        Log.d("VEHICLES_RESULT", "Loading")
                    }
                    is ResourceUi.Failure -> {
                        Log.d("VEHICLES_RESULT", "${resource.error}")
                    }
                    is ResourceUi.Success -> {
                        Log.d("VEHICLES_RESULT", "${resource.data}")
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        googleMap?.clear()
        mapFragment?.onDestroy()
        super.onDestroy()
    }
}
