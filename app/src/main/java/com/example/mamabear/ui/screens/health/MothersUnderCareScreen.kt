package com.example.mamabear.ui.screens.health
//package com.example.mamabear.ui.screens.health

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mamabear.data.models.MotherModel
import com.example.mamabear.data.repository.MotherRepository
import com.example.mamabear.ui.theme.MamaBearPurple
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MothersUnderCareScreen(
    navController: NavController
) {

    val repository = remember { MotherRepository() }
    val scope = rememberCoroutineScope()

    var mothers by remember {
        mutableStateOf<List<MotherModel>>(emptyList())
    }

    var searchQuery by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                isLoading = true
                mothers = repository.getAllMothers()
                errorMessage = null
            } catch (e: Exception) {
                errorMessage =
                    e.localizedMessage ?: "Failed to load mothers."
            } finally {
                isLoading = false
            }
        }
    }

    val filteredMothers = mothers.filter { mother ->

        mother.name.contains(
            searchQuery,
            ignoreCase = true
        ) ||

                mother.phone.contains(
                    searchQuery,
                    ignoreCase = true
                ) ||

                mother.email.contains(
                    searchQuery,
                    ignoreCase = true
                )
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text(
                        text = "Mothers Under Care",
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
        ) {

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "${mothers.size} mother(s) under care",
                fontSize = 15.sp,
                color = Color.Gray
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            OutlinedTextField(

                value = searchQuery,

                onValueChange = {
                    searchQuery = it
                },

                modifier = Modifier.fillMaxWidth(),

                placeholder = {
                    Text("Search mother")
                },

                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },

                singleLine = true,

                shape = RoundedCornerShape(16.dp)
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            when {

                isLoading -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        CircularProgressIndicator(
                            color = MamaBearPurple
                        )
                    }
                }

                errorMessage != null -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = errorMessage ?: "Something went wrong.",
                            color = Color.Red
                        )
                    }
                }

                filteredMothers.isEmpty() -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MamaBearPurple,
                                modifier = Modifier.size(50.dp)
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            Text(
                                text = if (searchQuery.isEmpty())
                                    "No mothers registered yet."
                                else
                                    "No mother found.",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                else -> {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        items(
                            items = filteredMothers,
                            key = { it.id }
                        ) { mother ->

                            MotherCareCard(
                                mother = mother,
                                onClick = {

                                    navController.navigate(
                                        "mother_health_profile/${mother.id}"
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MotherCareCard(
    mother: MotherModel,
    onClick: () -> Unit
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(

                modifier = Modifier.size(52.dp),

                shape = RoundedCornerShape(50),

                color = MamaBearPurple.copy(alpha = 0.12f)
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MamaBearPurple,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Spacer(
                modifier = Modifier.width(15.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = mother.name.ifEmpty {
                        "Unnamed Mother"
                    },

                    fontSize = 17.sp,

                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = if (mother.weeksPregnant.isNotEmpty())
                        "${mother.weeksPregnant} weeks pregnant"
                    else
                        "Pregnancy information unavailable",

                    fontSize = 14.sp,

                    color = Color.Gray
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = if (mother.dueDate.isNotEmpty())
                        "Due date: ${mother.dueDate}"
                    else
                        "Due date not recorded",

                    fontSize = 13.sp,

                    color = Color.Gray
                )
            }
        }
    }
}