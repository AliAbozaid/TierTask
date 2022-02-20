package app.tier.map.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import app.tier.map.R
import app.tier.map.databinding.FragmentTierMapBinding
import app.tier.utils.Permission
import app.tier.utils.ResourceUi
import app.tier.utils.isPermissionGranted
import app.tier.utils.launchAndRepeatOnStart
import app.tier.utils.showErrorSnackBar
import app.tier.utils.viewBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TierMapFragment : Fragment(R.layout.fragment_tier_map) {

	private val binding by viewBinding(FragmentTierMapBinding::bind)
	private var googleMap: GoogleMap? = null
	private var mapFragment: SupportMapFragment? = null
	private val viewModel by viewModel<TierMapViewModel>()
	private val locationHelper: LocationRetriever by inject()

	private val requestPermissionLauncher =
		registerForActivityResult(
			ActivityResultContracts.RequestPermission()
		) { isGranted: Boolean ->
			if (isGranted) {
				requestUserLocation()
			} else {
				showSnackBar()
			}
		}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		mapFragment =
			childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

		mapFragment?.getMapAsync {
			googleMap = it
		}
		bindObservers()
		handlePermission()
	}

	private fun handlePermission() {
		when {
			requireContext().isPermissionGranted(Permission.LOCATION.listOfPermissions) -> {
				requestUserLocation()
			}
			shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
				showSnackBar()
			}
			else -> {
				requestLocationPermission()
			}
		}
	}

	private fun showSnackBar() {
		requireContext().showErrorSnackBar(
			container = binding.container,
			message = getString(R.string.location_permission_message),
			buttonText = R.string.snackbar_button,
			action = {
				requestLocationPermission()
			}
		)
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

	private fun requestUserLocation() {
		locationHelper.startSettingActivity = {
			Log.d("LOCATION_PERMISSION", it.toString())
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
					// TODO tracking
					requireContext().showErrorSnackBar(
						container = binding.container,
						message = getString(R.string.location_gps_not_enabled),
					)
					Log.d("LOCATION_PERMISSION", it.error.message.toString())
				}
				is ResourceUi.Success -> {
					googleMap?.animateCamera(
						CameraUpdateFactory.newLatLngZoom(
							LatLng(it.data.latitude, it.data.longitude),
							MAP_ZOOM_LEVEL
						)
					)
				}
			}
		}
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
					ResourceUi.Loading -> {
						Log.d("VEHICLES_RESULT", "Loading")
					}
					is ResourceUi.Failure -> {
						// TODO tracking
						Log.d("VEHICLES_RESULT", "${resource.error}")
					}
					is ResourceUi.Success -> {
						// TODO tracking
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

	companion object {
		private const val MAP_ZOOM_LEVEL = 13f
		private const val REQUEST_CHECK_SETTINGS = 11
	}
}
