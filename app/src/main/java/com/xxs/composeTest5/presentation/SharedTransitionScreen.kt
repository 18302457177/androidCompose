package com.xxs.composeTest5.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.xxs.composetest.R
import com.xxs.composetest.presentation.OnePieceModel
import com.xxs.composetest.presentation.onePieceData


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScreen(){
    SharedTransitionLayout(
        modifier = Modifier.fillMaxSize(),
    ) {
        val detailItem = remember { mutableStateOf<OnePieceModel?>(null) }
        AnimatedContent(targetState = detailItem.value, label = "") {target->
            if(target == null){
                OnePieceGridList(this) {
                    detailItem.value = it
                }
            }else{
                OnePieceDetail(
                    animatedVisibilityScope = this,
                    item = target,
                ) {
                    detailItem.value = null
                }
            }
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.OnePieceGridList(
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClick:(OnePieceModel)-> Unit
){
    Scaffold(
        modifier = Modifier.sharedBounds(
            sharedContentState = rememberSharedContentState("bg"),
            animatedVisibilityScope = animatedVisibilityScope
        ),
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(R.drawable.one_piece_logo),
                        contentDescription = null,
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState("logo"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    )
                }
            )
        }
    ) {padding ->
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            columns = StaggeredGridCells.Fixed(2)
        ) {
            items(onePieceData){ item->
                Column(
                    modifier = Modifier.padding(8.dp)
                        .clickable{onClick(item)},
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(item.cover),
                        contentDescription = "img-${item.name}",
                        modifier = Modifier
                            .sizeIn(maxHeight = 360.dp)
                            .fillMaxWidth()
                            .sharedElement(
                                state = rememberSharedContentState("bg-${item.name}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.FillWidth
                    )
                    Text(
                        text = item.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(item.name),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.OnePieceDetail(
    animatedVisibilityScope: AnimatedVisibilityScope,
    item: OnePieceModel,
    upPress:()->Unit
){
    BackHandler(enabled = true) {upPress () }
    Scaffold(
        modifier = Modifier.fillMaxSize()
            .sharedBounds(
                sharedContentState = rememberSharedContentState("bg"),
                animatedVisibilityScope = animatedVisibilityScope
            ),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = item.name,
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(item.name),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {upPress ()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    Image(
                        painter = painterResource(R.drawable.one_piece_logo),
                        contentDescription = null,
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState("logo"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    )
                }
            )
        }
    ) { padding->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(item.cover),
                contentDescription = item.name,
                modifier = Modifier
                    .sharedElement(
                        state = rememberSharedContentState("bg-${item.name}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = item.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 30.dp)
            )
            Text(
                text = item.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(top = 60.dp, bottom = 20.dp)
            )
        }

    }
}

