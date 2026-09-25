package com.example.cozybite.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cozybite.R
import com.example.cozybite.ui.theme.DarkBrown
import com.example.cozybite.ui.theme.SecondaryText
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun CustomSplashScreen(
    onFinished: () -> Unit
) {

    var visible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        visible = true
        delay(2200.milliseconds)
        onFinished()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(
                animationSpec = tween(800)
            ) + scaleIn(
                animationSpec = tween(800)
            )
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(R.drawable.cozybite_logo),
                    contentDescription = "CozyBite Logo",
                    modifier = Modifier.size(150.dp)
                )

                Text(
                    text = "CozyBite",
                    color = DarkBrown,
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = "Good food, cozy moments.",
                    color = SecondaryText
                )
            }
        }
    }
}