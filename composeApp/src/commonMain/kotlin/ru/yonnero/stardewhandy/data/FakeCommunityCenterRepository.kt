package ru.yonnero.stardewhandy.data

import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ru.yonnero.stardewhandy.domain.CommunityCenter
import ru.yonnero.stardewhandy.domain.CommunityCenterRepository
import stardewhandy.composeapp.generated.resources.Res

class FakeCommunityCenterRepository : CommunityCenterRepository {

    private var mockData = CommunityCenter(emptyList())

    @OptIn(ExperimentalResourceApi::class)
    override suspend fun loadData() {
        val bytes = Res.readBytes("files/community_center.json")
        val jsonString = bytes.decodeToString()
        val jsonParser = Json { ignoreUnknownKeys = true }
        mockData = jsonParser.decodeFromString(jsonString)
    }

    override fun getCommunityCenter(): CommunityCenter {
        return mockData
    }

    override fun toggleItemDonation(itemId: Int) {
        val updatedRooms = mockData.rooms.map { room ->
            val updatedBundles = room.bundles.map { bundle ->
                val updatedItems = bundle.items.map { item ->
                    if (item.id == itemId) item.copy(isDonated = !item.isDonated) else item
                }
                bundle.copy(items = updatedItems)
            }
            room.copy(bundles = updatedBundles)
        }
        mockData = mockData.copy(rooms = updatedRooms)
    }

}