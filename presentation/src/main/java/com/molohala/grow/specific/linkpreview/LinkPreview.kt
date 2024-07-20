package com.molohala.grow.specific.linkpreview

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import com.bestswlkh0310.mydesignsystem.extension.bounceClick
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.bestswlkh0310.mydesignsystem.foundation.util.MyPreviews
import com.molohala.grow.data.opengraph.OpenGraph
import com.molohala.grow.data.opengraph.OpenGraphRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LinkPreview(
    modifier: Modifier = Modifier,
    url: String,
    openGraph: OpenGraph?,
    onChange: (OpenGraph) -> Unit
) {

    val coroutine = rememberCoroutineScope()
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        coroutine.launch {
            withContext(Dispatchers.IO) {
                val fetchedOpenGraph = OpenGraphRepository.instance.fetchOpenGraph(url)
                onChange(fetchedOpenGraph)
            }
        }
    }

    Column(
        modifier = modifier
            .bounceClick {
                uriHandler.openUri(url)
            }
    ) {

        Text(
            text = openGraph.toString(),
            color = MyTheme.colorScheme.textNormal
        )
    }
}