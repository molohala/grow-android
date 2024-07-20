package com.molohala.grow.data.opengraph

import android.util.Log
import com.molohala.grow.common.constant.TAG
import org.jsoup.Jsoup

class OpenGraphRepository private constructor() {

    companion object {
        val instance = OpenGraphRepository()
    }

    fun fetchOpenGraph(url: String): OpenGraph {
        val response = Jsoup.connect(url)
            .userAgent("Mozilla")
            .referrer("http://www.google.com")
            .execute()
        val document = response.parse()
        val elements = document.select("meta[property^=og:]")
        val openGraph = OpenGraph()
        elements.forEach {
            when (it.attr("property")) {
                "og:title" -> {
                    openGraph.title = it.attr("content")
                }
                "og:image" -> {
                    openGraph.image = it.attr("content")
                }
                "og:imageUrl" -> {
                    openGraph.imageUrl = it.attr("content")
                }
                "og:description" -> {
                    openGraph.description = it.attr("content")
                }
            }
        }
        Log.d(TAG, "fetchOpenGraph: $openGraph")
        return openGraph
    }
}