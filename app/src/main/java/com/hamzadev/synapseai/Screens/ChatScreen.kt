package com.hamzadev.synapseai.Screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hamzadev.synapseai.R
import com.hamzadev.synapseai.Screens.ui.theme.SynapseAITheme
import com.hamzadev.synapseai.ViewModels.BottomBarViewModel
import com.hamzadev.synapseai.ViewModels.ChatViewModel

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel(),
    bottomBarViewModel: BottomBarViewModel = viewModel() // ViewModel for Bottom Bar
) {
    Scaffold(
        topBar = {
            CustomTopBar(viewModel = bottomBarViewModel) // Add the custom top bar with ViewModel
        },
        bottomBar = {
            BottomNavBar(viewModel = bottomBarViewModel) // Pass the ViewModel here
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Your chat screen content remains the same
                // Logo, Welcome text, etc.
                Image(
                    painter = painterResource(id = R.drawable.synapse_logo), // Replace with your image
                    contentDescription = "Synapse Logo",
                    modifier = Modifier.size(120.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                val welcomeMessage by viewModel.welcomeMessage.observeAsState("Welcome to Synapse AI 👋")
                Text(
                    text = welcomeMessage,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Start chatting with Synapse AI now.\nYou can ask me anything",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { viewModel.startChat() },
                    colors = ButtonDefaults.run {
                        buttonColors(
                            containerColor = Color(colorResource(id = R.color.teal_green).value)) },
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text(text = "Start Chat", color = Color.White, fontSize = 18.sp)
                }
            }
        }
    )
}