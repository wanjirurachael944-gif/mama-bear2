package com.example.mamabear.ui.screens.health

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mamabear.ui.theme.MamaBearPurple

@Composable
fun HealthRecordsScreen(
    navController: NavController,
    viewModel: HealthRecordsViewModel = viewModel()
) {
    val recordsList by viewModel.records.collectAsState()
    val mother by viewModel.mother.collectAsState()
    val reminders by viewModel.reminders.collectAsState()

    var selectedTab by remember { mutableStateOf("Overview") }

    val filteredRecords = remember(recordsList, selectedTab) {
        when (selectedTab) {
            "Visits" -> recordsList
                .filter {
                    it.bloodPressure.isNotEmpty() ||
                            it.weight.isNotEmpty()
                }
                .sortedByDescending { it.date }

            "Vaccinations" -> recordsList
                .filter { it.vaccine.isNotEmpty() }
                .sortedByDescending { it.date }

            else -> recordsList.sortedByDescending { it.date }
        }
    }

    val weeks = mother?.weeksPregnant?.toIntOrNull() ?: 0
    val completedVisits = recordsList
        .map { it.visitNumber }
        .filter { it > 0 }
        .distinct()
        .size

    val totalVisits = 8

    val visitProgress = if (totalVisits > 0) {
        completedVisits.toFloat() / totalVisits
    } else {
        0f
    }

    val trimester = viewModel.getTrimester()

    val trimesterText = when (trimester) {
        1 -> "1st Trimester"
        2 -> "2nd Trimester"
        3 -> "3rd Trimester"
        else -> "Unknown"
    }

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            // TITLE
            Text(
                text = "Health Records",
                color = MamaBearPurple,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            // VIEW-ONLY INFORMATION
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MamaBearPurple.copy(alpha = 0.08f)
                )
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MamaBearPurple
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "These health records are provided by your health professional. You can view them here but cannot edit or delete them.",
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            // PROGRESS SUMMARY
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE1F5FE)
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.TrendingUp,
                            contentDescription = null,
                            tint = Color(0xFF0288D1)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "Mother's Progress",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0288D1)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = viewModel.getProgressSummary(),
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

// ANC VISIT PROGRESS
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = MamaBearPurple
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "ANC Visits",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MamaBearPurple
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "$completedVisits of $totalVisits visits completed",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    LinearProgressIndicator(
                        progress = { visitProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp),
                        color = MamaBearPurple,
                        trackColor = Color(0xFFE7D9FF)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${(visitProgress * 100).toInt()}% completed",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))
            // TRIMESTER INFORMATION
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MamaBearPurple.copy(alpha = 0.15f)
                )
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MamaBearPurple
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {

                        Text(
                            text = "$weeks Weeks Pregnant • $trimesterText",
                            fontWeight = FontWeight.Bold,
                            color = MamaBearPurple
                        )

                        val suggestion = when (trimester) {

                            1 -> "Remember to attend your ANC visits and follow your health professional's advice."

                            2 -> "Continue attending your scheduled ANC visits and keep your health records up to date."

                            3 -> "Continue your ANC visits and prepare for delivery with your health professional."

                            else -> "Stay connected with your health professional and attend your scheduled visits."
                        }

                        Text(
                            text = suggestion,
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // TABS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                listOf(
                    "Overview",
                    "Visits",
                    "Vaccinations"
                ).forEach { tab ->

                    FilterChip(
                        selected = selectedTab == tab,
                        onClick = {
                            selectedTab = tab
                        },
                        label = {
                            Text(tab)
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MamaBearPurple,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            // UPCOMING VISITS
            if (reminders.isNotEmpty()) {

                Text(
                    text = "Upcoming Visits & Suggestions",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFFE64A19)
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(
                    modifier = Modifier.heightIn(max = 150.dp)
                ) {

                    items(reminders) { reminder ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFF3E0)
                            )
                        ) {

                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Icon(
                                    imageVector = Icons.Default.CalendarToday,
                                    contentDescription = null,
                                    tint = Color(0xFFEF6C00),
                                    modifier = Modifier.size(20.dp)
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {

                                    Text(
                                        text = reminder.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )

                                    Text(
                                        text = reminder.date,
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }

            // VISIT HISTORY
            Text(
                text = when (selectedTab) {
                    "Vaccinations" -> "Vaccination History"
                    "Visits" -> "ANC Visit History"
                    else -> "Health Record History"
                },
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // RECORDS
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {

                items(filteredRecords) { record ->

                    HealthRecordItem(
                        record = record
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
@Composable
fun HealthRecordItem(
    record: com.example.mamabear.data.models.HealthRecordsModel
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F3FF)
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.MedicalServices,
                    contentDescription = null,
                    tint = MamaBearPurple
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {

                    Text(
                        text = if (record.date.isNotEmpty()) {
                            record.date
                        } else {
                            "Unknown Date"
                        },
                        color = MamaBearPurple,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    if (record.visitNumber > 0) {

                        Text(
                            text = "ANC Visit ${record.visitNumber} of 8",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            // BLOOD PRESSURE
            if (record.bloodPressure.isNotEmpty()) {

                Text(
                    text = "Blood Pressure: ${record.bloodPressure} mmHg",
                    fontWeight = FontWeight.Medium
                )
            }

            // WEIGHT
            if (record.weight.isNotEmpty()) {

                Text(
                    text = "Weight: ${record.weight} kg",
                    fontWeight = FontWeight.Medium
                )
            }

            // LAB RESULTS
            if (record.labResults.isNotEmpty()) {

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Lab Results: ${record.labResults}",
                    color = Color.Gray
                )
            }

            // VACCINE
            if (record.vaccine.isNotEmpty()) {

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Vaccine: ${record.vaccine}",
                    color = Color.Gray
                )
            }

            // HEALTH PROFESSIONAL
            if (record.recordedBy.isNotEmpty()) {

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Recorded by: ${record.recordedBy}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}