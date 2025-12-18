package com.xxs.composeTest7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xxs.composeTest3.presentation.AnimatedBorderCard
import com.xxs.composeTest3.presentation.AnimatedBorderCardPreview
import com.xxs.composeTest4.presentation.SwiperScreen
import com.xxs.composeTest5.presentation.SharedTransitionScreen
import com.xxs.composeTest6.presentation.ParallaxScrollPage
import com.xxs.composetest.R
import com.xxs.composetest.ui.theme.ComposeTestTheme
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

class MainActivity7 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "splash"
                ) {
                    composable("splash") { SplashScreen(navController) }
                    composable("home") {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Home")
                        }
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun SplashScreen(
    navController: NavHostController = rememberNavController()
) {

    var step by remember { mutableStateOf<SplashStep>(SplashStep.None) }
    val transition = updateTransition(targetState = step, label = "transition")

    LaunchedEffect(Unit) {
        step = SplashStep.Start
        delay(800)
        step = SplashStep.Loading
        delay(800)
        step = SplashStep.End
        delay(600)
        navController.navigate("home"){
            popUpTo("splash") {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    val alpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 600) },
        label = "alpha"
    ) {
        when (it) {
            SplashStep.None,
            SplashStep.End -> 0f
            SplashStep.Loading,
            SplashStep.Start -> 1f
        }
    }
    val scale by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 600) },
        label = "scale"
    ) {
        when (it) {
            SplashStep.None,
            SplashStep.End -> 0f
            SplashStep.Loading,
            SplashStep.Start -> 1f
        }
    }
    val loadingWidth by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 600) },
        label = "width"
    ) {
        when (it) {
            SplashStep.Start,
            SplashStep.None,

            SplashStep.End -> 0f
            SplashStep.Loading -> 0.8f

        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.one_piece_logo),
                contentDescription = "logo",
                modifier = Modifier
                    .graphicsLayer {
                        this.alpha = alpha

                    }
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(loadingWidth)
                    .graphicsLayer {
                        this.alpha = alpha
                        scaleY = scale
                        scaleX = scale
                    },
                thickness = 3.dp,
                color = Color.Red
            )
        }
    }
}

sealed interface SplashStep {
    data object None : SplashStep
    data object Start : SplashStep
    data object End : SplashStep
    data object Loading : SplashStep
}