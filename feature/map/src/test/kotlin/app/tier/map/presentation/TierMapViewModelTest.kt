package app.tier.map.presentation

import app.tier.map.base.DispatcherImplTest
import app.tier.map.domain.MapUseCase
import app.tier.model.BatteryStatus
import app.tier.model.Scooter
import app.tier.utils.Resource
import app.tier.utils.ResourceUi
import com.google.android.gms.maps.model.LatLng
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Test

class TierMapViewModelTest {
    private lateinit var tierMapViewModel: TierMapViewModel
    private val mapUseCase: MapUseCase = mockk(relaxed = true)
    private val dispatcher = DispatcherImplTest()

    private val currents = mutableListOf<Scooter>()
        .apply {
            add(
                Scooter(
                    id = "6348dfa0-1b20-40ed-98e9-fe9e232b6105",
                    vehicleId = "8ece0495-bef0-4eac-a58e-dede2bf975a3",
                    hardwareId = "868446031763952",
                    zoneId = "BERLIN",
                    resolution = "CLAIMED",
                    resolvedBy = "5VRiXTOvRWbWfAlIKDv10HrE8LJ2",
                    resolvedAt = "2019-10-10T06:35:21.153Z",
                    battery = 91,
                    state = "ACTIVE",
                    model = "AB",
                    fleetBirdId = 118160,
                    position = LatLng(52.506731, 13.289618),
                    batteryStatus = BatteryStatus.BATTERY_10
                )
            )
        }

    @Test
    fun `when viewModel initiated then getTierVehicles called from useCase`() {
        coEvery { mapUseCase.getTierVehicles() }.returns(
            Resource.Success(
                currents
            )
        )
        tierMapViewModel = TierMapViewModel(mapUseCase, dispatcher)
        coVerify { mapUseCase.getTierVehicles() }
    }

    @Test
    fun `given getTierVehicles called when success then it should return success`() =
        runBlockingTest {
            coEvery { mapUseCase.getTierVehicles() }.returns(
                Resource.Success(
                    currents
                )
            )
            tierMapViewModel = TierMapViewModel(mapUseCase, dispatcher)
            MatcherAssert.assertThat(
                "",
                tierMapViewModel.vehiclesStateFlow.value is ResourceUi.Success
            )
            Assert.assertEquals(
                (tierMapViewModel.vehiclesStateFlow.value as ResourceUi.Success).data.first().id,
                currents.first().id
            )
        }

    @Test
    fun `given getTierVehicles called when error then it should return error`() =
        runBlockingTest {
            coEvery { mapUseCase.getTierVehicles() }.returns(
                Resource.Error(Throwable(ERROR_MESSAGE))
            )
            tierMapViewModel = TierMapViewModel(mapUseCase, dispatcher)
            MatcherAssert.assertThat(
                "",
                tierMapViewModel.vehiclesStateFlow.value is ResourceUi.Failure
            )
            Assert.assertEquals(
                (
                    tierMapViewModel.vehiclesStateFlow.value
                        as ResourceUi.Failure
                    ).error.message,
                ERROR_MESSAGE
            )
        }

    companion object {
        private const val ERROR_MESSAGE = "Error"
    }
}
