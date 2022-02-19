package app.tier.map.presentation

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import app.tier.map.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment

class TierMapFragment : Fragment(R.layout.fragment_tier_map) {

// 	private val binding by viewBinding(FragmentTierMapBinding::bind)
    private var googleMap: GoogleMap? = null
    private var mapFragment: SupportMapFragment? = null

    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
            } else -> {
                // No location access granted.
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment?.getMapAsync {
            googleMap = it
        }
    }

    override fun onDestroy() {
        googleMap?.clear()
        mapFragment?.onDestroy()
        super.onDestroy()
    }
}
