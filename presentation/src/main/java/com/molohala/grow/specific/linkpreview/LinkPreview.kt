package com.molohala.grow.specific.linkpreview

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.bestswlkh0310.mydesignsystem.extension.bounceClick
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.bestswlkh0310.mydesignsystem.foundation.util.MyPreviews
import com.molohala.grow.data.opengraph.OpenGraph
import com.molohala.grow.data.opengraph.OpenGraphRepository
import kotlinx.coroutines.launch

@Composable
fun LinkPreview(
    modifier: Modifier = Modifier,
    url: String,
    openGraph: OpenGraph,
    onChange: (OpenGraph) -> Unit
) {

    val coroutine = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutine.launch {
            val fetchedOpenGraph = OpenGraphRepository.instance.fetchOpenGraph(url)
            onChange(fetchedOpenGraph)
        }
    }

    Column(
        modifier = modifier
            .bounceClick {
                // TODO: Open url
            }
    ) {
        Text(text = openGraph.toString())
    }
}

@Composable
@MyPreviews
private fun Preview() {
    MyTheme {
        LinkPreview(
            url = "https://www.youtube.com/watch?v=ohcHYg6J5Aw",
            openGraph = OpenGraph()
        ) {

        }
    }
}