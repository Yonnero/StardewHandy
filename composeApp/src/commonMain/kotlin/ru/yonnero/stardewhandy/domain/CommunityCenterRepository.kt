package ru.yonnero.stardewhandy.domain

interface CommunityCenterRepository {

    suspend fun loadData()

    fun getCommunityCenter(): CommunityCenter
    fun toggleItemDonation(itemId: Int)
}