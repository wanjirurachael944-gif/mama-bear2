package com.example.mamabear.ui.screens.authentication

import android.R.attr.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mamabear.R
import com.example.mamabear.ui.navigation.ROUTES
import com.example.mamabear.ui.theme.primaryColor
import com.example.mamabear.ui.theme.purpleColor
import com.example.mamabear.ui.theme.secondaryColor

@Composable
fun LoginScreen(navController:NavHostController ,modifier: Modifier){
    var emailInput by remember {mutableStateOf(TextFieldValue ("")) }
    var passwordInput by remember { mutableStateOf(TextFieldValue("")) }
    var isVisible by remember { mutableStateOf(value = false) }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        //lottie animation
        LottieAnimationWidget(R.raw.login_character_animation, 280.dp)

        //welcome message
        Text(
            text = "Welcome Back!",
            style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold

            ))
        Text(
            text = "Login to  Mama Bear!",
            style = TextStyle(
                fontSize = 24.sp,


                ))
        Spacer(modifier = Modifier.height(16.dp))
        //email input
        OutlinedTextField(
            value = emailInput,
            onValueChange={ emailInput = it },
            label = { Text(text="Enter Email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email",
                    tint = secondaryColor
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = RoundedCornerShape(32.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = primaryColor

            ),
            modifier = Modifier.fillMaxWidth().padding(24.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        //password input
        OutlinedTextField(
            value = passwordInput,
            onValueChange={ passwordInput = it },
            label = { Text(text="Enter Password") },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_password_24),
                    contentDescription = "Password",
                    tint = purpleColor
                )
            },
            trailingIcon = {
                IconButton(onClick={
                    isVisible =! isVisible
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_visibility_24),
                        contentDescription = "Password",
                        tint = purpleColor
                    )
                }
            },
            visualTransformation = if (!isVisible){PasswordVisualTransformation()}else VisualTransformation.None,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(32.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = primaryColor
            ),
            modifier = Modifier.fillMaxWidth().padding(24.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        //Login button
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = purpleColor
            ),
            modifier = Modifier .fillMaxWidth().padding(24.dp)

        ) {
            Text(
                text = "Login" ,
                color = Color.White,
                fontSize = 24.sp ,
                fontWeight = FontWeight.Bold


            )
            Spacer(modifier = Modifier.height(36.dp))
        }

        //button
        OutlinedButton(
            onClick = {
                navController.navigate(ROUTES.ForgotPassword.name)
            }
        ) {
            Text(
                text ="(forgotPassword page)"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = {
                navController.navigate(ROUTES.DashboardScreen.name)
            }
        ) {
            Text(
                text ="(Dashboard page)"
            )
        }


    }
}
@Composable
fun LottieAnimationWidget(drawable: Int, size:Dp) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(drawable))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(size)
    )
}
