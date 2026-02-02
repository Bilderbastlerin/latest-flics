@file:OptIn(ExperimentalMaterial3Api::class)

package de.franziskaneumeister.recentflics.feature.movie.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.franziskaneumeister.recentflics.core.designsystem.theme.AppTheme
import de.franziskaneumeister.recentflics.core.designsystem.theme.utils.formatDate
import de.franziskaneumeister.recentflics.feature.movie.R
import de.franziskaneumeister.recentflics.feature.movie.details.DetailsViewModel.DetailsUiState
import de.franziskaneumeister.recentflics.feature.movie.details.DetailsViewModel.DetailsUiState.Success
import java.time.LocalDate

@Composable
internal fun DetailsScreen(viewModel: DetailsViewModel, goBack: () -> Unit) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    DetailsScreen(state, goBack)
}

@Composable
private fun DetailsScreen(state: DetailsUiState, goBack: () -> Unit) {
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    if (state is Success) Text(state.title)
                },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                R.string.label_back
                            )
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        when (state) {
            is DetailsUiState.Error -> {
                ErrorOverlay(Modifier.padding(paddingValues))
            }

            is DetailsUiState.Loading -> {
                LoadingOverlay(Modifier.padding(paddingValues))
            }

            is Success -> {
                MovieDetails(
                    modifier = Modifier.padding(paddingValues),
                    releaseDate = state.releaseDate,
                    text = state.bodyText
                )

            }
        }
    }
}

@Composable
private fun LoadingOverlay(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
private fun ErrorOverlay(modifier: Modifier) {
    Box(modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.general_error_message),
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun MovieDetails(
    modifier: Modifier,
    releaseDate: LocalDate,
    text: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Text(releaseDate.formatDate())
        Text(text)
    }
}

@Preview
@Composable
private fun PreviewDetailsScreen() {
    AppTheme {
        DetailsScreen(
            state = Success(
                title = "Foo Film",
                releaseDate = LocalDate.now(),
                bodyText = "Lorem Ipsum demonstrandum"
            ),
            goBack = {}
        )
    }
}
