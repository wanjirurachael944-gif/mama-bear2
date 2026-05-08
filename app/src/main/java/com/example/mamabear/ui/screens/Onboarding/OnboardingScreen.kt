package com.example.mamabear.screens

import android.R.attr.text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mamabear.R
import com.example.mamabear.ui.theme.PurplePrimary
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen( navController: NavController) {
    Button(
        onClick={navController.navigate("SignupScreen")},
        colors =ButtonDefaults.buttonColors(containerColor = Color(0xFF9C6ADE))
    ){
        Text("Test")
    }

    val purple = Color(0xFF7B2CBF)

    val images = listOf(
        R.drawable.pregnancy_icon,
       // R.drawable.,
        //R.drawable.,
        R.drawable.pregnancy_icon,
        R.drawable.pregnancy_icon
    )

    val titles = listOf(
        "Welcome to MamaBear",
        "Track Your Health",
        "Smart Reminders",
        "Save With Purpose",
        "You've Got This Mama!"
    )

    val descriptions = listOf(
        "Your journey to a healthy pregnancy starts here",
        "Keep all your clinic visits and records in one place",
        "Never miss appointments or medication again",
        "Prepare financially for your baby’s needs",
        "We are here to support you every step"
    )

    val pagerState = rememberPagerState(
        pageCount = { images.size }
    )

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        HorizontalPager(
            state = pagerState
        ) { page ->

            val image = images[page]
            val title = titles[page]
            val description = descriptions[page]

            Column(
                modifier = Modifier.fillMaxWidth(),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(40.dp))

                Image(
                    painter = painterResource(id = R.drawable.baseline_visibility_24),
                    contentDescription = null,
                    modifier = Modifier.size(280.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = purple
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = description,
                    textAlign = TextAlign.Center,
                    fontSize = 17.sp,
                    color = Color.DarkGray
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row {

                repeat(images.size) { index ->

                    Indicator(
                        isSelected = pagerState.currentPage == index
                    )

                    Spacer(modifier = Modifier.size(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {

                    scope.launch {

                        if (pagerState.currentPage < images.lastIndex) {

                            pagerState.animateScrollToPage(
                                pagerState.currentPage + 1
                            )

                        } else {

                            // Navigate to login screen here




                        }
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape = RoundedCornerShape(16.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = purple
                )
            ) {

                Text(
                    text =
                        if (pagerState.currentPage == images.lastIndex)
                            "Get Started"
                        else
                            "Next",

                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {

    val color =
        if (isSelected)
            Color(0xFF7B2CBF)
        else
            Color.LightGray

    Spacer(
        modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(color)
    )
}