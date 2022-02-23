package app.tier.map.domain

import app.tier.map.data.repository.MapRepositoryImpl
import app.tier.model.BatteryStatus
import app.tier.model.Scooter
import app.tier.utils.Resource
import com.google.android.gms.maps.model.LatLng
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MapUseCaseTest {
    private lateinit var mapUseCase: MapUseCase
    private val mapRepository: MapRepositoryImpl = mockk(relaxed = true)

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
                    batteryStatus = BatteryStatus.BATTERY_0
                )
            )
        }

    @Before
    fun setUp() {
        mapUseCase = MapUseCase(mapRepository)
    }

    @Test
    fun `given getTierVehicles when called from usecase then it get called from client`() =
        runBlockingTest {
            mapUseCase.getTierVehicles()
            coVerify {
                mapRepository.getTierVehicles()
            }
        }

    @Test
    fun `given getTierVehicles called when success then it should return success`() =
        runBlockingTest {
            coEvery { mapRepository.getTierVehicles() }.returns(currents)
            MatcherAssert.assertThat(
                "",
                mapUseCase.getTierVehicles() is Resource.Success
            )
            Assert.assertEquals(
                (mapUseCase.getTierVehicles() as Resource.Success).data,
                currents
            )
        }

    @Test
    fun `given getTierVehicles called when error then it should return error`() =
        runBlockingTest {
            coEvery { mapRepository.getTierVehicles() }.throws(
                Throwable(
                    ERROR_MESSAGE
                )
            )
            MatcherAssert.assertThat(
                "",
                mapUseCase.getTierVehicles() is Resource.Error
            )
            Assert.assertEquals(
                (mapUseCase.getTierVehicles() as Resource.Error).throwable.message,
                ERROR_MESSAGE
            )
        }

    companion object {
        private const val ERROR_MESSAGE = "Error"
    }
}
