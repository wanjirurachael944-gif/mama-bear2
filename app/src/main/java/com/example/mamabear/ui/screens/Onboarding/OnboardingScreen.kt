package com.example.mamabear.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.toArgb
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.*
import com.example.mamabear.R

val PurplePrimary = Color(0xFF9C6ADE)

@Composable
fun OnboardingScreen(navController: NavController) {

    var currentPage by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F6FB))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(560.dp),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // ⭐ LOTTIE FROM RAW FOLDER (FIXED)
                val composition by rememberLottieComposition(
                    LottieCompositionSpec.RawRes(
                        when (currentPage) {
                            0 -> R.raw.pregnancy
                            1 -> R.raw.health_json
                            2 -> R.raw.calendar_icon
                            else -> R.raw.piggy_bank
                        }
                    )
                )

                val progress by animateLottieCompositionAsState(
                    composition,
                    iterations = LottieConstants.IterateForever
                )

                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier.size(
                        when (currentPage) {
                            0 -> 350.dp
                            1 -> 320.dp
                            2 -> 380.dp
                            else -> 380.dp
                        }
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = when (currentPage) {
                        0 -> ""
                        1 -> "Track your Health"
                        2 -> "Smart reminders"
                        else -> "Save with a purpose"
                    },
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = PurplePrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = when (currentPage) {
                        0 -> "Your journey to a healthy pregnancy begins here."
                        1 -> "Track your Health,keep all your clinic visits ,checkups and health records in one place"
                        2 -> "Never miss important appointments ,medicine or vaccination."
                        else -> "Save a little at a time and be ready for your baby's needs."


                    },
                    fontSize = 20.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(54.dp))

        // DOTS
        Row {
            repeat(4) { index ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (currentPage == index) 12.dp else 8.dp)
                        .background(
                            color = if (currentPage == index) PurplePrimary else Color.LightGray,
                            shape = RoundedCornerShape(50)
                        )
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // BUTTON
        Button(
            onClick = {
                if (currentPage < 3) {
                    currentPage++
                } else {
                    navController.navigate("welcome") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
        ) {
            Text(
                text = if (currentPage == 3) "Get Started" else "Next",
                color = Color.White,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}