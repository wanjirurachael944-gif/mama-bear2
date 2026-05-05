package com.example.mamabear.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mamabear.ui.screens.Onboarding.OnboardingScreen
import com.example.mamabear.ui.screens.authentication.ForgotPasswordScreen
import com.example.mamabear.ui.screens.authentication.LoginScreen
import com.example.mamabear.ui.screens.authentication.SignupPage
import com.example.mamabear.ui.screens.home.DashboardScreen

@Composable
fun AppNavigation(navController: NavHostController, modifier : Modifier){
    NavHost(
        navController = navController ,
        startDestination = ROUTES.Onboarding.name
    ){
        composable(ROUTES.Onboarding.name){
            OnboardingScreen(navController,modifier)
        }
        composable(ROUTES.Login.name){
            LoginScreen(navController,modifier)
        }

       composable(ROUTES.ForgotPassword.name) {
           ForgotPasswordScreen(navController,modifier)
       }
        composable(ROUTES.SignupPage.name){
            SignupPage (navController,modifier)
        }
        composable(ROUTES .DashboardScreen.name){
            DashboardScreen( navController ,modifier)
        }

        }
    }

