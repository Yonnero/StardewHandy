package ru.yonnero.stardewhandy.data

import kotlinx.serialization.json.Json
import ru.yonnero.stardewhandy.domain.CommunityCenter
import ru.yonnero.stardewhandy.domain.CommunityCenterRepository

class FakeCommunityCenterRepository : CommunityCenterRepository {

    private val rawJsonText = """
        {
          "rooms": [
            {
              "id": 1,
              "name": "Кладовая",
              "bundles": [
                {
                  "id": 1,
                  "title": "Весенние культуры",
                  "requiredItemsCount": 4,
                  "items": [
                    {"id": 1, "name": "Пастернак", "isDonated": false},
                    {"id": 2, "name": "Зеленая фасоль", "isDonated": false},
                    {"id": 3, "name": "Цветная капуста", "isDonated": false},
                    {"id": 4, "name": "Картофель", "isDonated": false}
                  ]
                }
              ]
            },
            {
              "id": 2,
              "name": "Аквариум",
              "bundles": [
                {
                  "id": 2,
                  "title": "Речная рыба",
                  "requiredItemsCount": 4,
                  "items": [
                    {"id": 5, "name": "Сом", "isDonated": false},
                    {"id": 6, "name": "Солнечник", "isDonated": false},
                    {"id": 7, "name": "Тигровая форель", "isDonated": false},
                    {"id": 8, "name": "Лещ", "isDonated": false}
                  ]
                }
              ]
            }
          ]
        }
    """.trimIndent()

    private val jsonParser = Json { ignoreUnknownKeys = true }

    private var mockData: CommunityCenter = jsonParser.decodeFromString(rawJsonText)

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