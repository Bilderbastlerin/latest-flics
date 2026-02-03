@file:OptIn(ExperimentalMaterial3Api::class)

package de.franziskaneumeister.recentflics.feature.home.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import de.franziskaneumeister.recentflics.core.designsystem.theme.AppTheme
import de.franziskaneumeister.recentflics.core.designsystem.theme.utils.formatDate
import de.franziskaneumeister.recentflics.core.types.entities.MovieId
import de.franziskaneumeister.recentflics.feature.home.R
import de.franziskaneumeister.recentflics.feature.home.list.ListViewModel.ListEntry
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

@Composable
internal fun ListScreen(viewModel: ListViewModel, goToMovie: (MovieId) -> Unit) {
    val lazyPagingItems: LazyPagingItems<ListEntry> = viewModel.uiState.collectAsLazyPagingItems()
    ListScreen(lazyPagingItems, goToMovie)
}

@Composable
private fun ListScreen(
    lazyPagingItems: LazyPagingItems<ListEntry>,
    goToMovie: (MovieId) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.title_movie_list))
                },
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues,
            state = rememberLazyListState()
        ) {
            items(
                lazyPagingItems.itemCount,
                // could lead to crash because sometimes the api puts a movie on 2 pages…
                key = lazyPagingItems.itemKey { it.id }
            ) { index ->
                val movie = lazyPagingItems[index]
                if (movie != null) {
                    MovieRow(
                        modifier = Modifier.fillParentMaxWidth(),
                        movie = movie,
                        goToMovie = goToMovie
                    )
                }
            }

            when (lazyPagingItems.loadState.refresh) {
                LoadState.Loading -> {
                    item(key = "Loading Overlay") {
                        LoadingOverlay(Modifier.fillParentMaxSize())
                    }
                }

                is LoadState.Error -> {
                    item(key = "Error Message") {
                        ErrorMessage(
                            Modifier.fillParentMaxSize(),
                            retry = { lazyPagingItems.retry() })
                    }
                }

                else -> {}
            }

            when (lazyPagingItems.loadState.append) {
                is LoadState.Error -> {
                    item(key = "Inline Error Message") {
                        InlineErrorMessage(
                            modifier = Modifier.fillParentMaxWidth(),
                            retry = { lazyPagingItems.retry() }
                        )
                    }
                }

                LoadState.Loading -> {
                    item(key = "Inline loading indicator") {
                        InlineLoadingIndicator(Modifier.fillParentMaxWidth())
                    }
                }

                else -> {}
            }

            item(key = "bottom inset") {
                Spacer(Modifier.padding(paddingValues.calculateBottomPadding()))
            }
        }
    }
}

@Composable
private fun InlineLoadingIndicator(modifier: Modifier) {
    Box(
        modifier.padding(16.dp)
    ) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
private fun LoadingOverlay(modifier: Modifier) {
    Box(modifier = modifier) {
        Column(
            Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.text_loading_message),
            )
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun InlineErrorMessage(
    modifier: Modifier,
    retry: () -> Unit
) {
    Box(modifier) {
        Column(
            Modifier.padding(16.dp).align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.text_inline_error_message))
            Button(onClick = retry) {
                Text(stringResource(R.string.label_retry))
            }
        }
    }
}

@Composable
private fun ErrorMessage(modifier: Modifier, retry: () -> Unit) {
    Box(modifier) {
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.error_message_inital_load),
            )
            Button(onClick = retry) {
                Text(stringResource(R.string.label_retry))
            }
        }
    }
}

@Composable
private fun MovieRow(
    modifier: Modifier = Modifier,
    movie: ListEntry,
    goToMovie: (MovieId) -> Unit,
) {
    Card(
        modifier = modifier.padding(8.dp),
        onClick = { goToMovie(movie.id) }
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(movie.title)
            Text(movie.releaseDate.formatDate())
        }
    }
}

@Preview
@Composable
private fun PreviewListScreen() {
    AppTheme {
        ListScreen(
            lazyPagingItems = flowOf(
                PagingData.from(
                    listOf(
                        ListEntry(id = 1, title = "Hello World", releaseDate = LocalDate.now())
                    )
                )
            ).collectAsLazyPagingItems(),
            goToMovie = {}
        )
    }
}
