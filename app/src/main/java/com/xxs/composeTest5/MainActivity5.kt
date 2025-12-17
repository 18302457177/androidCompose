package com.xxs.composeTest5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.xxs.composeTest3.presentation.AnimatedBorderCard
import com.xxs.composeTest3.presentation.AnimatedBorderCardPreview
import com.xxs.composeTest4.presentation.SwiperScreen
import com.xxs.composeTest5.presentation.SharedTransitionScreen
import com.xxs.composetest.ui.theme.ComposeTestTheme

class MainActivity5 : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                SharedTransitionScreen()
            }
        }
    }
}