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
import com.example.mamabear.ui.theme.MamaBearPurple
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun ForgotPasswordScreen(
    navController: NavController
) {

    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

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
            color = MamaBearPurple
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Enter your email to reset your password",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                message = ""
            },
            label = {
                Text("Email")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
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
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = {

                val trimmedEmail = email.trim()

                if (trimmedEmail.isEmpty()) {
                    message = "Please enter your email address."
                    return@Button
                }

                scope.launch {

                    isLoading = true
                    message = ""

                    try {

                        FirebaseAuth.getInstance()
                            .sendPasswordResetEmail(trimmedEmail)
                            .await()

                        message =
                            "Password reset link sent! Please check your email."

                    } catch (e: FirebaseAuthInvalidUserException) {

                        message =
                            "No account was found with this email address."

                    } catch (e: Exception) {

                        message =
                            "Error: ${e.localizedMessage ?: "Unable to send reset link."}"

                    } finally {

                        isLoading = false
                    }
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            enabled = !isLoading,

            shape = RoundedCornerShape(18.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = MamaBearPurple
            )
        ) {

            if (isLoading) {

                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )

            } else {

                Text(
                    text = "Reset Password",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = {
                navController.popBackStack()
            },
            enabled = !isLoading
        ) {
            Text(
                text = "Back to Login",
                color = MamaBearPurple
            )
        }
    }
}