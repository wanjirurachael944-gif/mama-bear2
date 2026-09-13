package com.example.mamabear.ui.screens.health

//package com.example.mamabear.ui.screens.health

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mamabear.data.models.HealthRecordsModel
import com.example.mamabear.data.models.MotherModel
import com.example.mamabear.data.repository.MotherRepository
import com.example.mamabear.data.repository.RecordRepository
import com.example.mamabear.ui.theme.MamaBearPurple
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MotherHealthProfileScreen(
    navController: NavController,
    motherId: String
) {

    val motherRepository = remember { MotherRepository() }
    val recordRepository = remember { RecordRepository() }
    val scope = rememberCoroutineScope()

    var mother by remember {
        mutableStateOf<MotherModel?>(null)
    }

    var records by remember {
        mutableStateOf<List<HealthRecordsModel>>(emptyList())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(motherId) {

        scope.launch {

            try {

                isLoading = true

                mother = motherRepository.getMotherById(motherId)

                recordRepository
                    .getRecordsFlow(motherId)
                    .collect { loadedRecords ->
                        records = loadedRecords
                        isLoading = false
                    }

            } catch (e: Exception) {

                errorMessage =
                    e.localizedMessage
                        ?: "Failed to load mother's profile."

                isLoading = false
            }
        }
    }

    val latestRecord = records
        .sortedByDescending { it.date }
        .firstOrNull()

    val visitsCompleted = records
        .map { it.visitNumber }
        .filter { it > 0 }
        .distinct()
        .size

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text(
                        text = "Mother Health Profile",
                        fontWeight = FontWeight.Bold,
                        color = MamaBearPurple
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }

    ) { padding ->

        if (isLoading) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {

                CircularProgressIndicator(
                    color = MamaBearPurple
                )
            }

        } else if (errorMessage != null) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = errorMessage ?: "Something went wrong.",
                    color = Color.Red
                )
            }

        } else if (mother == null) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "Mother's profile could not be found.",
                    color = Color.Gray
                )
            }

        } else {

            LazyColumn(

                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF8F6FB))
                    .padding(padding)
                    .padding(horizontal = 20.dp),

                verticalArrangement = Arrangement.spacedBy(14.dp),

                contentPadding = PaddingValues(
                    top = 18.dp,
                    bottom = 30.dp
                )
            ) {

                item {

                    MotherInformationCard(
                        mother = mother!!
                    )
                }

                item {

                    PregnancyCard(
                        mother = mother!!
                    )
                }

                item {

                    ANCProgressCard(
                        visitsCompleted = visitsCompleted
                    )
                }

                item {

                    LatestHealthCard(
                        latestRecord = latestRecord
                    )
                }

                item {

                    Button(
                        onClick = {

                            navController.navigate(
                                "record_anc_visit/$motherId"
                            )
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),

                        shape = RoundedCornerShape(16.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = MamaBearPurple
                        )
                    ) {

                        Icon(
                            imageVector = Icons.Default.MedicalServices,
                            contentDescription = null
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        Text(
                            text = "Record New ANC Visit",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                item {

                    Text(
                        text = "ANC Visit History",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (records.isEmpty()) {

                    item {

                        Text(
                            text = "No ANC records have been recorded yet.",
                            color = Color.Gray
                        )
                    }

                } else {

                    items(
                        items = records.sortedByDescending { it.date },
                        key = { it.id }
                    ) { record ->

                        ANCRecordCard(
                            record = record
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MotherInformationCard(
    mother: MotherModel
) {

    ProfileCard {

        Text(
            text = mother.name.ifEmpty {
                "Unnamed Mother"
            },
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MamaBearPurple
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        ProfileRow(
            icon = Icons.Default.Person,
            label = "Phone",
            value = mother.phone.ifEmpty {
                "Not recorded"
            }
        )

        ProfileRow(
            icon = Icons.Default.Person,
            label = "Email",
            value = mother.email.ifEmpty {
                "Not recorded"
            }
        )
    }
}

@Composable
private fun PregnancyCard(
    mother: MotherModel
) {

    ProfileCard {

        Text(
            text = "Pregnancy Information",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        ProfileRow(
            icon = Icons.Default.Favorite,
            label = "Weeks Pregnant",
            value = mother.weeksPregnant.ifEmpty {
                "Not recorded"
            }
        )

        ProfileRow(
            icon = Icons.Default.CalendarToday,
            label = "Expected Due Date",
            value = mother.dueDate.ifEmpty {
                "Not recorded"
            }
        )
    }
}

@Composable
private fun ANCProgressCard(
    visitsCompleted: Int
) {

    val totalVisits = 8

    ProfileCard {

        Text(
            text = "ANC Progress",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "$visitsCompleted of $totalVisits ANC visits completed",
            fontSize = 15.sp,
            color = Color.Gray
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        LinearProgressIndicator(

            progress = {
                (visitsCompleted.toFloat() / totalVisits)
                    .coerceIn(0f, 1f)
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),

            color = MamaBearPurple
        )
    }
}

@Composable
private fun LatestHealthCard(
    latestRecord: HealthRecordsModel?
) {

    ProfileCard {

        Text(
            text = "Latest Health Information",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        ProfileRow(
            icon = Icons.Default.Favorite,
            label = "Blood Pressure",
            value = latestRecord?.bloodPressure?.takeIf {
                it.isNotEmpty()
            } ?: "Not recorded"
        )

        ProfileRow(
            icon = Icons.Default.Scale,
            label = "Weight",
            value = latestRecord?.weight?.takeIf {
                it.isNotEmpty()
            }?.let {
                "$it kg"
            } ?: "Not recorded"
        )

        ProfileRow(
            icon = Icons.Default.MedicalServices,
            label = "Vaccine",
            value = latestRecord?.vaccine?.takeIf {
                it.isNotEmpty()
            } ?: "Not recorded"
        )

        ProfileRow(
            icon = Icons.Default.MedicalServices,
            label = "Lab Results",
            value = latestRecord?.labResults?.takeIf {
                it.isNotEmpty()
            } ?: "Not recorded"
        )
    }
}

@Composable
private fun ANCRecordCard(
    record: HealthRecordsModel
) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(18.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = "Visit ${record.visitNumber}",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = MamaBearPurple
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = record.date.ifEmpty {
                    "Date not recorded"
                },
                color = Color.Gray,
                fontSize = 13.sp
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Blood Pressure: ${
                    record.bloodPressure.ifEmpty {
                        "Not recorded"
                    }
                }"
            )

            Text(
                text = "Weight: ${
                    record.weight.ifEmpty {
                        "Not recorded"
                    }
                } kg"
            )

            Text(
                text = "Vaccine: ${
                    record.vaccine.ifEmpty {
                        "Not recorded"
                    }
                }"
            )

            Text(
                text = "Lab Results: ${
                    record.labResults.ifEmpty {
                        "Not recorded"
                    }
                }"
            )

            if (record.recordedBy.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Recorded by: ${record.recordedBy}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
private fun ProfileCard(
    content: @Composable ColumnScope.() -> Unit
) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp),
            content = content
        )
    }
}

@Composable
private fun ProfileRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MamaBearPurple,
            modifier = Modifier.size(22.dp)
        )

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Column {

            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Gray
            )

            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}