package com.udemy.tddmasterclass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.udemy.tddmasterclass.pages.PlayListDetailpage
import com.udemy.tddmasterclass.pages.PlayListPage
import com.udemy.tddmasterclass.ui.theme.TDDMasterclassTheme
import com.udemy.tddmasterclass.viewmodel.PlaylistDetailViewModelFactory
import com.udemy.tddmasterclass.viewmodel.PlaylistDetailsViewModel
import com.udemy.tddmasterclass.viewmodel.PlaylistViewModel
import com.udemy.tddmasterclass.viewmodel.PlaylistViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: PlaylistViewModelFactory

    @Inject
    lateinit var playlistDetailViewModelFactory: PlaylistDetailViewModelFactory

    private lateinit var playlistViewModel: PlaylistViewModel
    private lateinit var playlistDetailsViewModel: PlaylistDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playlistViewModel = ViewModelProvider(this, viewModelFactory)[PlaylistViewModel::class.java]
        playlistDetailsViewModel = ViewModelProvider(this, playlistDetailViewModelFactory)[PlaylistDetailsViewModel::class.java]

        setContent {
            TDDMasterclassTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavigationGraph(navHostController = rememberNavController())
                }
            }
        }
    }

    @Composable
    fun NavigationGraph(navHostController: NavHostController) {
        NavHost(
            navController = navHostController,
            startDestination = Routes.PlayListPage.route
        ) {
            composable(route = Routes.PlayListPage.route) {
                PlayListPage(navHostController = navHostController, viewModel = playlistViewModel)
            }
            composable(
                route = Routes.PlayListDetailPage.route,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) {
                playlistDetailsViewModel.getPlaylistDetails(it.arguments?.getString("id") ?: "")
                PlayListDetailpage(navHostController = navHostController, viewModel = playlistDetailsViewModel)
            }
        }
    }
}