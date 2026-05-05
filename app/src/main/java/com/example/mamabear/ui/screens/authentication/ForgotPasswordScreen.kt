package com.example.mamabear.ui.screens.authentication

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
import androidx.navigation.NavHostController
import com.example.mamabear.ui.navigation.ROUTES

@Composable
fun ForgotPasswordScreen(navController: NavHostController, modifier : Modifier){
    var emailInput by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
    //Lottie Animation
        LottieAnimationWidget(R.raw.forgot_password,250.dp)
        Spacer(modifier = Modifier.height(28.dp))
    //welcome message
        Text(
            text = "Forgot Password?",
            style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold

            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Enter email to reset your password",
            style = TextStyle(
                fontSize = 16.sp,


                )
        )
        Spacer(modifier = Modifier.height(24.dp))
      //email input
        OutlinedTextField(
            value = emailInput,
            onValueChange = { emailInput = it },
            label = { Text(text = "Enter Email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email",
                    tint = purpleColor
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(32.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = primaryColor
            ),
            modifier = Modifier.fillMaxWidth() .padding(24.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        //button
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = purpleColor
            ),
            modifier = Modifier .fillMaxWidth().padding(24.dp)

        ) {
            Text(
                text = "Reset Password" ,
                color = Color.White,
                fontSize = 24.sp ,
                fontWeight = FontWeight.Bold


            )
            Spacer(modifier = Modifier.height(36.dp))
        }
        Text(
            text = "Back to sign in",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold


            )
        )
        Spacer(modifier = Modifier.height(36.dp))
        OutlinedButton(
            onClick = {
                navController.navigate(ROUTES.SignupPage.name)
            }
        ){
            Text(
                text = "Get started(Register.name)"
            )
        }

    }
}

@Composable
fun LottieAnimationWidget() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.forgot_password))
    val progress by animateLottieCompositionAsState(composition)
    LottieAnimation(
        composition = composition,
        progress = { progress },
    )
}