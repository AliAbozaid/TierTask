package app.tier.map.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.tier.details.ScooterDetailFragmentArgs
import app.tier.map.R
import app.tier.map.databinding.FragmentTierMapBinding
import app.tier.map.presentation.cluster.MapBoundMeasurements
import app.tier.map.presentation.cluster.MarkerClusterRenderer
import app.tier.map.presentation.cluster.VehicleClusterItem
import app.tier.model.Scooter
import app.tier.utils.Permission
import app.tier.utils.ResourceUi
import app.tier.utils.bitmapDescriptorFromVector
import app.tier.utils.isPermissionGranted
import app.tier.utils.launchAndRepeatOnStart
import app.tier.utils.showErrorSnackBar
import app.tier.utils.viewBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.algo.NonHierarchicalDistanceBasedAlgorithm
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TierMapFragment : Fragment(R.layout.fragment_tier_map) {

    private val binding by viewBinding(FragmentTierMapBinding::bind)
    private var googleMap: GoogleMap? = null
    private var mapFragment: SupportMapFragment? = null
    private val viewModel by viewModel<TierMapViewModel>()
    private val locationHelper: LocationRetriever by inject()
    private lateinit var clusterManager: ClusterManager<VehicleClusterItem>
    private lateinit var clusterRenderer: MarkerClusterRenderer

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                requestUserLocation()
            } else {
                showLocationSnackBar()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment?.getMapAsync {
            googleMap = it
            setupCluster(it)
            bindObservers()
            relocateGoogleWaterMark()
        }
        handlePermission()
        binding.location.setOnClickListener {
            viewModel.currentLocation?.let {
                zoom(LatLng(it.latitude, it.longitude), MAP_ZOOM_LEVEL)
            }
        }
    }

    override fun onDestroy() {
        googleMap?.clear()
        mapFragment?.onDestroy()
        super.onDestroy()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> {
                if (resultCode == Activity.RESULT_OK || resultCode == Activity.RESULT_CANCELED) {
                    requestUserLocation()
                } else {
                    locationHelper.onRetrieveLocationFailed(Throwable())
                }
            }
        }
    }

    private fun setupCluster(googleMap: GoogleMap) {
        clusterManager = ClusterManager(requireContext(), googleMap)
        clusterRenderer = MarkerClusterRenderer(
            requireContext(),
            googleMap,
            clusterManager
        )
        clusterManager.renderer = clusterRenderer
        clusterManager.setOnClusterClickListener { cluster ->
            zoomToBound(cluster.items.toList())
            true
        }
        clusterManager.setOnClusterItemClickListener { vehicleClusterItem ->
            val marker = clusterRenderer.getMarker(vehicleClusterItem)
            zoom(
                marker.position,
                MAP_ZOOM_LEVEL_BIG
            )
            openDetails(viewModel.getScooter(vehicleClusterItem))
            false
        }
        clusterManager.algorithm = NonHierarchicalDistanceBasedAlgorithm()
        googleMap.setOnCameraIdleListener(clusterManager)
        googleMap.setOnMarkerClickListener(clusterManager)
    }

    private fun handlePermission() {
        when {
            requireContext().isPermissionGranted(
                Permission.LOCATION.listOfPermissions
            ) -> {
                requestUserLocation()
            }
            shouldShowRequestPermissionRationale(
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                showLocationSnackBar()
            }
            else -> {
                requestLocationPermission()
            }
        }
    }

    /**
     * relocate google waterMark
     * To be visible at all times as the guideline
     */
    private fun relocateGoogleWaterMark() {
        val googleLogo =
            mapFragment?.view?.findViewWithTag<View>(TAG_GOOGLE_WATER_MARK)
        val glLayoutParams =
            googleLogo?.layoutParams as RelativeLayout.LayoutParams
        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0)
        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0)
        glLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START, 0)
        glLayoutParams.addRule(
            RelativeLayout.ALIGN_PARENT_TOP,
            RelativeLayout.TRUE
        )
        glLayoutParams.addRule(
            RelativeLayout.ALIGN_PARENT_END,
            RelativeLayout.TRUE
        )
        googleLogo.layoutParams = glLayoutParams
    }

    private fun showLocationSnackBar() {
        requireContext().showErrorSnackBar(
            container = binding.container,
            message = getString(R.string.location_permission_message),
            buttonText = R.string.snackbar_button,
            action = {
                requestLocationPermission()
            }
        )
    }

    private fun requestUserLocation() {
        locationHelper.startSettingActivity = {
            startIntentSenderForResult(
                it.resolution.intentSender,
                REQUEST_CHECK_SETTINGS,
                null,
                0,
                0,
                0,
                null
            )
        }
        locationHelper.requestUserLocation()
        locationHelper.onLocationFound = {
            when (it) {
                is ResourceUi.Failure -> {
                    binding.location.isVisible = false
                    viewModel.logRetrieveLocationException(it.error)
                    requireContext().showErrorSnackBar(
                        container = binding.container,
                        message = getString(R.string.location_gps_not_enabled),
                    )
                }
                is ResourceUi.Success -> {
                    binding.location.isVisible = true
                    viewModel.currentLocation = it.data
                    addMyLocationMarker(it.data)
                    zoom(
                        LatLng(it.data.latitude, it.data.longitude),
                        MAP_ZOOM_LEVEL
                    )
                }
            }
        }
    }

    private fun zoomToBound(list: List<VehicleClusterItem>) {
        val builder = LatLngBounds.builder()
        list.forEach { item ->
            builder.include(item.position)
        }
        val bounds = builder.build()
        val measurements =
            MapBoundMeasurements(requireContext())

        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                measurements.padding
            )
        )
        googleMap?.setMinZoomPreference(MAP_ZOOM_LEVEL)
    }

    private fun zoom(latLng: LatLng, zoomLevel: Float) {
        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLng,
                zoomLevel
            )
        )
    }

    private fun addMyLocationMarker(location: Location) {
        val icon = requireContext().bitmapDescriptorFromVector(
            R.drawable.ic_current_gps,
            R.color.ic_marker_color
        )
        googleMap?.addMarker(
            MarkerOptions()
                .icon(icon)
                .position(
                    LatLng(
                        location.latitude,
                        location.longitude
                    )
                )
        )
    }

    private fun requestLocationPermission() {
        requestPermissionLauncher.launch(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private fun bindObservers() {
        launchAndRepeatOnStart {
            viewModel.vehiclesStateFlow.collect { resource ->
                when (resource) {
                    is ResourceUi.Failure -> {
                        requireContext().showErrorSnackBar(
                            container = binding.container,
                            message = getString(R.string.internet_connection),
                            buttonText = R.string.reload,
                            action = {
                                viewModel.getTierVehicles()
                            }
                        )
                    }
                    is ResourceUi.Success -> {
                        // TODO tracking
                        clusterManager.addItems(resource.data)
                        clusterManager.cluster()
                        if (viewModel.currentLocation == null) {
                            zoomToBound(resource.data)
                        }
                    }
                }
            }
        }
    }

    private fun openDetails(scooter: Scooter) {
        // TODO tracking
        findNavController().navigate(
            R.id.scooter_details_navigation,
            ScooterDetailFragmentArgs(
                scooter = scooter
            ).toBundle()
        )
    }

    companion object {
        private const val MAP_ZOOM_LEVEL = 13f
        private const val MAP_ZOOM_LEVEL_BIG = 18f
        private const val REQUEST_CHECK_SETTINGS = 11
        private const val TAG_GOOGLE_WATER_MARK = "GoogleWatermark"
    }
}
