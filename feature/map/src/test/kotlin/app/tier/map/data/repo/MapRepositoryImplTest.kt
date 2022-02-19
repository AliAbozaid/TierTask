package app.tier.map.data.repo

import app.tier.map.data.client.MapApi
import app.tier.map.data.entity.CurrentEntity
import app.tier.map.data.entity.DataEntity
import app.tier.map.data.entity.MapEntity
import app.tier.map.data.entity.StatsEntity
import app.tier.map.data.repository.MapRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class MapRepositoryImplTest {
    private lateinit var mapRepository: MapRepositoryImpl
    private val mapApi: MapApi = mockk(relaxed = true)

    private val currents = mutableListOf<CurrentEntity>()
        .apply {
            add(
                CurrentEntity(
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
                    latitude = 52.506731,
                    longitude = 13.289618
                )
            )
        }

    private val mapEntity = MapEntity(
        DataEntity(
            current = currents,
            stats = StatsEntity(
                open = 38,
                assigned = 0,
                resolved = 113
            )
        )
    )

    @Before
    fun setUp() {
    }

    @Test
    fun `given getTierVehicles when called from repo then it get called from client`() =
        runBlockingTest {
            coEvery { mapApi.getTierVehicles(any()) }.returns(mapEntity)
            mapRepository = MapRepositoryImpl(mapApi)
            mapRepository.getTierVehicles()
            coVerify {
                mapApi.getTierVehicles(any())
            }
        }
}
