package com.udemy.tddmasterclass.pages

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.udemy.tddmasterclass.viewmodel.PlaylistDetailsViewModel

@Composable
fun PlayListDetailpage(
    navHostController: NavHostController,
    viewModel: PlaylistDetailsViewModel,
) {
    val playlistDetailState = viewModel.playlistDetails.observeAsState()
    val loaderState = viewModel.loader.observeAsState()

    Box {
        if (loaderState.value != null && loaderState.value == true) {
            Box(Modifier.align(Alignment.Center).semantics {
                testTag = "loader"
            }) {
                CircularProgressindicator()
            }
        } else {
            Column {
                TopAppBar(
                    title = {
                        Text(text = "Playlists Detail")
                    },
                    navigationIcon = if (navHostController.previousBackStackEntry != null) {
                        {
                            IconButton(onClick = { navHostController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    } else {
                        null
                    }
                )

                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, top = 10.dp)) {
                    val detailValue = playlistDetailState.value?.getOrNull()

                    Text(text = detailValue?.name ?: "")
                    Text(text = detailValue?.details ?: "")
                }
            }
        }
    }
}