package com.example.mamabear.ui.screens.professional

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import com.airbnb.lottie.compose.*
import com.google.firebase.auth.FirebaseAuth
import com.example.mamabear.R
import com.example.mamabear.ui.theme.MamaBearPurple
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.google.firebase.database.FirebaseDatabase

@Composable
fun HealthProfessionalSignupScreen(
    navController: NavController
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var profession by remember { mutableStateOf("") }
    var facility by remember { mutableStateOf("") }
    var registrationNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }
    var signupMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.pregnancy)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F6FB))
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .size(220.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "Professional Sign Up 💜",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MamaBearPurple
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Create your MamaBear health professional account",
            color = Color.Gray,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Full Name
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

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Professional Email") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Phone
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

        // Profession
        OutlinedTextField(
            value = profession,
            onValueChange = { profession = it },
            label = { Text("Profession") },
            placeholder = {
                Text("e.g. Nurse, Midwife, Doctor")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Facility
        OutlinedTextField(
            value = facility,
            onValueChange = { facility = it },
            label = { Text("Facility / Organization") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Registration Number
        OutlinedTextField(
            value = registrationNumber,
            onValueChange = { registrationNumber = it },
            label = { Text("Professional Registration Number") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            trailingIcon = {
                IconButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    }
                ) {
                    Icon(
                        imageVector =
                            if (passwordVisible)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            visualTransformation =
                if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            trailingIcon = {
                IconButton(
                    onClick = {
                        confirmPasswordVisible = !confirmPasswordVisible
                    }
                ) {
                    Icon(
                        imageVector =
                            if (confirmPasswordVisible)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            visualTransformation =
                if (confirmPasswordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Verification notice
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEDE7F6)
            )
        ) {
            Text(
                text = "Your professional account will require verification before you can access maternal health records.",
                modifier = Modifier.padding(16.dp),
                color = Color.DarkGray,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Sign Up button
        Button(
            onClick = {

                val trimmedFullName = fullName.trim()
                val trimmedEmail = email.trim()
                val trimmedPhone = phone.trim()
                val trimmedProfession = profession.trim()
                val trimmedFacility = facility.trim()
                val trimmedRegistrationNumber =
                    registrationNumber.trim()
                val trimmedPassword = password.trim()
                val trimmedConfirmPassword =
                    confirmPassword.trim()

                if (
                    trimmedFullName.isEmpty() ||
                    trimmedEmail.isEmpty() ||
                    trimmedPhone.isEmpty() ||
                    trimmedProfession.isEmpty() ||
                    trimmedFacility.isEmpty() ||
                    trimmedRegistrationNumber.isEmpty() ||
                    trimmedPassword.isEmpty() ||
                    trimmedConfirmPassword.isEmpty()
                ) {
                    signupMessage = "Please fill in all fields."
                    return@Button
                }

                if (trimmedPassword != trimmedConfirmPassword) {
                    signupMessage = "Passwords do not match."
                    return@Button
                }

                if (trimmedPassword.length < 6) {
                    signupMessage =
                        "Password should be at least 6 characters."
                    return@Button
                }

                scope.launch {

                    isLoading = true
                    signupMessage = null

                    try {

                        val authResult =
                            FirebaseAuth.getInstance()
                                .createUserWithEmailAndPassword(
                                    trimmedEmail,
                                    trimmedPassword
                                )
                                .await()

                        val firebaseUser = authResult.user

                        if (firebaseUser != null) {

                            val professionalData = mapOf(
                                "id" to firebaseUser.uid,
                                "fullName" to trimmedFullName,
                                "email" to trimmedEmail,
                                "phone" to trimmedPhone,
                                "profession" to trimmedProfession,
                                "facility" to trimmedFacility,
                                "registrationNumber" to trimmedRegistrationNumber,
                                "role" to "health_professional",
                                "verified" to false
                            )

                            FirebaseDatabase.getInstance()
                                .getReference("health_professionals")
                                .child(firebaseUser.uid)
                                .setValue(professionalData)
                                .await()

                            signupMessage =
                                "Account created successfully!"

                            navController.navigate(
                                "health_professional_login"
                            ) {
                                popUpTo(
                                    "health_professional_signup"
                                ) {
                                    inclusive = true
                                }
                            }
                        }

                    } catch (
                        e: com.google.firebase.auth.FirebaseAuthUserCollisionException
                    ) {

                        signupMessage =
                            "This email is already registered."

                    } catch (e: Exception) {

                        signupMessage =
                            e.localizedMessage
                                ?: "Signup failed. Try again."

                        Log.e(
                            "ProfessionalSignupError",
                            "Error: ${e.message}"
                        )

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
                    text = "Create Professional Account",
                    color = Color.White,
                    fontSize = 17.sp
                )
            }
        }

        if (signupMessage != null) {

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = signupMessage!!,
                color = Color.Red,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(
            onClick = {
                navController.navigate(
                    "health_professional_login"
                )
            }
        ) {
            Text(
                text = "Already have a professional account? Login",
                color = MamaBearPurple
            )
        }
    }
}