package kz.tolegen.mechtatest.ui.feature.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kz.tolegen.mechtatest.model.entity.SmartphoneDetailData
import kz.tolegen.mechtatest.ui.utils.PageLoader
import kotlin.math.absoluteValue

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    pressOnBack: () -> Unit,
) {
    val detail: SmartphoneDetailData? by viewModel.smartphoneDetailFlow.collectAsState(initial = null)
    val isFavorite = viewModel.isFavorite.collectAsState().value
    detail?.let { smartphone ->
        DetailScreenBody(
            isFavorite = isFavorite,
            smartphone = smartphone,
            pressOnBack = pressOnBack,
            onFavoriteIconClicked = { viewModel.toggleFavorite(smartphone) }
        )
    } ?: PageLoader(modifier = Modifier.fillMaxSize())
}


@OptIn(ExperimentalPagerApi::class)
@Composable
private fun DetailScreenBody(
    isFavorite: Boolean,
    smartphone: SmartphoneDetailData,
    pressOnBack: () -> Unit = {},
    onFavoriteIconClicked: () -> Unit,
) {

    val pagerState = rememberPagerState()
    val imageSlider = smartphone.photos.map {
        rememberImagePainter(
            data = it,
        )
    }
    Scaffold(
        topBar = {
            DetailAppBar(
                isFavorite = isFavorite,
                onBackClicked = { pressOnBack() },
                onFavoriteIconClicked = { onFavoriteIconClicked() },
            )
        },
    ) {
        Column(Modifier.padding(it)) {
            HorizontalPager(
                count = imageSlider.size,
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier
                    .height(240.dp)
                    .fillMaxWidth()
            ) { page ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .graphicsLayer {
                            val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                            lerp(
                                start = 0.85f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            ).also { scale ->
                                scaleX = scale
                                scaleY = scale
                            }

                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }
                ) {
                    Image(
                        painter = imageSlider[page],
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = smartphone.title,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            LazyColumn(
                modifier = Modifier.padding(
                    top = 24.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            ) {
                itemsIndexed(smartphone.mainProperties) { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 2.dp)
                            .then(
                                if ((index % 2) == 0)
                                    Modifier.drawBehind {
                                        drawRoundRect(
                                            color = Color.LightGray,
                                            cornerRadius = CornerRadius(10.dp.toPx())
                                        )
                                    }
                                else Modifier
                            )
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.propName,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = item.propValue,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailAppBar(
    isFavorite: Boolean,
    onBackClicked: () -> Unit,
    onFavoriteIconClicked: () -> Unit,
) {
    TopAppBar(
        navigationIcon = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { onBackClicked() }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Action icon"
                    )
                }
                IconButton(onClick = { onFavoriteIconClicked() }) {
                    Icon(
                        imageVector = if (isFavorite)
                            Icons.Filled.Favorite
                        else
                            Icons.Filled.FavoriteBorder,
                        contentDescription = "Action icon",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            }
        },
        title = {},
    )
}