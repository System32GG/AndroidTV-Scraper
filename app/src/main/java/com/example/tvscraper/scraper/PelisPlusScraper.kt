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
        } catch (e: IOException) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun search(query: String): List<MediaItem> {
        val searchUrl = "$baseUrl/search?s=${query.replace(" ", "+")}"
        // Similar logic to getLatestMovies but on search results page
        return emptyList() 
    }
}
