package com.example.mamabear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.mamabear.ui.navigation.AppNavigation
import com.example.mamabear.ui.screens.authentication.LoginScreen
import com.example.mamabear.ui.screens.authentication.SignupPage
import com.example.mamabear.ui.theme.MamaBearTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MamaBearTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        //LoginScreen()
                        //SignupPage()
                        //ForgotPasswordScreen()
                    //OnboardingScreen()
                    //DashboardScreen()
                    AppNavigation(navController ,modifier = Modifier.padding(innerPadding))


                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MamaBearTheme {

    }
}