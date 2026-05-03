package ru.yonnero.stardewhandy.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yonnero.stardewhandy.domain.Bundle
import ru.yonnero.stardewhandy.domain.Room


@Composable
fun CommunityCenterScreen(viewModel: CommunityCenterViewModel) {
    val state by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(state.rooms) { room ->
            RoomCard(
                room = room,
                onItemClick = { itemId -> viewModel.onToggleItem(itemId) }
            )
        }
    }
}

@Composable
fun RoomCard(room: Room, onItemClick: (Int) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${room.name} ${if (room.isCompleted) "✅" else ""}",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))

            room.bundles.forEach { bundle ->
                BundleView(bundle = bundle, onItemClick = onItemClick)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun BundleView(bundle: Bundle, onItemClick: (Int) -> Unit) {
    Column(modifier = Modifier.padding(start = 8.dp)) {
        Text(
            text = "${bundle.title} (${if (bundle.isCompleted) "Завершен" else "Нужно сдать: ${bundle.requiredItemsCount}"})",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        bundle.items.forEach { item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = item.isDonated,
                    onCheckedChange = { onItemClick(item.id) } // Клик улетает в ViewModel!
                )
                Text(text = item.name)
            }
        }
    }
}