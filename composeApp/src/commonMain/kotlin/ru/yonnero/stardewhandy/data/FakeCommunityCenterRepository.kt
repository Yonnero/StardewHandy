package ru.yonnero.stardewhandy.data

import com.russhwolf.settings.Settings
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import stardewhandy.composeapp.generated.resources.Res
import ru.yonnero.stardewhandy.domain.CommunityCenter
import ru.yonnero.stardewhandy.domain.CommunityCenterRepository

class FakeCommunityCenterRepository : CommunityCenterRepository {

    private var mockData = CommunityCenter(emptyList())

    private val settings = Settings()
    private val DONATED_ITEMS_KEY = "donated_items_key"

    private fun getSavedIds(): Set<Int> {
        val savedString = settings.getString(DONATED_ITEMS_KEY, "")
        if (savedString.isEmpty()) return emptySet() // Если пусто - возвращаем пустой список

        return savedString.split(",")
            .mapNotNull { it.toIntOrNull() }
            .toSet()
    }

    private fun saveIds(ids: Set<Int>) {
        settings.putString(DONATED_ITEMS_KEY, ids.joinToString(","))
    }

    @OptIn(ExperimentalResourceApi::class)
    override suspend fun loadData() {
        val bytes = Res.readBytes("files/community_center.json")
        val jsonString = bytes.decodeToString()
        val jsonParser = Json { ignoreUnknownKeys = true }

        val baseData: CommunityCenter = jsonParser.decodeFromString(jsonString)

        val savedIds = getSavedIds()

        val restoredRooms = baseData.rooms.map { room ->
            val restoredBundles = room.bundles.map { bundle ->
                val restoredItems = bundle.items.map { item ->
                    if (savedIds.contains(item.id)) item.copy(isDonated = true) else item
                }
                bundle.copy(items = restoredItems)
            }
            room.copy(bundles = restoredBundles)
        }

        mockData = CommunityCenter(restoredRooms)
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

        val newDonatedIds = mutableSetOf<Int>()
        mockData.rooms.forEach { room ->
            room.bundles.forEach { bundle ->
                bundle.items.forEach { item ->
                    if (item.isDonated) newDonatedIds.add(item.id)
                }
            }
        }

        saveIds(newDonatedIds)
    }
}