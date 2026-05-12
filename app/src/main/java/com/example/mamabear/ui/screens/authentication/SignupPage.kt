package com.example.mamabear.ui.screens.authentication

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.VisualTransformation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import com.example.mamabear.data.models.MotherModel
import com.example.mamabear.data.repository.MotherRepository
import com.example.mamabear.ui.theme.MamaBearPurple
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    navController: NavController
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val repository = remember { MotherRepository() }
    var isLoading by remember { mutableStateOf(false) }
    var signupError by remember { mutableStateOf<String?>(null) }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(com.example.mamabear.R.raw.pregnancy))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F6FB))
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .size(260.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "Create Account 💜",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MamaBearPurple
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Join MamaBear today",
            color = Color.Gray,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            trailingIcon = {
                val image = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                val trimmedFullName = fullName.trim()
                val trimmedEmail = email.trim()
                val trimmedPhone = phone.trim()
                val trimmedPassword = password.trim()
                val trimmedConfirmPassword = confirmPassword.trim()

                if (trimmedEmail.isEmpty() || trimmedPassword.isEmpty() || trimmedFullName.isEmpty()) {
                    signupError = "Please fill in all fields"
                    return@Button
                }

                if (trimmedPassword != trimmedConfirmPassword) {
                    signupError = "Passwords do not match"
                    return@Button
                }

                if (trimmedPassword.length < 6) {
                    signupError = "Password should be at least 6 characters"
                    return@Button
                }

                scope.launch {
                    isLoading = true
                    signupError = null
                    try {
                        val authResult = FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(trimmedEmail, trimmedPassword)
                            .await()

                        val firebaseUser = authResult.user

                        if (firebaseUser != null) {
                            val mother = MotherModel(
                                id = firebaseUser.uid,
                                name = trimmedFullName,
                                email = trimmedEmail,
                                phone = trimmedPhone,
                                weeksPregnant = "4"
                            )
                            repository.insertMother(mother)

                            signupError = "Account created successfully!"
                            navController.navigate("personalDetails") {
                                popUpTo("signup") { inclusive = true }
                            }
                        }
                    } catch (e: com.google.firebase.auth.FirebaseAuthUserCollisionException) {
                        signupError = "This email is already registered."
                    } catch (e: Exception) {
                        signupError = e.localizedMessage ?: "Signup failed. Try again."
                        Log.e("SignupError", "Error: ${e.message}")
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
            colors = ButtonDefaults.buttonColors(containerColor = MamaBearPurple)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(text = "Sign Up", color = Color.White, fontSize = 18.sp)
            }
        }

        if (signupError != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = signupError!!, color = Color.Red, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(
            onClick = { navController.navigate("login") }
        ) {
            Text(text = "Already have an account? Login", color = MamaBearPurple)
        }
    }
}
