package ru.yonnero.stardewhandy.domain

import kotlinx.serialization.Serializable

@Serializable
data class BundleItem(
    val id: Int,
    val name: String,
    val isDonated: Boolean = false
)


@Serializable
data class Bundle(
    val id: Int,
    val title: String,
    val requiredItemsCount: Int,
    val items: List<BundleItem>
) {
    val isCompleted: Boolean
        get() = items.count { it.isDonated } >= requiredItemsCount
}


@Serializable
data class Room(
    val id: Int,
    val name: String,
    val bundles: List<Bundle>
) {
    val isCompleted: Boolean
        get() = bundles.all { it.isCompleted }
}


@Serializable
data class CommunityCenter(
    val rooms: List<Room>
)
