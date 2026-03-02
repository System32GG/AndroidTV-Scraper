package com.example.tvscraper.ui

import androidx.compose.foundation.layout.*
import androidx.tv.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tvscraper.domain.MediaItem

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movie: MediaItem,
    onWatchClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image (Blurred or dimmed)
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.3f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(48.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Poster
                Card(
                    onClick = {}
                ) {
                    AsyncImage(
                        model = movie.posterUrl,
                        contentDescription = movie.title,
                        modifier = Modifier.width(200.dp).aspectRatio(2f/3f),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(32.dp))

                // Info
                Column {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.displayMedium
                    )
                    movie.year?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Button(onClick = onWatchClick) {
                        Text("Ver Ahora")
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    OutlinedButton(onClick = onBackClick) {
                        Text("Regresar")
                    }
                }
            }
        }
    }
}
