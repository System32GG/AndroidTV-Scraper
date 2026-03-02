package com.example.tvscraper.ui

import androidx.compose.foundation.layout.*
import androidx.tv.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tvscraper.domain.MediaItem

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MovieCard(
    movie: MediaItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    StandardCardContainer(
        imageCard = {
            CardLayoutDefaults.ImageCard(
                onClick = onClick,
                shape = CardDefaults.shape(MaterialTheme.shapes.medium),
                interactionSource = it
            ) {
                AsyncImage(
                    model = movie.posterUrl,
                    contentDescription = movie.title,
                    modifier = Modifier.aspectRatio(2f / 3f),
                    contentScale = ContentScale.Crop
                )
            }
        },
        title = {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                modifier = Modifier.padding(top = 4.dp)
            )
        },
        modifier = modifier.width(150.dp)
    )
}
