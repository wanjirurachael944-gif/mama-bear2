package com.example.mamabear.ui.screens.Learning

//package com.example.mamabear.ui.screens.learning

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mamabear.ui.theme.MamaBearPurple

data class LearningTopic(
    val title: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningHubScreen(
    navController: NavController
) {

    val topics = listOf(

        LearningTopic(
            title = "Pregnancy by Trimester",
            description = "Learn about changes and care during each stage of pregnancy.",
            icon = Icons.Default.Favorite
        ),

        LearningTopic(
            title = "Antenatal Care",
            description = "Understand the importance of ANC visits and regular check-ups.",
            icon = Icons.Default.LocalHospital
        ),

        LearningTopic(
            title = "Nutrition During Pregnancy",
            description = "Learn about healthy eating and nutrition during pregnancy.",
            icon = Icons.Default.Restaurant
        ),

        LearningTopic(
            title = "Preparing for Birth",
            description = "Learn how to prepare for delivery and the arrival of your baby.",
            icon = Icons.Default.ChildCare
        ),

        LearningTopic(
            title = "Newborn Care",
            description = "Basic information about caring for your newborn.",
            icon = Icons.Default.BabyChangingStation
        ),

        LearningTopic(
            title = "Pregnancy Warning Signs",
            description = "Know when you should seek urgent medical attention.",
            icon = Icons.Default.Warning
        ),

        LearningTopic(
            title = "Mental Wellbeing",
            description = "Learn about emotional wellbeing and seeking support during pregnancy.",
            icon = Icons.Default.SelfImprovement
        ),

        LearningTopic(
            title = "Savings for Mother and Baby",
            description = "Simple ways to prepare financially for pregnancy, birth and baby needs.",
            icon = Icons.Default.Savings
        )
    )

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text(
                        text = "Learning Hub",
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

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F6FB))
                .padding(padding)
                .padding(horizontal = 20.dp),

            verticalArrangement = Arrangement.spacedBy(14.dp),

            contentPadding = PaddingValues(
                top = 20.dp,
                bottom = 30.dp
            )
        ) {

            item {

                Text(
                    text = "Learn. Prepare. Feel supported. 💜",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MamaBearPurple
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Helpful pregnancy, newborn and financial education in one place.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            items(topics) { topic ->

                LearningTopicCard(
                    topic = topic,
                    onClick = {

                        // Topic details will be connected next.
                    }
                )
            }

            item {

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Health information should not replace advice from your healthcare professional.",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Health information will be reviewed against trusted sources such as WHO guidance.",
                    fontSize = 12.sp,
                    color = MamaBearPurple
                )
            }
        }
    }
}

@Composable
private fun LearningTopicCard(
    topic: LearningTopic,
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

                shape = RoundedCornerShape(16.dp),

                color = MamaBearPurple.copy(
                    alpha = 0.12f
                )
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = topic.icon,
                        contentDescription = null,
                        tint = MamaBearPurple,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(15.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = topic.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = topic.description,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Open",
                tint = MamaBearPurple
            )
        }
    }
}