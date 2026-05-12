package com.example.mamabear.ui.screens.reminders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Notifications
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

import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import com.example.mamabear.data.models.ReminderModel

@Composable
fun ReminderScreen(
    navController: NavController,
    viewModel: ReminderViewModel = viewModel()
) {
    val reminders by viewModel.reminders.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var reminderToEdit by remember { mutableStateOf<ReminderModel?>(null) }

    val suggestionsWithDescriptions = mapOf(
        "Prenatal Vitamin Intake" to "Fuel your body and baby with essential nutrients.",
        "Upcoming Clinic Visit" to "Stay on track with your checkups for a healthy journey.",
        "Daily Kick Counter" to "Bond with your baby and monitor their little movements.",
        "Anomaly Scan Appointment" to "A special peek to ensure everything is developing perfectly.",
        "Hospital Bag Preparation" to "Get your essentials ready for the big day!",
        "Birth Plan Discussion" to "Voice your preferences for a comfortable delivery.",
        "Nutrition Boost" to "Eat well-balanced meals to support your growing bump.",
        "Maternity Savings" to "Small steps today for a bright future with your little one."
    )

    if (showDialog || reminderToEdit != null) {
        AddReminderDialog(
            suggestions = suggestionsWithDescriptions,
            reminderToEdit = reminderToEdit,
            onDismiss = { 
                showDialog = false
                reminderToEdit = null
            },
            onSave = { title, desc, date ->
                if (reminderToEdit != null) {
                    viewModel.updateReminder(reminderToEdit!!.copy(title = title, description = desc, date = date))
                } else {
                    viewModel.addReminder(title, desc, date)
                }
                showDialog = false
                reminderToEdit = null
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MamaBearPurple,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Reminder")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F6FB))
                .padding(padding)
                .padding(20.dp)
        ) {

            Text(
                text = "Reminders",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MamaBearPurple
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Stay updated with your pregnancy care",
                color = Color.Gray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (reminders.isEmpty()) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text("No reminders set. Tap + to add one!", color = Color.Gray)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(reminders) { reminder ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = null,
                                    tint = MamaBearPurple,
                                    modifier = Modifier.size(40.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = reminder.title,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.DarkGray
                                    )
                                    Text(
                                        text = reminder.description,
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = reminder.date,
                                        fontSize = 12.sp,
                                        color = MamaBearPurple
                                    )
                                }
                                IconButton(onClick = { reminderToEdit = reminder }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Gray)
                                }
                                IconButton(onClick = { viewModel.deleteReminder(reminder.id) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun AddReminderDialog(
    suggestions: Map<String, String>,
    reminderToEdit: ReminderModel? = null,
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    var title by remember { mutableStateOf(reminderToEdit?.title ?: "") }
    var desc by remember { mutableStateOf(reminderToEdit?.description ?: "") }
    var date by remember { mutableStateOf(reminderToEdit?.date ?: "") }
    var showSuggestions by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (reminderToEdit != null) "Edit Reminder" else "Add New Reminder") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Box {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            IconButton(onClick = { showSuggestions = !showSuggestions }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                        }
                    )
                    DropdownMenu(expanded = showSuggestions, onDismissRequest = { showSuggestions = false }) {
                        suggestions.keys.forEach { suggestionTitle ->
                            DropdownMenuItem(
                                text = { Text(suggestionTitle) },
                                onClick = {
                                    title = suggestionTitle
                                    desc = suggestions[suggestionTitle] ?: ""
                                    showSuggestions = false
                                }
                            )
                        }
                    }
                }
                OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date (e.g., 12 Nov)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onSave(title, desc, date) }) {
                Text(if (reminderToEdit != null) "Update" else "Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
