package ru.yonnero.stardewhandy

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import ru.yonnero.stardewhandy.presentation.CommunityCenterScreen
import ru.yonnero.stardewhandy.presentation.CommunityCenterViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import ru.yonnero.stardewhandy.theme.StardewTheme

enum class Screen {
    CommunityCenter,
    Villagers
}

@Composable
@Preview
fun App() {

    StardewTheme {
        val communityCenterViewModel = remember { CommunityCenterViewModel() }
        var currentScreen by remember { mutableStateOf(Screen.CommunityCenter) }

        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Клуб") },
                        label = { Text("Клуб") },
                        selected = currentScreen == Screen.CommunityCenter,
                        onClick = { currentScreen = Screen.CommunityCenter }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.DateRange, contentDescription = "Жители") },
                        label = { Text("Жители") },
                        selected = currentScreen == Screen.Villagers,
                        onClick = { currentScreen = Screen.Villagers }
                    )
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (currentScreen) {
                    Screen.CommunityCenter -> {
                        CommunityCenterScreen(viewModel = communityCenterViewModel)
                    }
                    Screen.Villagers -> {
                        Text("Здесь будет справочник жителей и подарков!")
                    }
                }
            }
        }
    }
}