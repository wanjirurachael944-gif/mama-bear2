package com.example.mamabear.ui.screens.health

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.navigation.NavController
import com.example.mamabear.data.models.HealthRecordsModel
import com.example.mamabear.ui.theme.MamaBearPurple
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HealthRecordsScreen(
    navController: NavController,
    viewModel: HealthRecordsViewModel = viewModel()
) {
    val recordsList by viewModel.records.collectAsState()
    val mother by viewModel.mother.collectAsState()
    val reminders by viewModel.reminders.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var recordToEdit by remember { mutableStateOf<HealthRecordsModel?>(null) }
    var selectedTab by remember { mutableStateOf("Overview") }

    val filteredRecords = remember(recordsList, selectedTab) {
        when (selectedTab) {
            "Visits" -> recordsList.filter { it.bloodPressure.isNotEmpty() || it.weight.isNotEmpty() }.sortedByDescending { it.date }
            "Vaccinations" -> recordsList.filter { it.vaccine.isNotEmpty() }.sortedByDescending { it.date }
            else -> recordsList.sortedByDescending { it.date }
        }
    }

    val weeks = mother?.weeksPregnant?.toIntOrNull() ?: 0
    val trimester = viewModel.getTrimester()
    val trimesterText = when (trimester) {
        1 -> "1st Trimester"
        2 -> "2nd Trimester"
        3 -> "3rd Trimester"
        else -> "Unknown"
    }

    if (showAddDialog) {
        HealthRecordDialog(
            onDismiss = { showAddDialog = false },
            onSave = { bp, weight, lab, vaccine, date ->
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    viewModel.addRecord(
                        HealthRecordsModel(
                            motherId = user.uid,
                            bloodPressure = bp,
                            weight = weight,
                            labResults = lab,
                            vaccine = vaccine,
                            date = date
                        )
                    )
                }
                showAddDialog = false
            }
        )
    }

    if (recordToEdit != null) {
        HealthRecordDialog(
            record = recordToEdit,
            onDismiss = { recordToEdit = null },
            onSave = { bp, weight, lab, vaccine, date ->
                viewModel.updateRecord(
                    recordToEdit!!.copy(
                        bloodPressure = bp,
                        weight = weight,
                        labResults = lab,
                        vaccine = vaccine,
                        date = date
                    )
                )
                recordToEdit = null
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MamaBearPurple,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Record")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Health Records",
                color = MamaBearPurple,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 🔥 PROGRESS SUMMARY CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE1F5FE)) // Light Blue
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.TrendingUp, contentDescription = null, tint = Color(0xFF0288D1))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Mother's Progress",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0288D1)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = viewModel.getProgressSummary(), fontSize = 14.sp, color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 🔥 TRIMESTER INFO CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MamaBearPurple.copy(alpha = 0.15f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = MamaBearPurple)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "$weeks Weeks Pregnant • $trimesterText",
                            fontWeight = FontWeight.Bold,
                            color = MamaBearPurple
                        )
                        val suggestion = when (trimester) {
                            1 -> "Remember to take Folic Acid and attend your first ANC visit."
                            2 -> "Schedule an anomaly scan and start thinking about birth plans."
                            3 -> "Keep track of baby kicks and prepare your hospital bag."
                            else -> "Stay hydrated and get plenty of rest."
                        }
                        Text(text = suggestion, fontSize = 12.sp, color = Color.DarkGray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("Overview", "Visits", "Vaccinations").forEach { tab ->
                    FilterChip(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        label = { Text(tab) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MamaBearPurple,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (reminders.isNotEmpty()) {
                Text(
                    text = "Upcoming Visits & Suggestions",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFFE64A19)
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(modifier = Modifier.heightIn(max = 150.dp)) {
                    items(reminders) { reminder ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color(0xFFEF6C00), modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(text = reminder.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(text = reminder.date, fontSize = 12.sp, color = Color.Gray)
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Visit History",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                TextButton(onClick = { navController.navigate("reminders") }) {
                    Text("Add Reminder", color = MamaBearPurple)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(filteredRecords) { record ->
                    HealthRecordItem(
                        record = record,
                        onEdit = { recordToEdit = record },
                        onDelete = { viewModel.deleteRecord(record.id) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun HealthRecordItem(
    record: HealthRecordsModel,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F3FF)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.MedicalServices,
                contentDescription = null,
                tint = MamaBearPurple
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (record.date.isNotEmpty()) record.date else "Unknown Date",
                    color = MamaBearPurple,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "BP: ${record.bloodPressure}, Weight: ${record.weight}",
                    fontWeight = FontWeight.Bold
                )
                if (record.labResults.isNotEmpty()) {
                    Text(
                        text = "Lab: ${record.labResults}",
                        color = Color.Gray
                    )
                }
                if (record.vaccine.isNotEmpty()) {
                    Text(
                        text = "Vaccine: ${record.vaccine}",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = Color.Gray)
                }
                DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                    DropdownMenuItem(
                        text = { Text("Edit") },
                        onClick = {
                            showMenu = false
                            onEdit()
                        },
                        leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) }
                    )
                    DropdownMenuItem(
                        text = { Text("Delete") },
                        onClick = {
                            showMenu = false
                            onDelete()
                        },
                        leadingIcon = { Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthRecordDialog(
    record: HealthRecordsModel? = null,
    onDismiss: () -> Unit,
    onSave: (String, String, String, String, String) -> Unit
) {
    var bp by remember { mutableStateOf(record?.bloodPressure ?: "") }
    var weight by remember { mutableStateOf(record?.weight ?: "") }
    var lab by remember { mutableStateOf(record?.labResults ?: "") }
    var vaccine by remember { mutableStateOf(record?.vaccine ?: "") }
    var date by remember { mutableStateOf(record?.date ?: SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())) }

    val vaccineSuggestions = listOf("Tetanus (Tdap)", "Flu Shot", "Hepatitis B", "COVID-19")
    var showVaccineDropdown by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (record == null) "Add Health Record" else "Edit Health Record") },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Visit Date") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = bp,
                    onValueChange = { bp = it },
                    label = { Text("Blood Pressure") },
                    placeholder = { Text("e.g. 120/80") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Weight (kg)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = lab,
                    onValueChange = { lab = it },
                    label = { Text("Lab Results") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Box {
                    OutlinedTextField(
                        value = vaccine,
                        onValueChange = { vaccine = it },
                        label = { Text("Vaccine Administered") },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            IconButton(onClick = { showVaccineDropdown = true }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = showVaccineDropdown,
                        onDismissRequest = { showVaccineDropdown = false }
                    ) {
                        vaccineSuggestions.forEach { suggestion ->
                            DropdownMenuItem(
                                text = { Text(suggestion) },
                                onClick = {
                                    vaccine = suggestion
                                    showVaccineDropdown = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(bp, weight, lab, vaccine, date) },
                colors = ButtonDefaults.buttonColors(containerColor = MamaBearPurple)
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
