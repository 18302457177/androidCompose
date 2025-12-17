package com.xxs.composeTest3.presentation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedBorderCard(
    modifier: Modifier,
    shape: Shape = RoundedCornerShape(6.dp),
    borderWidth: Dp = 2.dp,
    gradient: Brush = Brush.sweepGradient(listOf(Color.Blue,Color.Green,Color.Cyan)),
    animationDuation:Int = 10000,
    content: @Composable () -> Unit
){
    val infiniteTransition = rememberInfiniteTransition(label = "border_color_animation")
    val degrees = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuation, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "degrees"
    )
    Surface(
        modifier = modifier
            .clip( shape),
        shape = shape
    ){
        Surface(
            modifier = Modifier.fillMaxWidth()
                .padding(borderWidth)
                .drawWithContent{
                    rotate(degrees = degrees.value){
                        drawCircle(
                            brush = gradient,
                            radius = size.width,
                            blendMode = BlendMode.SrcIn
                        )
                    }
                    drawContent()
                },
            color = MaterialTheme.colorScheme.surface,
            shape = shape
        ){
            content()
        }
    }
}
@Preview(showBackground = true)
@Composable
fun AnimatedBorderCardPreview(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        AnimatedBorderCard(
            modifier = Modifier.fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 18.dp)
        ) {
            Text(text = "Hello World")
        }
    }
}