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
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.Font
import stardewhandy.composeapp.generated.resources.Res
import stardewhandy.composeapp.generated.resources.pixel_font
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.BorderStroke

@Composable
fun CommunityCenterScreen(viewModel: CommunityCenterViewModel) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

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
    val stardewFont = FontFamily(Font(Res.font.pixel_font))


    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(size = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(width = 4.dp, color = MaterialTheme.colorScheme.outline)

    ) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Text(
                text = "${room.name} ${if (room.isCompleted) "✅" else ""}",
                fontFamily = stardewFont,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(height = 8.dp))

            room.bundles.forEach { bundle ->
                BundleView(bundle = bundle, onItemClick = onItemClick)
            }
        }
    }
}

@Composable
fun BundleView(bundle: Bundle, onItemClick: (Int) -> Unit) {
    Column(modifier = Modifier.padding(start = 8.dp)) {
        val stardewFont = FontFamily(Font(Res.font.pixel_font))
        Text(
            text = "${bundle.title} (${if (bundle.isCompleted) "Завершен" else "Осталось: ${bundle.remainingItemsCount}"})",
            style = MaterialTheme.typography.titleMedium,
            fontFamily = stardewFont,
            color = MaterialTheme.colorScheme.primary
        )

        bundle.items.forEach { item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = item.isDonated,
                    onCheckedChange = { onItemClick(item.id) }
                )
                Text(text = item.name)
            }
        }
    }
}