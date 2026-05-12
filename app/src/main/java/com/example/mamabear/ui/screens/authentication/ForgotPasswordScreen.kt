package com.example.mamabear.ui.screens.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun ForgotPasswordScreen(
    navController: NavController
) {

    var email by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F6FB))
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Forgot Password",

            fontSize = 30.sp,

            fontWeight = FontWeight.Bold,

            color = Color(0xFF9C6ADE)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Enter your email to reset password",

            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(30.dp))

        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = if (message.contains("Error")) Color.Red else Color.Green,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        OutlinedTextField(
            value = email,

            onValueChange = {
                email = it
            },

            label = {
                Text("Email")
            },

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(

            onClick = {
                if (email.isNotEmpty()) {
                    scope.launch {
                        isLoading = true
                        try {
                            FirebaseAuth.getInstance().sendPasswordResetEmail(email).await()
                            message = "Password reset link sent to your email!"
                        } catch (e: Exception) {
                            message = "Error: ${e.message}"
                        } finally {
                            isLoading = false
                        }
                    }
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            enabled = !isLoading,

            shape = RoundedCornerShape(18.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF9C6ADE)
            )

        ) {

            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(
                    text = "Reset Password",
                    color = Color.White
                )
            }
        }
    }
}
