package com.example.tvscraper.domain

data class MediaItem(
    val title: String,
    val posterUrl: String,
    val detailUrl: String,
    val year: String? = null,
    val rating: String? = null,
    val type: MediaType = MediaType.MOVIE
)

enum class MediaType {
    MOVIE, TV_SERIES
}

data class VideoSource(
    val name: String,
    val url: String,
    val quality: String? = null
)
