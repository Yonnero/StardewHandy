package ru.yonnero.stardewhandy.domain

data class BundleItem(
    val id: Int,
    val name: String,
    val isDonated: Boolean = false
)

data class Bundle(
    val id: Int,
    val title: String,
    val requiredItemsCount: Int,
    val items: List<BundleItem>
) {
    val isCompleted: Boolean
        get() = items.count { it.isDonated } >= requiredItemsCount
}

data class Room(
    val id: Int,
    val name: String,
    val bundles: List<Bundle>
) {
    val isCompleted: Boolean
        get() = bundles.all { it.isCompleted }
}

data class CommunityCenter(
    val rooms: List<Room>
)
