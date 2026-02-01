package de.franziskaneumeister.recentflics.feature.home.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.franziskaneumeister.recentflics.core.designsystem.theme.AppTheme

@Composable
internal fun ListScreen(viewModel: ListViewModel) {
    val state: ListViewModel.ListUiState by viewModel.uiState.collectAsStateWithLifecycle()
    ListScreen(state)
}

@Composable
private fun ListScreen(state: ListViewModel.ListUiState) {
    Scaffold { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues
        ) {
            when (state) {
                is ListViewModel.ListUiState.Error -> {}
                ListViewModel.ListUiState.Loading -> {}
                is ListViewModel.ListUiState.Success -> {
                    items(state.data, key = { it.id }) {
                        Text(it.name)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewListScreen() {
    AppTheme {
        ListScreen(
            state = ListViewModel.ListUiState.Success(
                data = listOf(
                    ListViewModel.ListEntry(
                        name = "Hello World", id = "0"
                    )
                )
            )
        )
    }
}
