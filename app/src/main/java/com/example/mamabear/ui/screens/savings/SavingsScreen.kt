package com.example.mamabear.ui.screens.savings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Savings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mamabear.ui.theme.MamaBearPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavingsScreen(
    navController: NavController,
    viewModel: SavingsViewModel = viewModel()
) {
    val savingsList by viewModel.savings.collectAsState()
    val totalSaved = savingsList.sumOf { it.amountSaved }
    val goal = 10000 // Fixed goal for now
    val progress = if (goal > 0) totalSaved.toFloat() / goal else 0f
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AddSavingsDialog(
            onDismiss = { showDialog = false },
            onSave = { amount ->
                viewModel.addSavings(amount)
                showDialog = false
            }
        )
    }

    Scaffold(
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF6F1FF))
                .padding(padding)
        ) {

            // 🔥 TOP SECTION (REDUCED HEIGHT)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MamaBearPurple)
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(text = "My Goal", fontWeight = FontWeight.Bold)
                        Text(text = "Prepare for baby needs", color = Color.Gray)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "KES $goal",
                            fontSize = 32.sp,
                            color = MamaBearPurple,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔥 MOTIVATIONAL NOTE
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Savings, contentDescription = null, tint = Color(0xFFFBC02D))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Every small coin saved is a big step towards a comfortable arrival for your little one! ✨",
                        fontSize = 13.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔥 SAVED CARD
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "KES $totalSaved",
                        fontSize = 30.sp,
                        color = MamaBearPurple,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Saved so far", color = Color.Gray)
                    Spacer(modifier = Modifier.height(20.dp))
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxWidth().height(10.dp),
                        color = MamaBearPurple,
                        trackColor = Color(0xFFE7D9FF)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "${(progress * 100).toInt()}% completed", color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Recent Transactions",
                modifier = Modifier.padding(horizontal = 20.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(savingsList) { saving ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Savings, contentDescription = null, tint = MamaBearPurple)
                            Spacer(modifier = Modifier.width(15.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "+KES ${saving.amountSaved}", fontWeight = FontWeight.Bold)
                                Text(text = "Contribution", color = Color.Gray)
                            }
                            Icon(Icons.Default.ArrowForwardIos, contentDescription = null, tint = Color.Gray)
                        }
                    }
                }
            }

            Button(
                onClick = { showDialog = true },
                modifier = Modifier.fillMaxWidth().padding(20.dp).height(55.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MamaBearPurple)
            ) {
                Text(text = "Add Savings", fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun AddSavingsDialog(
    onDismiss: () -> Unit,
    onSave: (Int) -> Unit
) {
    var amount by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Savings") },
        text = {
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount (KES)") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(onClick = { amount.toIntOrNull()?.let { onSave(it) } }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
