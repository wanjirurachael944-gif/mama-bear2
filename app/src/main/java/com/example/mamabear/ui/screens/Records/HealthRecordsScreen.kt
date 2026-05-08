package com.example.mamabear.ui.screens.Records


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mamabear.ui.theme.PurplePrimary

@Composable
fun HealthRecordScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F6FB))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text(
            text = "Health Records 📋",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        //  Clinic Visits
        Text("Clinic Visits", fontWeight = FontWeight.SemiBold, color = PurplePrimary)

        Spacer(modifier = Modifier.height(8.dp))

        RecordCard("12 May 2025", "Routine checkup")
        RecordCard("10 April 2025", "Blood pressure normal")

        Spacer(modifier = Modifier.height(16.dp))

        //  Vaccinations
        Text("Vaccinations", fontWeight = FontWeight.SemiBold, color = PurplePrimary)

        Spacer(modifier = Modifier.height(8.dp))

        RecordCard("Tetanus", "15 March 2025")
        RecordCard("Flu", "01 April 2025")

        Spacer(modifier = Modifier.height(20.dp))

        //  Add Button
        Button(
            onClick = { navController.navigate("addRecord") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
        ) {
            Text("Add Record", color = Color.White)
        }
    }
}
@Composable
fun RecordCard(title: String, subtitle: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Text(text = subtitle)
        }
    }
}

