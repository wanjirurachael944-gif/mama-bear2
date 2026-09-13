//package com.example.mamabear.ui.screens.emergency

package com.example.mamabear.ui.screens.emergency

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalContext
import com.example.mamabear.ui.theme.MamaBearPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencySupportScreen(
    navController: NavController
) {

    val context = LocalContext.current

    fun makeCall(number: String) {

        val intent = Intent(
            Intent.ACTION_DIAL,
            Uri.parse("tel:$number")
        )

        context.startActivity(intent)
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text(
                        text = "Emergency Support",
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
                .padding(20.dp)
        ) {

            Text(
                text = "🚨 Need immediate help?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MamaBearPurple
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "If you or your baby may be in danger, seek emergency medical care immediately.",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(25.dp))

            Button(

                onClick = {
                    makeCall("112")
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),

                shape = RoundedCornerShape(18.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {

                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "CALL EMERGENCY — 112",
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(

                onClick = {
                    makeCall("999")
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape = RoundedCornerShape(18.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.LocalHospital,
                    contentDescription = null,
                    tint = MamaBearPurple
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Call Emergency — 999"
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Seek urgent medical help if you experience serious warning signs such as:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            DangerSign("Heavy bleeding")

            DangerSign("Severe abdominal pain")

            DangerSign("Severe headache or vision problems")

            DangerSign("Difficulty breathing")

            DangerSign("Convulsions")

            DangerSign("Loss of consciousness")

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Mama Bear provides information and support. It does not replace professional medical care.",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun DangerSign(
    text: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = MamaBearPurple,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = text,
            fontSize = 14.sp
        )
    }
}