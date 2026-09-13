//package com.example.mamabear.ui.screens.health

package com.example.mamabear.ui.screens.health

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.mamabear.data.repository.RecordRepository
import com.example.mamabear.ui.theme.MamaBearPurple
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordAncVisitScreen(
    navController: NavController ,
    motherId : String
) {

    val scope = rememberCoroutineScope()
    val repository = remember { RecordRepository() }

   // var motherId by remember { mutableStateOf("") }
    var visitNumber by remember { mutableStateOf("") }
    var bloodPressure by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var labResults by remember { mutableStateOf("") }
    var vaccine by remember { mutableStateOf("") }

    var isSaving by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    val currentProfessional =
        FirebaseAuth.getInstance().currentUser

    val today = remember {
        SimpleDateFormat(
            "dd MMM yyyy",
            Locale.getDefault()
        ).format(Date())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Record ANC Visit",
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F6FB))
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Enter Mother's Visit Information",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MamaBearPurple
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Record the information provided during today's antenatal visit.",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(25.dp))


            // VISIT NUMBER
            OutlinedTextField(
                value = visitNumber,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        visitNumber = it
                        message = ""
                    }
                },
                label = {
                    Text("ANC Visit Number")
                },
                placeholder = {
                    Text("Example: 1")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // BLOOD PRESSURE
            OutlinedTextField(
                value = bloodPressure,
                onValueChange = {
                    bloodPressure = it
                    message = ""
                },
                label = {
                    Text("Blood Pressure")
                },
                placeholder = {
                    Text("Example: 110/70")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // WEIGHT
            OutlinedTextField(
                value = weight,
                onValueChange = {
                    weight = it
                    message = ""
                },
                label = {
                    Text("Weight (kg)")
                },
                placeholder = {
                    Text("Example: 62")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // LAB RESULTS
            OutlinedTextField(
                value = labResults,
                onValueChange = {
                    labResults = it
                    message = ""
                },
                label = {
                    Text("Lab Results")
                },
                placeholder = {
                    Text("Enter relevant laboratory results")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // VACCINE
            OutlinedTextField(
                value = vaccine,
                onValueChange = {
                    vaccine = it
                    message = ""
                },
                label = {
                    Text("Vaccination")
                },
                placeholder = {
                    Text("Example: Tetanus")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // DATE
            OutlinedTextField(
                value = today,
                onValueChange = {},
                label = {
                    Text("Visit Date")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                readOnly = true,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    color = if (message.startsWith("Error")) {
                        Color.Red
                    } else {
                        MamaBearPurple
                    },
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            // SAVE BUTTON
            Button(
                onClick = {

                    if (motherId.trim().isEmpty()) {
                        message = "Please enter the mother's ID."
                        return@Button
                    }

                    if (visitNumber.trim().isEmpty()) {
                        message = "Please enter the ANC visit number."
                        return@Button
                    }

                    val visit = visitNumber.toIntOrNull()

                    if (visit == null || visit !in 1..8) {
                        message = "ANC visit number must be between 1 and 8."
                        return@Button
                    }

                    scope.launch {

                        isSaving = true
                        message = ""

                        try {

                            val professionalName =
                                currentProfessional?.email
                                    ?: "Health Professional"

                            val record = HealthRecordsModel(
                                motherId = motherId.trim(),
                                bloodPressure = bloodPressure.trim(),
                                weight = weight.trim(),
                                labResults = labResults.trim(),
                                vaccine = vaccine.trim(),
                                date = today,
                                visitNumber = visit,
                                recordedBy = professionalName
                            )

                            repository.insertRecord(record)

                            message =
                                "ANC visit recorded successfully."

                        } catch (e: Exception) {

                            message =
                                "Error: ${e.localizedMessage ?: "Unable to save record."}"

                        } finally {

                            isSaving = false
                        }
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                enabled = !isSaving,

                shape = RoundedCornerShape(18.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = MamaBearPurple
                )
            ) {

                if (isSaving) {

                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )

                } else {

                    Text(
                        text = "Save ANC Visit",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}