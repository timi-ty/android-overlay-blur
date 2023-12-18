package com.timisandbox.blur

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Demo()
        }
    }
}

@Composable
fun Demo() {
    //this will hold the size of the outermost box
    var containerSize: Size = Size.Zero
    Box(
        Modifier
            .fillMaxSize()
            .graphicsLayer {
                containerSize = size
            }) {
        //the same scroll state will be used for both images (regular and blurred)
        val horizontalScroll = rememberScrollState()
        val verticalScroll = rememberScrollState()
        Box(modifier = Modifier.align(Alignment.Center)) {
            Box(
                modifier = Modifier
                    .horizontalScroll(horizontalScroll)
                    .verticalScroll(verticalScroll)
            ) {
                Image(
                    modifier = Modifier
                        .height(1200.dp)
                        .alpha(1.0f),
                    painter = painterResource(id = R.drawable.blurwoman),
                    contentDescription = stringResource(id = R.string.no_desc),
                    contentScale = ContentScale.FillHeight
                )
            }
        }
        Box(
            modifier = Modifier
                .size(190.dp)
                .clip(CircleShape)
                .align(Alignment.Center)
                .blur(15.dp, BlurredEdgeTreatment.Unbounded)
        ) {
            Box(modifier = Modifier
                .horizontalScroll(horizontalScroll)
                .verticalScroll(verticalScroll)
                .graphicsLayer {
                    //offset the blurred image to coincide perfectly with the regular image
                    translationY = -(containerSize.height / 2f) + 95.dp.toPx()
                    translationX = -(containerSize.width / 2f) + 95.dp.toPx()
                }) {
                Image(
                    modifier = Modifier
                        .height(1200.dp)
                        .alpha(1.0f),
                    painter = painterResource(id = R.drawable.blurwoman),
                    contentDescription = stringResource(id = R.string.no_desc),
                    contentScale = ContentScale.FillHeight
                )
            }
        }
        Image(
            modifier = Modifier
                .size(240.dp)
                .align(Alignment.Center)
                .alpha(0.5f)
                .blur(5.dp, BlurredEdgeTreatment.Unbounded),
            painter = painterResource(id = R.drawable.blurdrop),
            contentDescription = stringResource(id = R.string.no_desc),
            contentScale = ContentScale.Inside
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DemoPreview() {
    Demo()
}