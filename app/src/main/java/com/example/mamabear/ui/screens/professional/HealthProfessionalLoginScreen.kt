package com.example.mamabear.ui.screens.professional

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import com.example.mamabear.ui.theme.MamaBearPurple
import com.example.mamabear.ui.screens.health.HealthProfessionalScreen
import android.util.Log
import com.example.mamabear.ui.navigation.ROUTES
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthProfessionalLoginScreen(
    navController: NavController
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<String?>(null) }

    var showForgotPasswordDialog by remember { mutableStateOf(false) }
    var resetEmail by remember { mutableStateOf("") }
    var resetMessage by remember { mutableStateOf<String?>(null) }
    var isResettingPassword by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Health Professional",
                        color = MamaBearPurple,
                        fontWeight = FontWeight.Bold
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
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "Mama Bear 💜",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MamaBearPurple
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Health Professional Login",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sign in to manage maternal health records.",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(35.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Professional Email")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Password")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = {

                    val trimmedEmail = email.trim()
                    val trimmedPassword = password.trim()

                    if (trimmedEmail.isEmpty() || trimmedPassword.isEmpty()) {
                        loginError = "Please enter your email and password."
                        return@Button
                    }

                    scope.launch {

                        isLoading = true
                        loginError = null

                        try {

                            val authResult = FirebaseAuth.getInstance()
                                .signInWithEmailAndPassword(
                                    trimmedEmail,
                                    trimmedPassword
                                )
                                .await()

                            val firebaseUser = authResult.user

                            if (firebaseUser == null) {
                                loginError = "Login failed. Please try again."
                                return@launch
                            }

                            val professionalRef =
                                FirebaseDatabase.getInstance()
                                    .getReference("health_professionals")
                                    .child(firebaseUser.uid)

                            val snapshot = professionalRef.get().await()

                            if (!snapshot.exists()) {

                                loginError =
                                    "This account is not registered as a health professional."

                                FirebaseAuth.getInstance().signOut()

                                return@launch
                            }

                            val verified =
                                snapshot.child("verified")
                                    .getValue(Boolean::class.java) ?: false

                            if (!verified) {

                                loginError =
                                    "Your professional account is awaiting verification."

                                FirebaseAuth.getInstance().signOut()

                                return@launch
                            }

                            navController.navigate("health_professional") {
                                popUpTo("health_professional_login") {
                                    inclusive = true
                                }
                            }

                        } catch (
                            e: com.google.firebase.auth.FirebaseAuthInvalidUserException
                        ) {

                            loginError = "No account found with this email."

                        } catch (
                            e: com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
                        ) {

                            loginError = "Incorrect email or password."

                        } catch (e : Exception) {
                            loginError = e.localizedMessage ?: "Login failed. Please try again."
                            Log.e(
                                 "ProfessionalLogin" ,
                                "Login error : $ {e.message} "
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
                        text = "Login",
                        fontSize = 17.sp
                    )
                }
            }
            if (loginError != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = loginError!!,
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(18.dp))

            TextButton(
                onClick = {
                    resetEmail = email
                    resetMessage = null
                    showForgotPasswordDialog = true
                }
            ) {
                Text(
                    text = "Forgot Password?",
                    color = MamaBearPurple,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = {
                    navController.navigate("health_professional_signup")
                }
            ) {
                Text(
                    text = "Don't have a professional account? Sign Up",
                    color = MamaBearPurple
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MamaBearPurple.copy(alpha = 0.08f)
                )
            ) {
                Text(
                    text = "Access is intended for authorized healthcare professionals only.",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
                if (showForgotPasswordDialog) {

                    AlertDialog(
                        onDismissRequest = {
                            if (!isResettingPassword) {
                                showForgotPasswordDialog = false
                            }
                        },

                        title = {
                            Text(
                                text = "Reset Password",
                                color = MamaBearPurple,
                                fontWeight = FontWeight.Bold
                            )
                        },

                        text = {

                            Column {

                                Text(
                                    text = "Enter your professional email and we'll send you a password reset link.",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )

                                Spacer(
                                    modifier = Modifier.height(16.dp)
                                )

                                OutlinedTextField(
                                    value = resetEmail,
                                    onValueChange = {
                                        resetEmail = it
                                        resetMessage = null
                                    },
                                    label = {
                                        Text("Professional Email")
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    shape = RoundedCornerShape(16.dp)
                                )

                                if (resetMessage != null) {

                                    Spacer(
                                        modifier = Modifier.height(10.dp)
                                    )

                                    Text(
                                        text = resetMessage!!,
                                        color = MamaBearPurple,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        },

                        confirmButton = {

                            TextButton(
                                enabled = !isResettingPassword,
                                onClick = {

                                    val trimmedEmail = resetEmail.trim()

                                    if (trimmedEmail.isEmpty()) {

                                        resetMessage =
                                            "Please enter your email address."

                                        return@TextButton
                                    }

                                    scope.launch {

                                        isResettingPassword = true
                                        resetMessage = null

                                        try {

                                            FirebaseAuth.getInstance()
                                                .sendPasswordResetEmail(
                                                    trimmedEmail
                                                )
                                                .await()

                                            resetMessage =
                                                "If an account exists for this email, a password reset link has been sent. Please check your inbox."

                                        } catch (e: Exception) {

                                            resetMessage =
                                                "We couldn't send the reset email. Please check the email address and try again."

                                            Log.e(
                                                "PasswordReset",
                                                "Error: ${e.message}"
                                            )

                                        } finally {

                                            isResettingPassword = false
                                        }
                                    }
                                }
                            ) {

                                if (isResettingPassword) {

                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        color = MamaBearPurple
                                    )

                                } else {

                                    Text(
                                        text = "Send Reset Link",
                                        color = MamaBearPurple
                                    )
                                }
                            }
                        },

                        dismissButton = {

                            TextButton(
                                enabled = !isResettingPassword,
                                onClick = {
                                    showForgotPasswordDialog = false
                                    resetMessage = null
                                }
                            ) {
                                Text("Cancel")
                            }
                        }
                    )
                }
            }
        }
    }
}