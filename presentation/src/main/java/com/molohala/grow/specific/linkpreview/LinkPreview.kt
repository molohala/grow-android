package com.molohala.grow.specific.linkpreview

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bestswlkh0310.mydesignsystem.extension.bounceClick
import com.bestswlkh0310.mydesignsystem.foundation.MyTheme
import com.bestswlkh0310.mydesignsystem.foundation.util.MyPreviews
import com.molohala.grow.common.constant.TAG
import com.molohala.grow.data.opengraph.OpenGraph
import com.molohala.grow.data.opengraph.OpenGraphRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

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
        if (openGraph != null) {
            return@LaunchedEffect
        }
        coroutine.launch {
            withContext(Dispatchers.IO) {
                val fetchedOpenGraph = OpenGraphRepository.instance.fetchOpenGraph(url)
                onChange(fetchedOpenGraph)
            }
        }
    }

    val shape = RoundedCornerShape(8.dp)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .bounceClick {
                uriHandler.openUri(url)
            }
            .border(1.dp, MyTheme.colorScheme.backgroundAlt, shape)
            .clip(shape)
    ) {
        (openGraph?.image ?: openGraph?.imageUrl)?.let {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.FillBounds,
                model = it,
                contentDescription = null,
                onError = {
                    Log.d(TAG, "LinkPreview: ${it.result.throwable.message}")
                }
            )
        }
        Column(
            modifier = Modifier
                .padding(12.dp)
                .background(MyTheme.colorScheme.background)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            openGraph?.title?.let {
                Text(
                    text = it,
                    color = MyTheme.colorScheme.textNormal,
                    style = MyTheme.typography.bodyBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            val desc = openGraph?.description ?: "여기를 눌러 링크를 확인하세요."
            Text(
                text = desc,
                maxLines = 1,
                color = MyTheme.colorScheme.textAlt,
                style = MyTheme.typography.labelMedium,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = URL(url).host,
                maxLines = 1,
                color = MyTheme.colorScheme.textAlt,
                style = MyTheme.typography.labelMedium,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}