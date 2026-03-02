package com.example.tvscraper.scraper

import com.example.tvscraper.domain.MediaItem
import com.example.tvscraper.domain.MediaType
import org.jsoup.Jsoup
import java.io.IOException

class PelisPlusScraper {
    private val baseUrl = "https://pelisplushd.bz"
    private val userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"

    fun getLatestMovies(): List<MediaItem> {
        return try {
            val doc = Jsoup.connect(baseUrl)
                .userAgent(userAgent)
                .get()
            
            val items = mutableListOf<MediaItem>()
            val elements = doc.select(".items-movies .item")
            
            for (element in elements) {
                val title = element.select(".title").text()
                val posterUrl = element.select("img").attr("src")
                val detailUrl = element.select("a").attr("href")
                
                if (title.isNotEmpty()) {
                    items.add(
                        MediaItem(
                            title = title,
                            posterUrl = posterUrl,
                            detailUrl = if (detailUrl.startsWith("http")) detailUrl else baseUrl + detailUrl,
                            year = element.select(".year").text(),
                            type = MediaType.MOVIE
                        )
                    )
                }
            }
            items
        } catch (e: Exception) {
            e.printStackTrace()
            // Fallback content to verify UI and Player if scraping is blocked
            listOf(
                MediaItem(
                    title = "[Mock] Big Buck Bunny",
                    posterUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c5/Big_buck_bunny_poster_big.jpg/640px-Big_buck_bunny_poster_big.jpg",
                    detailUrl = "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8",
                    year = "2008",
                    type = MediaType.MOVIE
                ),
                MediaItem(
                    title = "[Mock] Sintel",
                    posterUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/Sintel_poster.jpg/640px-Sintel_poster.jpg",
                    detailUrl = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8",
                    year = "2010",
                    type = MediaType.MOVIE
                ),
                MediaItem(
                    title = "[Mock] Tears of Steel",
                    posterUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a2/Tears_of_Steel_poster.jpg/640px-Tears_of_Steel_poster.jpg",
                    detailUrl = "https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8",
                    year = "2012",
                    type = MediaType.MOVIE
                )
            )
        }
    }

    fun search(query: String): List<MediaItem> {
        val searchUrl = "$baseUrl/search?s=${query.replace(" ", "+")}"
        // Similar logic to getLatestMovies but on search results page
        return emptyList() 
    }
}
