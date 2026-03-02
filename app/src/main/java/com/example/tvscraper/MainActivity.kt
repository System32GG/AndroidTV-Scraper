package com.example.tvscraper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import com.example.tvscraper.ui.MainScreen

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TVScraperTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TVScraperTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        content()
    }
}
