package com.example.tvscraper.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvscraper.domain.MediaItem
import com.example.tvscraper.scraper.PelisPlusScraper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val scraper = PelisPlusScraper()
    
    private val _movies = MutableStateFlow<List<MediaItem>>(emptyList())
    val movies: StateFlow<List<MediaItem>> = _movies

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _selectedMovie = MutableStateFlow<MediaItem?>(null)
    val selectedMovie: StateFlow<MediaItem?> = _selectedMovie

    private val _currentScreen = MutableStateFlow(Screen.CATALOG)
    val currentScreen: StateFlow<Screen> = _currentScreen

    enum class Screen { CATALOG, DETAIL, PLAYER }

    fun selectMovie(movie: MediaItem) {
        _selectedMovie.value = movie
        _currentScreen.value = Screen.DETAIL
    }

    fun goBack() {
        when (_currentScreen.value) {
            Screen.DETAIL -> _currentScreen.value = Screen.CATALOG
            Screen.PLAYER -> _currentScreen.value = Screen.DETAIL
            else -> {}
        }
    }

    fun startPlayback() {
        _currentScreen.value = Screen.PLAYER
    }


    init {
        loadContent()
    }

    fun loadContent() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val results = scraper.getLatestMovies()
            _movies.value = results
            _isLoading.value = false
        }
    }
}
