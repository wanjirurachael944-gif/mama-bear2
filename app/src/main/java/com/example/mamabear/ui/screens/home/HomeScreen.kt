package com.example.mamabear.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mamabear.ui.theme.MamaBearPurple
import com.airbnb.lottie.compose.*
import com.example.mamabear.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadDashboardData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MamaBear 💜", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile", tint = MamaBearPurple)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = MamaBearPurple
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F6FB))
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            // 🔥 WELCOME TEXT
            Text(
                text = "Welcome ${uiState.mother?.name ?: "Mama"}!",
                fontSize = 18.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(30.dp))

            // 🔥 PREGNANCY CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MamaBearPurple
                )
            ) {
                Row(
                    modifier = Modifier.padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        val weeks = uiState.mother?.weeksPregnant?.toIntOrNull() ?: 0
                        val progress = if (weeks > 0) weeks / 40f else 0f

                        Text(
                            text = "$weeks Weeks Pregnant",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Your baby is growing beautifully 💜",
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp),
                            color = Color.White,
                            trackColor = Color(0xFFD8C2F2)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "${(progress * 100).toInt()}% completed",
                            color = Color.White
                        )
                    }

                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.pregnancy))
                    val lottieProgress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)
                    LottieAnimation(
                        composition = composition,
                        progress = { lottieProgress },
                        modifier = Modifier.size(110.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // 🔥 QUICK ACTIONS TITLE
            Text(
                text = "Quick Actions",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MamaBearPurple
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 🔥 RECENT REMINDERS SECTION (NEW)
            if (uiState.reminders.isNotEmpty()) {
                Text(
                    text = "Upcoming Reminders",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(12.dp))
                uiState.reminders.forEach { reminder ->
                    ReminderListItem(reminder = reminder)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // 🔥 HEALTH RECORDS CARD
            QuickActionCard(
                title = "Health Records",
                subtitle = uiState.latestRecord?.let { "Last BP: ${it.bloodPressure}" } ?: "Track blood pressure, health and weight",
                icon = Icons.Default.Favorite,
                onClick = { navController.navigate("healthrecords") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔥 SAVINGS CARD
            QuickActionCard(
                title = "Savings",
                subtitle = "Saved KES ${uiState.totalSaved} so far",
                icon = Icons.Default.Savings,
                onClick = { navController.navigate("savings") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔥 REMINDERS CARD
            QuickActionCard(
                title = "Reminders",
                subtitle = uiState.nextAppointment?.let { "${it.date} - ${it.title}" } ?: "View upcoming clinic visits",
                icon = Icons.Default.Notifications,
                onClick = { navController.navigate("reminders") }
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun ReminderListItem(reminder: com.example.mamabear.data.models.ReminderModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(MamaBearPurple.copy(alpha = 0.1f), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Notifications, contentDescription = null, tint = MamaBearPurple, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = reminder.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = reminder.date, color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MamaBearPurple,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}
