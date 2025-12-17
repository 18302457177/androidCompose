package com.xxs.composeTest4.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.xxs.composetest.R
import kotlinx.coroutines.delay
import kotlin.collections.listOf

@Composable
fun SwiperScreen(){
    val swiperList = remember{
        listOf(
            R.drawable.picture_one,
            R.drawable.picture_two,
            R.drawable.picture_three,
        )
    }
    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / swiperList.size+1
    ) {
        Int.MAX_VALUE
    }

    LaunchedEffect(
        pagerState
    ) {
        snapshotFlow { pagerState.isScrollInProgress }
            .collect { isScroll->
                if(!isScroll){
                    while ( true){
                        delay(3000)
                        if(!pagerState.isScrollInProgress){
                            pagerState.animateScrollToPage(
                                pagerState.currentPage+1
                            )
                        }
                    }
                }
            }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentPadding = PaddingValues(
                horizontal = 56.dp,
                vertical = 16.dp
            ),
            beyondViewportPageCount = 3
        ) {page->
            val index = page % swiperList.size
            Image(
                painter = painterResource(swiperList[index]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        val currentIndex = pagerState.currentPage % swiperList.size
                        scaleX = if(currentIndex == index) 1f else 0.8f
                        scaleY = if(currentIndex == index) 1f else 0.8f
                    }
                    .clip(RoundedCornerShape(18.dp))
            )

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)
        ) {
            repeat(swiperList.size){ index ->
                val isCurrent = pagerState.currentPage % swiperList.size == index
                Box(
                    modifier = Modifier
                        .height(5.dp)
                        .width(if(isCurrent) 9.dp else 5.dp)
                        .clip(CircleShape)
                        .background(color = if(isCurrent) Color.Blue else Color.LightGray)
                )
            }
        }
    }


}