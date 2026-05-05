package com.example.mamabear.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mamabear.ui.navigation.ROUTES
import com.example.mamabear.ui.theme.Background
import com.example.mamabear.ui.theme.CardWhite
import com.example.mamabear.ui.theme.PurplePrimary
import com.example.mamabear.ui.theme.TealAccent
import com.example.mamabear.ui.theme.TealLight
import com.example.mamabear.ui.theme.TextDark
import com.example.mamabear.ui.theme.TextLight
import com.example.mamabear.ui.theme.White

@Composable
fun DashboardScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp)
    ) {

        // 🔹 Greeting Section
        Text(
            text = "Hello Mama 👋",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        Text(
            text = "You’re doing great 💜",
            color = TextLight
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 Pregnancy Card (MAIN CARD)
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = PurplePrimary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Text(
                    text = "24 Weeks Pregnant",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "2nd Trimester",
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Next Visit Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardWhite),
            elevation = CardDefaults.cardElevation(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = TealAccent
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text("Next Clinic Visit", fontWeight = FontWeight.SemiBold)
                    Text("12 May 2025", color = PurplePrimary)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Savings Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardWhite),
            elevation = CardDefaults.cardElevation(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text("Savings Progress", fontWeight = FontWeight.SemiBold)

                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                    progress = 0.3f,
                    color = TealAccent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(10.dp))
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("KES 1,500 / 5,000", color = TextLight)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Tip Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = TealLight),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = PurplePrimary
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text("Drink plenty of water and get enough rest 💧")


            }
        }
    }
}