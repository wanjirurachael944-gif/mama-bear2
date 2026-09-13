package com.example.mamabear.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

// 🔥 IMPORT SCREENS
import com.example.mamabear.ui.screens.onboarding.OnboardingScreen
import com.example.mamabear.ui.screens.authentication.WelcomeScreen
import com.example.mamabear.ui.screens.authentication.SignupScreen
import com.example.mamabear.ui.screens.authentication.ForgotPasswordScreen
import com.example.mamabear.ui.screens.health.HealthRecordsScreen
import com.example.mamabear.ui.screens.authentication.LoginScreen
import com.example.mamabear.ui.screens.professional.HealthProfessionalLoginScreen
import com.example.mamabear.ui.screens.professional.HealthProfessionalSignupScreen
import com.example.mamabear.ui.screens.home.HomeScreen
import com.example.mamabear.ui.screens.profile.ProfileScreen
import com.example.mamabear.ui.screens.reminders.ReminderScreen
import com.example.mamabear.ui.screens.savings.SavingsScreen
import com.example.mamabear.ui.screens.profile.AboutScreen
import com.example.mamabear.ui.screens.profile.SettingsScreen
import com.example.mamabear.ui.screens.profile.SupportScreen
import com.example.mamabear.ui.screens.profile.PrivacyPolicyScreen

import com.example.mamabear.ui.screens.splash.SplashScreen
import com.example.mamabear.ui.screens.authentication.PersonalDetailsScreen
import com.example.mamabear.ui.screens.health.MotherHealthProfileScreen
import com.example.mamabear.ui.screens.health.RecordAncVisitScreen
import com.example.mamabear.ui.screens.health.HealthProfessionalScreen
import com.example.mamabear.ui.screens.health.MothersUnderCareScreen
import com .example .mamabear.ui.screens.learning.LearningHubScreen
import com.example .mamabear .ui.screens.emergency.EmergencySupportScreen

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = "splash",
        modifier = modifier
    ) {
        // 🔹 SPLASH SCREEN
        composable(route = "splash") {
            SplashScreen(navController = navHostController)
        }
        // 🔹 ONBOARDING SCREEN
        composable(route = "onboarding") {
            OnboardingScreen(navController = navHostController)
        }

        // 🔹 WELCOME SCREEN
        composable(route = "welcome") {
            WelcomeScreen(navController = navHostController)
        }

        // 🔹 SIGNUP SCREEN
        composable(route = "signup") {

            SignupScreen(
                navController = navHostController
            )
        }

        // 🔹 LOGIN SCREEN
        composable(route = "login") {

            LoginScreen(
                navController = navHostController
            )
        }
        // 🔹 HEALTH PROFESSIONAL SIGNUP SCREEN
        composable(route = "health_professional_signup") {
            HealthProfessionalSignupScreen(
                navController = navHostController
            )
        }

        // 🔹 HEALTH PROFESSIONAL LOGIN SCREEN
        composable(route = "health_professional_login") {
            HealthProfessionalLoginScreen(
                navController = navHostController
            )
        }
        //  HEALTH PROFESSIONAL DASHBOARD
        composable (route = "health_professional "){
            HealthProfessionalScreen (
                navController = navHostController
            )
        }
        //  MOTHERS UNDER CARE
        composable(route = "mothers_under_care"){
            MothersUnderCareScreen(
                navController =navHostController
            )
        }
        // LEARNING HUB
        composable (route = "learning_hub"){
            LearningHubScreen(
                navController = navHostController
            )
        }
        // MOTHER HEALTH PROFILE
        composable(
            route = "mother_health_profile/{motherId}"
        ) { backStackEntry ->

            val motherId =
                backStackEntry.arguments?.getString("motherId") ?: ""

            MotherHealthProfileScreen(
                navController = navHostController,
                motherId = motherId
            )
        }
        // Record ANC Visit Screen
       composable (
           route = "record_anc_visit/{motherId}"
       ){backStackEntry ->
           val motherId = backStackEntry.arguments?.getString("motherId") ?: ""
           RecordAncVisitScreen(
                navController =  navHostController ,
               motherId = motherId
           )
       }


        // 🔹 FORGOT PASSWORD SCREEN
        composable(route = "forgotPassword") {

            ForgotPasswordScreen(
                navController = navHostController
            )
        }

        // 🔹 HOME SCREEN
        composable(route = "home") {

            HomeScreen(
                navController = navHostController
            )
        }

        // 🔹 HEALTH RECORDS SCREEN
        composable(route = "healthrecords") {

            HealthRecordsScreen(
                navController = navHostController
            )
        }

        // 🔹 SAVINGS SCREEN
        composable(route = "savings") {

            SavingsScreen(
                navController = navHostController
            )
        }

        // 🔹 REMINDER SCREEN
        composable(route = "reminders") {

            ReminderScreen(
                navController = navHostController
            )
        }
        //Emergency
        composable (route = "emergency"){
            EmergencySupportScreen (
                navController = navHostController
            )
        }

        // 🔹 PROFILE SCREEN
        composable(route = "profile") {
            ProfileScreen(
                navController = navHostController
            )
        }

        // 🔹 ABOUT SCREEN
        composable(route = "about") {
            AboutScreen(
                navController = navHostController
            )
        }

        // 🔹 SETTINGS SCREEN
        composable(route = "settings") {
            SettingsScreen(
                navController = navHostController
            )
        }

        // 🔹 SUPPORT SCREEN
        composable(route = "support") {
            SupportScreen(
                navController = navHostController
            )
        }

        // 🔹 PRIVACY POLICY SCREEN
        composable(route = "privacy") {
            PrivacyPolicyScreen(
                navController = navHostController
            )
        }

        // 🔹 PERSONAL DETAILS SCREEN
        composable(route = "personalDetails") {
            PersonalDetailsScreen(
                navController = navHostController
            )
        }
    }
}
