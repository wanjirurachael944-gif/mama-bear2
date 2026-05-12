package com.example.mamabear.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mamabear.ui.theme.MamaBearPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Policy", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Your Privacy Matters",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MamaBearPurple
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            PolicySection(
                title = "1. Data Collection",
                content = "We collect information you provide directly to us, such as when you create an account, update your pregnancy profile, or log health records. This includes your name, email, phone number, and pregnancy-related dates."
            )
            
            PolicySection(
                title = "2. How We Use Data",
                content = "Your data is used to provide personalized reminders, track your pregnancy journey, and manage your maternity savings. We do not sell your personal health information to third parties."
            )
            
            PolicySection(
                title = "3. Data Security",
                content = "We implement industry-standard security measures to protect your data. Your health records and profile information are stored securely using Firebase encryption."
            )
            
            PolicySection(
                title = "4. Your Rights",
                content = "You have the right to access, update, or delete your personal information at any time through the profile and settings sections of the app."
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Last Updated: May 2026",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun PolicySection(title: String, content: String) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(text = title, fontWeight = FontWeight.Bold, color = MamaBearPurple, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = content, fontSize = 14.sp, color = Color.DarkGray, lineHeight = 20.sp)
    }
}
