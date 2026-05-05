package com.example.mamabear.ui.screens.Onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mamabear.R
import com.example.mamabear.ui.navigation.ROUTES
import com.example.mamabear.ui.screens.authentication.LottieAnimationWidget

@Composable
fun OnboardingScreen(navController: NavHostController, modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        LottieAnimationWidget(R.raw.pregnancy_icon ,280.dp)
        LottieAnimationWidget(R.raw.piggy_bank ,280 .dp)
        LottieAnimationWidget(R.raw.calendar_icon ,280 .dp)

        OutlinedButton(
            onClick = {
                navController.navigate(ROUTES.Login.name)
            }
        ) {
            Text(
                text = "Get started(login page)"
            )
        }
    }
}

@Composable
fun LottieAnimationWidget(drawable: Int, size:Dp) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(drawable))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(size)
    )
}
