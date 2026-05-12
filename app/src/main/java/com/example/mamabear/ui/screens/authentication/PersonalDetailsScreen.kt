package com.example.mamabear.ui.screens.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mamabear.ui.theme.MamaBearPurple
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

import com.example.mamabear.data.models.MotherModel
import com.example.mamabear.data.repository.MotherRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDetailsScreen(navController: NavController) {
    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var pregnancyWeek by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }
    var spouseName by remember { mutableStateOf("") }
    var spouseContact by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val repository = remember { MotherRepository() }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personal & Pregnancy Details", fontWeight = FontWeight.Bold) },
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
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Maternal Details", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MamaBearPurple)
            
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )

            Text("Pregnancy Details", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MamaBearPurple)

            OutlinedTextField(
                value = pregnancyWeek,
                onValueChange = { pregnancyWeek = it },
                label = { Text("Current Week of Pregnancy") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = dueDate,
                onValueChange = { dueDate = it },
                label = { Text("Expected Due Date (EDD)") },
                modifier = Modifier.fillMaxWidth()
            )

            Text("Spouse/Emergency Contact", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MamaBearPurple)

            OutlinedTextField(
                value = spouseName,
                onValueChange = { spouseName = it },
                label = { Text("Spouse's Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = spouseContact,
                onValueChange = { spouseContact = it },
                label = { Text("Spouse's Contact") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    if (userId != null) {
                        scope.launch {
                            val updatedMother = MotherModel(
                                id = userId,
                                name = fullName,
                                email = FirebaseAuth.getInstance().currentUser?.email ?: "",
                                phone = phoneNumber,
                                weeksPregnant = pregnancyWeek,
                                dueDate = dueDate,
                                spouseName = spouseName,
                                spouseContact = spouseContact
                            )
                            repository.updateMother(updatedMother)
                            // Also save spouse details to a different node if needed, 
                            // but for now let's keep it simple and just update the mother model
                            // or we can extend MotherModel to include spouse details.
                            
                            navController.navigate("home") {
                                popUpTo("personalDetails") { inclusive = true }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MamaBearPurple)
            ) {
                Text("Save and Continue", color = Color.White)
            }
        }
    }
}
