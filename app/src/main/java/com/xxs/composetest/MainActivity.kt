package com.xxs.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xxs.composetest.ui.theme.ComposeTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AmbientBackgroundPreview()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AmbientBackgroundPreview() {
    val images = remember {
        listOf(
            R.drawable.picture_one,
            R.drawable.picture_two,
            R.drawable.picture_three,
        )
    }
    val pagerState = rememberPagerState { images.size }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        AnimatedContent(
            targetState = pagerState.targetPage,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(durationMillis = 1500)
                ).togetherWith(
                    fadeOut(
                        animationSpec = tween(durationMillis = 1800)
                    )
                )
            }
        ) {target->
            Image(
                painter = painterResource(images[target]),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
                    .blur(50.dp),
                contentScale = ContentScale.FillBounds
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        )
                    )
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 18.dp)
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
                Column {
                    Text(
                        text = "old black",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White
                        ) {
                            Text(
                                text = "G",
                                modifier = Modifier
                                    .size(16.dp)
                                    .wrapContentSize(),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        Text(
                            text = "88,888",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                    }
                }

            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SmallIconButton(
                    icon = Icons.Default.Share
                )
                SmallIconButton(
                    icon = Icons.Default.Notifications
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 80.dp),
            contentPadding = PaddingValues(horizontal = 20.dp),
            pageSpacing = 8.dp
        ) {index->
            Image(
                painter = painterResource(images[index]),
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

        }
    }
}

@Composable
fun SmallIconButton(icon: ImageVector) {
    Surface(
        shape = CircleShape,
        color = Color.Black.copy(alpha = 0.1f),
        contentColor = Color.White,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = icon.name,
            modifier = Modifier
                .size(32.dp)
                .padding(6.dp)
        )
    }
}