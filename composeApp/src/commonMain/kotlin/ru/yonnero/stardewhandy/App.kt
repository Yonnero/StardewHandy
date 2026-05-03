import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import ru.yonnero.stardewhandy.presentation.CommunityCenterScreen
import ru.yonnero.stardewhandy.presentation.CommunityCenterViewModel

// ... остальной код в начале файла ...

@Composable
@Preview
fun App() {
    MaterialTheme {
        // Создаем нашего менеджера
        val viewModel = remember { CommunityCenterViewModel() }

        // Запускаем наш экран и передаем ему менеджера
        CommunityCenterScreen(viewModel = viewModel)
    }
}