package com.example.mamabear.ui.screens.savings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx. compose. ui .window .Dialog
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mamabear.ui.theme.MamaBearPurple

data class BudgetCategory(
    val name: String,
    val selected: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavingsScreen(
    navController: NavController,
    viewModel: SavingsViewModel = viewModel()
) {

    val savingsList by viewModel.savings.collectAsState()

    // Calculate total savings
    val totalSaved = savingsList.sumOf { it.amountSaved }

    // Savings goal
    val goal = 10000

    // Prevent progress from going above 100%
    val progress = if (goal > 0) {
        (totalSaved.toFloat() / goal).coerceIn(0f, 1f)
    } else {
        0f
    }

    val remainingAmount = (goal - totalSaved).coerceAtLeast(0)

    var showDialog by remember {
        mutableStateOf(false)
    }

    // Budget categories
    var budgetItems by remember {
        mutableStateOf(
            listOf(
                BudgetCategory("🏥 Antenatal Care"),
                BudgetCategory("🚕 Transport"),
                BudgetCategory("👶 Baby Essentials"),
                BudgetCategory("💊 Medication"),
                BudgetCategory("🍎 Nutrition"),
                BudgetCategory("💜 My Own Needs"),
                BudgetCategory("🚨 Emergency Fund")
            )
        )
    }

    // Add Savings Dialog
    if (showDialog) {
        AddSavingsDialog(
            onDismiss = {
                showDialog = false
            },
            onSave = { amount ->
                viewModel.addSavings(amount)
                showDialog = false
            }
        )
    }

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF6F1FF))
                .padding(padding)
        ) {

            // ---------------------------------------------------------
            // TOP GOAL SECTION
            // ---------------------------------------------------------

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MamaBearPurple)
                    .padding(
                        horizontal = 20.dp,
                        vertical = 20.dp
                    )
            ) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {

                        Text(
                            text = "Mama Care Fund",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = "Prepare for you and your baby's needs",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        Text(
                            text = "Goal",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )

                        Text(
                            text = "KES $goal",
                            fontSize = 32.sp,
                            color = MamaBearPurple,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = "KES $remainingAmount remaining",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            // ---------------------------------------------------------
            // MOTIVATIONAL MESSAGE
            // ---------------------------------------------------------

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF9C4)
                )
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "💰",
                        fontSize = 24.sp
                    )

                    Spacer(
                        modifier = Modifier.width(12.dp)
                    )

                    Text(
                        text = "Every small saving is a big step towards a more prepared and comfortable motherhood. ✨",
                        fontSize = 13.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // ---------------------------------------------------------
            // SAVINGS PROGRESS
            // ---------------------------------------------------------

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "KES $totalSaved",
                        fontSize = 30.sp,
                        color = MamaBearPurple,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Saved so far",
                        color = Color.Gray
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp),
                        color = MamaBearPurple,
                        trackColor = Color(0xFFE7D9FF)
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "${(progress * 100).toInt()}% completed",
                        color = Color.Gray
                    )
                }
            }

            // ---------------------------------------------------------
            // BUDGET SECTION
            // ---------------------------------------------------------

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "My Budget",
                modifier = Modifier.padding(horizontal = 20.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "What would you like to prepare for?",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    budgetItems.forEachIndexed { index, item ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .toggleable(
                                    value = item.selected,
                                    role = Role.Checkbox,
                                    onValueChange = { checked ->

                                        budgetItems =
                                            budgetItems.toMutableList().apply {

                                                this[index] =
                                                    this[index].copy(
                                                        selected = checked
                                                    )
                                            }
                                    }
                                )
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Checkbox(
                                checked = item.selected,
                                onCheckedChange = null
                            )

                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )

                            Text(
                                text = item.name,
                                fontSize = 15.sp
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Button(
                        onClick = {
                            // Budget saving functionality
                            // can be connected to ViewModel/database later.
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MamaBearPurple
                        )
                    ) {

                        Text(
                            text = "Create My Budget"
                        )
                    }
                }
            }

            // ---------------------------------------------------------
            // SMART SAVINGS EDUCATION
            // ---------------------------------------------------------

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFEDE7F6)
                )
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "💡",
                        fontSize = 24.sp
                    )
                    Spacer(
                        modifier = Modifier.width(12.dp)
                    )

                    Column {

                        Text(
                            text = "Smart Savings",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = "Learn how to budget, save and prepare for unexpected expenses.",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        TextButton(
                            onClick = {
                                // Connect to Savings Education screen later
                            }
                        ) {

                            Text(
                                text = "Learn More",
                                color = MamaBearPurple
                            )
                        }
                    }
                }
            }

            // ---------------------------------------------------------
            // MAMA TIP
            // ---------------------------------------------------------

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF3E0)
                )
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "💡",
                        fontSize = 24.sp
                    )
                    Spacer(
                        modifier = Modifier.width(10.dp)
                    )

                    Text(
                        text = "Mama Tip: Saving KES 100 every week gives you KES 5,200 in a year.",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // ---------------------------------------------------------
            // RECENT TRANSACTIONS
            // ---------------------------------------------------------

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            Text(
                text = "Recent Transactions",
                modifier = Modifier.padding(horizontal = 20.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {

                items(savingsList) { saving ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 20.dp,
                                vertical = 8.dp
                            ),
                        shape = RoundedCornerShape(18.dp),
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


                            Text(
                                text = "💰",
                                fontSize = 24.sp
                            )

                            Spacer(
                                modifier = Modifier.width(15.dp)
                            )

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = "+KES ${saving.amountSaved}",
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "Contribution",
                                    color = Color.Gray
                                )
                            }
                            Text(
                                text = "›",
                                fontSize = 24.sp,
                                color = Color.Gray
                            )


                        }
                    }
                }
            }

            // ---------------------------------------------------------
            // ADD SAVINGS BUTTON
            // ---------------------------------------------------------

            Button(
                onClick = {
                    showDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(55.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MamaBearPurple
                )
            ) {

                Text(
                    text = "Save Money",
                    fontSize = 18.sp
                )
            }
        }
    }
}


// =====================================================================
// ADD SAVINGS DIALOG
// =====================================================================

@Composable
fun AddSavingsDialog(
    onDismiss: () -> Unit,
    onSave: (Int) -> Unit
) {

    var amount by remember {
        mutableStateOf("")
    }
    Dialog(
        onDismissRequest = onDismiss
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Text(
                    text = "💰 Save Money",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MamaBearPurple
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "How much would you like to save?",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                OutlinedTextField(
                    value = amount,
                    onValueChange = {
                        if (it.all { character ->
                                character.isDigit()
                            }) {
                            amount = it
                        }
                    },
                    label = {
                        Text("Amount (KES)")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    TextButton(
                        onClick = onDismiss
                    ) {
                        Text("Cancel")
                    }

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Button(
                        onClick = {

                            amount
                                .toIntOrNull()
                                ?.takeIf { it > 0 }
                                ?.let { validAmount ->
                                    onSave(validAmount)
                                }

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MamaBearPurple
                        )
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }

}// Only al
