package com.example.mamabear.ui.screens.authentication

import android.R.attr.contentDescription
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mamabear.R
import com.example.mamabear.ui.navigation.ROUTES
import com.example.mamabear.ui.theme.primaryColor
import com.example.mamabear.ui.theme.purpleColor
import com.example.mamabear.ui.theme.secondaryColor

@Composable
fun SignupPage (navController:NavHostController ,modifier: Modifier) {
    var fullNameInput by remember { mutableStateOf(TextFieldValue("")) }
    var emailInput by remember { mutableStateOf(TextFieldValue("")) }
    var passwordInput by remember { mutableStateOf(TextFieldValue("")) }
    var confirmInput by remember { mutableStateOf(TextFieldValue("")) }
    var isVisible by remember { mutableStateOf(value = false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        //Lottie Animation
        LottieAnimationWidget(R.raw.user_icon, 100.dp)
        Spacer(modifier = Modifier.height(8.dp))
        //welcome message
        Text(
            text = "Create Account",
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = "Signup to get started!",
            style = TextStyle(
                fontSize = 20.sp,
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        //name Input
        OutlinedTextField(
            value = fullNameInput,
            onValueChange = { fullNameInput = it },
            label = { Text(text = "Enter full Name") },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_person_24),
                    contentDescription = "fullName",
                    tint = purpleColor
                )

            },
            shape = RoundedCornerShape(32.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = primaryColor
            ),
            modifier = Modifier.fillMaxWidth().padding(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        //  emailInput
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
            modifier = Modifier.fillMaxWidth().padding(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        //password input
        OutlinedTextField(
            value = passwordInput,
            onValueChange = { passwordInput = it },
            label = { Text(text = "Enter Password") },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_password_24),
                    contentDescription = "Password",
                    tint = purpleColor
                )
            },
            trailingIcon = {
                IconButton(onClick = { isVisible = !isVisible }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_visibility_24),
                        contentDescription = "Password",
                        tint = purpleColor
                    )
                }
            },
            visualTransformation = if (!isVisible) {
                PasswordVisualTransformation()
            } else VisualTransformation.None,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(32.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = primaryColor
            ),
            modifier = Modifier.fillMaxWidth().padding(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
//Confirm password
        OutlinedTextField(
            value = passwordInput,
            onValueChange = { passwordInput = it },
            label = { Text(text = "Confirm Password") },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_password_24),
                    contentDescription = "Password",
                    tint = purpleColor
                )
            },
            trailingIcon = {
                IconButton(onClick = { isVisible = !isVisible }) {

                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_visibility_24),
                        contentDescription = "Password",
                        tint = purpleColor
                    )
                }
            },
            visualTransformation = if (!isVisible) {
                PasswordVisualTransformation()
            } else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(32.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = primaryColor
            ),
            modifier = Modifier.fillMaxWidth().padding(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        //Signup button
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = purpleColor
            ),
            modifier = Modifier.fillMaxWidth().padding(24.dp)

        ) {
            Text(
                text = "SIGN UP",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            //navigation button
            OutlinedButton(
                onClick = {
                    navController.navigate(ROUTES.Login.name)
                }
            ) {
                Text(
                    text = "( Login )"
                )
            }

        }
    }

}

