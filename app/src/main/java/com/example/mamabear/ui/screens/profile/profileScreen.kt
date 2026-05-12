package com.example.mamabear.ui.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.mamabear.data.models.MotherModel
import com.example.mamabear.data.repository.MotherRepository
import com.example.mamabear.ui.theme.MamaBearPurple
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun ProfileScreen(
    navController: NavController
) {
    val repository = remember { MotherRepository() }
    var mother by remember { mutableStateOf<MotherModel?>(null) }
    var showEditProfileDialog by remember { mutableStateOf(false) }
    var showPregnancyDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            scope.launch {
                try {
                    val currentUser = auth.currentUser
                    if (currentUser != null && mother != null) {
                        val fileName = "${currentUser.uid}_profile.jpg"
                        val storageRef = FirebaseStorage.getInstance().getReference("profile_pictures/$fileName")
                        
                        storageRef.putFile(it).await()
                        val publicUrl = storageRef.downloadUrl.await().toString()
                        
                        val updatedMother = mother!!.copy(profilePictureUrl = publicUrl)
                        repository.updateMother(updatedMother)
                        mother = updatedMother
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            mother = repository.getMotherById(currentUser.uid)
        }
    }

    if (showEditProfileDialog && mother != null) {
        EditProfileDialog(
            mother = mother!!,
            onDismiss = { showEditProfileDialog = false },
            onSave = { updatedMother ->
                scope.launch {
                    repository.updateMother(updatedMother)
                    mother = updatedMother
                    showEditProfileDialog = false
                }
            }
        )
    }

    if (showPregnancyDialog && mother != null) {
        UpdatePregnancyDialog(
            currentWeeks = mother!!.weeksPregnant,
            onDismiss = { showPregnancyDialog = false },
            onSave = { newWeeks ->
                scope.launch {
                    val updatedMother = mother!!.copy(weeksPregnant = newWeeks)
                    repository.updateMother(updatedMother)
                    mother = updatedMother
                    showPregnancyDialog = false
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "My Profile",
            color = MamaBearPurple,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier.size(130.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF3E5F5)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            onClick = { imagePickerLauncher.launch("image/*") }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (mother?.profilePictureUrl != null) {
                    AsyncImage(
                        model = mother?.profilePictureUrl,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(70.dp),
                        tint = MamaBearPurple
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = mother?.name ?: "Loading...",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF311B92)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = mother?.email ?: "",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        ProfileOptionItem(
            title = "Personal Details",
            subtitle = "Update your maternal and spouse info",
            icon = Icons.Default.Person,
            onClick = { navController.navigate("personalDetails") }
        )

        ProfileOptionItem(
            title = "Pregnancy Progress",
            subtitle = "${mother?.weeksPregnant ?: "0"} weeks (${getTrimester(mother?.weeksPregnant ?: "0")})",
            icon = Icons.Default.Info,
            onClick = { showPregnancyDialog = true }
        )

        ProfileOptionItem(
            title = "Settings",
            subtitle = "Security & Notifications",
            icon = Icons.Default.Settings,
            onClick = { navController.navigate("settings") }
        )

        ProfileOptionItem(
            title = "Help & Support",
            subtitle = "FAQs & Customer Care",
            icon = Icons.Default.SupportAgent,
            onClick = { navController.navigate("support") }
        )

        ProfileOptionItem(
            title = "Privacy Policy",
            subtitle = "How we handle your data",
            icon = Icons.Default.PrivacyTip,
            onClick = { navController.navigate("privacy") }
        )

        ProfileOptionItem(
            title = "About MamaBear",
            subtitle = "Version 1.0.1",
            icon = Icons.AutoMirrored.Filled.HelpOutline,
            onClick = { navController.navigate("about") }
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                auth.signOut()
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFEBEE)
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null, tint = Color.Red)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Logout",
                    color = Color.Red,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun ProfileOptionItem(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F3FF)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A148C)
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

fun getTrimester(weeks: String): String {
    val w = weeks.toIntOrNull() ?: 0
    return when {
        w <= 0 -> "Not Set"
        w <= 13 -> "1st Trimester"
        w <= 26 -> "2nd Trimester"
        else -> "3rd Trimester"
    }
}

@Composable
fun EditProfileDialog(
    mother: MotherModel,
    onDismiss: () -> Unit,
    onSave: (MotherModel) -> Unit
) {
    var name by remember { mutableStateOf(mother.name) }
    var phone by remember { mutableStateOf(mother.phone) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Profile") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onSave(mother.copy(name = name, phone = phone)) }) {
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

@Composable
fun UpdatePregnancyDialog(
    currentWeeks: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var weeks by remember { mutableStateOf(currentWeeks) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Update Pregnancy Progress") },
        text = {
            Column {
                Text("How many weeks pregnant are you?", fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = weeks,
                    onValueChange = { if (it.all { char -> char.isDigit() }) weeks = it },
                    label = { Text("Weeks") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onSave(weeks) }) {
                Text("Update")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
