package com.example.tvscraper.scraper

import com.example.tvscraper.domain.MediaItem
import com.example.tvscraper.domain.MediaType
import org.jsoup.Jsoup

class PelisPlusScraper {
    private val baseUrl = "https://pelisplushd.bz"
    private val userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"

    private fun getFallbackMovies(): List<MediaItem> {
        return listOf(
            MediaItem(
                title = "Big Buck Bunny",
                posterUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c5/Big_buck_bunny_poster_big.jpg/640px-Big_buck_bunny_poster_big.jpg",
                detailUrl = "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8",
                year = "2008",
                type = MediaType.MOVIE
            ),
            MediaItem(
                title = "Sintel",
                posterUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/Sintel_poster.jpg/640px-Sintel_poster.jpg",
                detailUrl = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8",
                year = "2010",
                type = MediaType.MOVIE
            ),
            MediaItem(
                title = "Tears of Steel",
                posterUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a2/Tears_of_Steel_poster.jpg/640px-Tears_of_Steel_poster.jpg",
                detailUrl = "https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8",
                year = "2012",
                type = MediaType.MOVIE
            ),
            MediaItem(
                title = "Elephant's Dream",
                posterUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e8/Elephants_Dream_s5_both.jpg/640px-Elephants_Dream_s5_both.jpg",
                detailUrl = "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8",
                year = "2006",
                type = MediaType.MOVIE
            )
        )
    }

    fun getLatestMovies(): List<MediaItem> {
        return try {
            val doc = Jsoup.connect(baseUrl)
                .userAgent(userAgent)
                .timeout(10000)
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

            // KEY FIX: If scraping returned nothing (Cloudflare page, changed HTML, etc.)
            // show fallback content so the app is never blank
            if (items.isEmpty()) {
                getFallbackMovies()
            } else {
                items
            }
        } catch (e: Exception) {
            e.printStackTrace()
            getFallbackMovies()
        }
    }

    fun search(query: String): List<MediaItem> {
        val searchUrl = "$baseUrl/search?s=${query.replace(" ", "+")}"
        return emptyList() 
    }
}
