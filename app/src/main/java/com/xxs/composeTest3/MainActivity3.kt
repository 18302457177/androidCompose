package com.xxs.composeTest3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.xxs.composeTest3.presentation.AnimatedBorderCard
import com.xxs.composeTest3.presentation.AnimatedBorderCardPreview
import com.xxs.composetest.ui.theme.ComposeTestTheme

class MainActivity3 : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                AnimatedBorderCardPreview()
            }
        }
    }
}