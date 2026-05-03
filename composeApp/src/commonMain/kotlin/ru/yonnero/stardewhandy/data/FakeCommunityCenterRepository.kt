package ru.yonnero.stardewhandy.data

import ru.yonnero.stardewhandy.domain.Bundle
import ru.yonnero.stardewhandy.domain.BundleItem
import ru.yonnero.stardewhandy.domain.CommunityCenter
import ru.yonnero.stardewhandy.domain.CommunityCenterRepository
import ru.yonnero.stardewhandy.domain.Room

class FakeCommunityCenterRepository : CommunityCenterRepository {

    private var mockData = CommunityCenter(
        rooms = listOf(
            Room(
                id = 1,
                name = "Кладовая",
                bundles = listOf(
                    Bundle(
                        id = 1,
                        title = "Весенние культуры",
                        requiredItemsCount = 4,
                        items = listOf(
                            BundleItem(id = 1, name = "Пастернак", isDonated = false),
                            BundleItem(id = 2, name = "Зеленая фасоль", isDonated = false),
                            BundleItem(id = 3, name = "Цветная капуста", isDonated = false),
                            BundleItem(id = 4, name = "Картофель", isDonated = false)
                        )
                    )
                )
            )
        )
    )

    override fun getCommunityCenter(): CommunityCenter {
        return mockData
    }

    override fun toggleItemDonation(itemId: Int) {
        val updatedRooms = mockData.rooms.map { room ->
            val updatedBundles = room.bundles.map { bundle ->
                val updatedItems = bundle.items.map { item ->
                    if (item.id == itemId) {
                        item.copy(isDonated = !item.isDonated)
                    } else {
                        item
                    }
                }
                bundle.copy(items = updatedItems)
            }
            room.copy(bundles = updatedBundles)
        }
        mockData = mockData.copy(rooms = updatedRooms)
    }
}