package de.franziskaneumeister.recentflics.feature.home.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
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
import de.franziskaneumeister.recentflics.data.movies.entities.Movie
import de.franziskaneumeister.recentflics.feature.home.R
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
internal fun ListScreen(viewModel: ListViewModel) {
    val lazyPagingItems: LazyPagingItems<Movie> = viewModel.uiState.collectAsLazyPagingItems()
    ListScreen(lazyPagingItems)
}

@Composable
private fun ListScreen(lazyPagingItems: LazyPagingItems<Movie>) {
    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues
        ) {
            stickyHeader {
                Text(stringResource(R.string.title_movie_list))
            }
            if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
                item {
                    Box(modifier = Modifier.fillParentMaxSize()) {
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
            }
            items(
                lazyPagingItems.itemCount,
                key = lazyPagingItems.itemKey { it.id }
            ) { index ->
                val movie = lazyPagingItems[index]
                if (movie != null) {
                    MovieRow(
                        modifier = Modifier.fillParentMaxWidth(),
                        movie = movie
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieRow(
    modifier: Modifier = Modifier,
    movie: Movie
) {
    Card(modifier = modifier.padding(8.dp)) {
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
            ).collectAsLazyPagingItems()
        )
    }
}
