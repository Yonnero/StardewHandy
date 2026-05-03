package ru.yonnero.stardewhandy.domain

interface CommunityCenterRepository {

    fun getCommunityCenter(): CommunityCenter

    fun toggleItemDonation(itemId: Int)
}