package com.example.tvscraper.ui

import androidx.compose.foundation.layout.*
import androidx.tv.foundation.lazy.grid.*
import androidx.tv.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val movies by viewModel.movies.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val currentScreen by viewModel.currentScreen.collectAsState()
    val selectedMovie by viewModel.selectedMovie.collectAsState()

    when (currentScreen) {
        MainViewModel.Screen.CATALOG -> {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                TvLazyVerticalGrid(
                    columns = TvGridCells.Adaptive(160.dp),
                    contentPadding = PaddingValues(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(movies) { movie ->
                        MovieCard(
                            movie = movie,
                            onClick = { viewModel.selectMovie(movie) }
                        )
                    }
                }
            }
        }
        MainViewModel.Screen.DETAIL -> {
            selectedMovie?.let {
                MovieDetailScreen(
                    movie = it,
                    onWatchClick = { viewModel.startPlayback() },
                    onBackClick = { viewModel.goBack() }
                )
            }
        }
        MainViewModel.Screen.PLAYER -> {
            VideoPlayerScreen(url = selectedMovie?.detailUrl ?: "", onBack = { viewModel.goBack() })
        }
    }
}

