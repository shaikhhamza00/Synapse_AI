package com.hamzadev.synapseai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hamzadev.synapseai.Screens.ChatScreen
import com.hamzadev.synapseai.Screens.SplashScreen
import com.hamzadev.synapseai.ViewModels.SplashViewModel
import com.hamzadev.synapseai.ui.theme.SynapseAITheme

class MainActivity : ComponentActivity() {
    private val SplashViewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SynapseAITheme {
                SynapseAI(SplashViewModel)
            }
        }
    }
}


@Composable
fun SynapseAI(SplashViewModel: SplashViewModel){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Splash_Screen") {
        composable("Splash_Screen") {
            SplashScreen(navController = navController, SplashViewModel = SplashViewModel)
        }
        composable("Chat_Screen") {
            ChatScreen()
        }
    }
}

