package ru.yonnero.stardewhandy.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.yonnero.stardewhandy.data.FakeCommunityCenterRepository
import ru.yonnero.stardewhandy.domain.CommunityCenter

class CommunityCenterViewModel {

    private val repository = FakeCommunityCenterRepository()

    private val _uiState = MutableStateFlow(CommunityCenter(emptyList()))
    val uiState: StateFlow<CommunityCenter> = _uiState.asStateFlow()

    suspend fun loadData() {
        repository.loadData()

        _uiState.value = repository.getCommunityCenter()
    }

    fun onToggleItem(itemId: Int) {
        repository.toggleItemDonation(itemId)
        _uiState.value = repository.getCommunityCenter()
    }
}