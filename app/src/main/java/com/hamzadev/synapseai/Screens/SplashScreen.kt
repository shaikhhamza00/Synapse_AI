package com.hamzadev.synapseai.Screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hamzadev.synapseai.R
import com.hamzadev.synapseai.Screens.ui.theme.SynapseAITheme
import com.hamzadev.synapseai.ViewModels.SplashViewModel

@Composable
fun SplashScreen(navController: NavController, SplashViewModel: SplashViewModel){
    LaunchedEffect(Unit) {
        SplashViewModel.startTimeout {
            navController.navigate("Chat_Screen"){
                popUpTo("Splash_Screen"){ inclusive = true}
            }
        }
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Image(painter = painterResource(id = R.drawable.synapse_logo),
            contentDescription = "Synapse AI Logo",
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Fit)

        Text(text = "Synapse AI",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 16.dp))
    }

}