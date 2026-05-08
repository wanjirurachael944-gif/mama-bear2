package com.example.mamabear.ui.screens.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mamabear.R
import com.example.mamabear.R.raw
import com.example.mamabear.ui.theme.primaryColor
import com.example.mamabear.ui.theme.purpleColor
import com.example.mamabear.ui.theme.secondaryColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.OutlinedButton
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mamabear.ui.navigation.ROUTES
import com.example.mamabear.ui.theme.PurplePrimary

@Composable
fun ForgotPasswordScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }

    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F6FB))
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(60.dp))

        // 🔹 TITLE
        Text(
            text = "Forgot Password 🔐",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = PurplePrimary
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 🔹 SUBTITLE
        Text(
            text = "Enter your email to reset your password",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(40.dp))

        // 🔹 EMAIL INPUT
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },

            label = {
                Text("Email")
            },

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(14.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // 🔥 RESET BUTTON
        Button(
            onClick = {

                if (email.isNotEmpty()) {

                    message = "Password reset link sent to your email"

                } else {

                    message = "Please enter your email"
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            shape = RoundedCornerShape(14.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = PurplePrimary
            )

        ) {

            Text(
                text = "Reset Password",
                color = Color.White,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 MESSAGE
        if (message.isNotEmpty()) {

            Text(
                text = message,
                color = PurplePrimary,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // 🔹 BACK TO LOGIN
        Text(
            text = "Back to Login",
            color = PurplePrimary,
            fontWeight = FontWeight.Bold,

            modifier = Modifier.clickable {

                navController.navigate("login")
            }
        )
    }
}