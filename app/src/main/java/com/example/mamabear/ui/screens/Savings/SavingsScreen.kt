package com.example.mamabear.ui.screens.Savings


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mamabear.ui.theme.Gray
import com.example.mamabear.ui.theme.White
import com.example.mamabear.ui.theme.PurplePrimary


@Composable
fun SavingsScreen() {

    val total = 10000
    val saved = 3500
    val progress = saved.toFloat() / total.toFloat()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F6FB))
            .padding(16.dp)
    ) {

        Text(
            text = "Savings ",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))


        //  MAIN CARD
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE7F6)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Text("Total Saved", color = Color(0xFF888888))

                Text(
                    text = "KES $saved",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = PurplePrimary
                )

                Text("Goal: KES total", color = Color(0xFF888888))

                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                    progress = progress,
                    color = PurplePrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 Add Savings Button
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
        ) {
            Text("Add Savings", color = Color(0XFFFFFFFF))
        }
    }
}