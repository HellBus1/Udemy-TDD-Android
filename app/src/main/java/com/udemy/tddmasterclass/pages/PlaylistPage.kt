package com.udemy.tddmasterclass.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.udemy.tddmasterclass.R
import com.udemy.tddmasterclass.data.PlaylistAPI
import com.udemy.tddmasterclass.data.PlaylistRepository
import com.udemy.tddmasterclass.data.PlaylistService
import com.udemy.tddmasterclass.model.PlaylistItem
import com.udemy.tddmasterclass.viewmodel.PlaylistViewModel
import com.udemy.tddmasterclass.viewmodel.PlaylistViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun PlayListPage(
    navHostController: NavHostController,
    viewModel: PlaylistViewModel
) {
    val playlistState = viewModel.playlists.observeAsState()
    val loaderState = viewModel.loader.observeAsState()

    Column {
        TopAppBar(
            title = {
                Text(text = "Playlists")
            },
        )

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.semantics {
                testTag = "playlist-rv"
            }) {
                items(items = playlistState.value?.getOrNull() ?: listOf()) {
                    PlayListListItem(data = it) {
                        navHostController.navigate(route = "play_list_detail_page/${it.id}")
                    }
                }
            }

            if (loaderState.value != null && loaderState.value == true) {
                Box(Modifier.align(Alignment.Center).semantics {
                    testTag = "loader"
                }) {
                    CircularProgressindicator()
                }
            }
        }
    }
}

@Composable
fun PlayListListItem(data: PlaylistItem, callback: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp, top = 10.dp)
            .clickable {
                callback()
            }) {
        Image(
            painter = painterResource(id = data.image),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(48.dp)
                .padding(end = 10.dp)
        )
        Column {
            Text(data.name)
            Text(data.category)
        }
    }
}

@Composable
fun CircularProgressindicator() {
    CircularProgressIndicator(
        modifier = Modifier
            .drawBehind {
                drawCircle(
                    Color.Red,
                    radius = size.width / 2 - 5.dp.toPx() / 2,
                    style = Stroke(5.dp.toPx())
                )
            },
        color = Color.LightGray,
        strokeWidth = 5.dp
    )
}