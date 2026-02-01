package de.franziskaneumeister.recentflics.feature.home.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import de.franziskaneumeister.recentflics.core.designsystem.theme.AppTheme
import de.franziskaneumeister.recentflics.core.types.entities.MovieId
import de.franziskaneumeister.recentflics.data.movies.entities.Movie
import de.franziskaneumeister.recentflics.feature.home.R
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
internal fun ListScreen(viewModel: ListViewModel, goToMovie: (MovieId) -> Unit) {
    val lazyPagingItems: LazyPagingItems<Movie> = viewModel.uiState.collectAsLazyPagingItems()
    ListScreen(lazyPagingItems, goToMovie)
}

@Composable
private fun ListScreen(
    lazyPagingItems: LazyPagingItems<Movie>,
    goToMovie: (MovieId) -> Unit
) {
    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues,
        ) {
            stickyHeader {
                Column {
                    Spacer(Modifier.padding(top = paddingValues.calculateTopPadding()))
                    Text(stringResource(R.string.title_movie_list))
                }
            }

            items(
                lazyPagingItems.itemCount,
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
                    item {
                        LoadingOverlay(Modifier.fillParentMaxSize())
                    }
                }

                is LoadState.Error -> {
                    item {
                        ErrorMessage(Modifier.fillParentMaxSize())
                    }
                }

                else -> {}
            }

            when (lazyPagingItems.loadState.append) {
                is LoadState.Error -> {
                    item {
                        InlineErrorMessage(Modifier.fillParentMaxWidth(), lazyPagingItems)
                    }
                }

                LoadState.Loading -> {
                    item {
                        InlineLoadingIndicator(Modifier.fillParentMaxWidth())
                    }
                }

                else -> {}
            }

            item {
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
        CircularProgressIndicator()
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
    lazyPagingItems: LazyPagingItems<Movie>
) {
    Box(modifier) {
        Column(
            Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.text_inline_error_message))
            Button(onClick = { lazyPagingItems.retry() }) {
                Text(stringResource(R.string.label_retry))
            }
        }
    }
}

@Composable
private fun ErrorMessage(modifier: Modifier) {
    Box(modifier) {
        Text(
            text = stringResource(R.string.error_message_inital_load),
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun MovieRow(
    modifier: Modifier = Modifier,
    movie: Movie,
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

@Composable
@ReadOnlyComposable
private fun LocalDate.formatDate(): String {
    return format(
        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
            .withLocale(
                java.util.Locale(
                    Locale.current.language,
                    Locale.current.region
                )
            )
    )
}

@Preview
@Composable
private fun PreviewListScreen() {
    AppTheme {
        ListScreen(
            lazyPagingItems = flowOf(
                PagingData.from(
                    listOf(
                        Movie(1, "Hello World", LocalDate.now(), "")
                    )
                )
            ).collectAsLazyPagingItems(),
            goToMovie = {}
        )
    }
}
