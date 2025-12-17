package com.xxs.composeTest6.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xxs.composetest.R

@Preview(showBackground = true)
@Composable
fun ParallaxScrollPage(){
    val bgs = remember {
        listOf(
            R.drawable.picture_one,
            R.drawable.picture_two,
            R.drawable.picture_three,
        )
    }
    val pagerState = rememberPagerState{ bgs.size}
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
    ) { index ->
        val pageOffset = (pagerState.currentPage-index)+pagerState.currentPageOffsetFraction
        Image(
            painter = painterResource(bgs[index]),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 30.dp)
                .clip(RoundedCornerShape(16.dp))
                .graphicsLayer(
                    translationX = pageOffset * 300
                ),
            contentScale = ContentScale.FillHeight
        )
    }
}
