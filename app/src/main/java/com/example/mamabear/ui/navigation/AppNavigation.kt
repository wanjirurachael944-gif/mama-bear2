package com.example.mamabear.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mamabear.screens.OnboardingScreen
import com.example.mamabear.ui.screens.authentication.ForgotPasswordScreen
import com.example.mamabear.ui.screens.authentication.SignupScreen
import com.example.mamabear.ui.screens.login.LoginScreen

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    modifier : Modifier = Modifier
) {

    //val navController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = "onboarding" ,
        modifier = modifier
    ) {

        // 🔹 ONBOARDING
        composable("onboarding") {
            OnboardingScreen(navController = navHostController)
        }

        // 🔹 LOGIN
        composable("login") {
            LoginScreen(navHostController)
        }

        // 🔹 SIGNUP
        composable("signup") {
            SignupScreen(navHostController)
        }

        // 🔹 FORGOT PASSWORD
        composable("forgotPassword") {
            ForgotPasswordScreen(navHostController)
        }


    }
}