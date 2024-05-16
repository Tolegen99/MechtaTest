package kz.tolegen.mechtatest.ui.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.Dispatchers
import kz.tolegen.mechtatest.R
import kz.tolegen.mechtatest.ui.utils.ErrorMessage
import kz.tolegen.mechtatest.ui.utils.LoadingNextPageItem
import kz.tolegen.mechtatest.ui.utils.PageLoader

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigationRequested: (code: String) -> Unit,
) {
    val smartphonePagingItems = viewModel.smartphonesState.collectAsLazyPagingItems()
    val favorites = viewModel.favorites.collectAsState().value

    LaunchedEffect(Dispatchers.IO) {
        viewModel.getFavorites()
    }

    Scaffold(
        topBar = {
            MechtaAppBar()
        },
    ) {
        Box(modifier = Modifier.padding(it)) {
            LazyColumn {
                items(smartphonePagingItems.itemCount) { index ->
                    smartphonePagingItems[index]?.let { smartphone ->
                        val isFavorite = favorites.contains(smartphone.id.toLong())
                        SmartphoneCardUi(
                            item = smartphone,
                            isFavorite = isFavorite,
                            onItemClicked = { code -> onNavigationRequested(code) },
                            onFavoriteIconClicked = {
                                viewModel.toggleFavorite(
                                    isFavorite,
                                    smartphone
                                )
                            },
                        )
                    }
                }
                smartphonePagingItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val error = smartphonePagingItems.loadState.refresh as LoadState.Error
                            item {
                                ErrorMessage(
                                    modifier = Modifier.fillParentMaxSize(),
                                    message = error.error.localizedMessage!!,
                                    onClickRetry = { retry() })
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item { LoadingNextPageItem(modifier = Modifier) }
                        }

                        loadState.append is LoadState.Error -> {
                            val error = smartphonePagingItems.loadState.append as LoadState.Error
                            item {
                                ErrorMessage(
                                    modifier = Modifier,
                                    message = error.error.localizedMessage!!,
                                    onClickRetry = { retry() })
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.padding(4.dp)) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MechtaAppBar() {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.app_name),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleLarge,
            )
        },
    )
}