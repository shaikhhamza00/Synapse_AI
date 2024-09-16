package com.hamzadev.synapseai.Screens

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.hamzadev.synapseai.R
import com.hamzadev.synapseai.Screens.ui.theme.SynapseAITheme
import com.hamzadev.synapseai.ViewModels.BottomBarViewModel
import com.hamzadev.synapseai.ViewModels.ChatViewModel

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel(),
    bottomBarViewModel: BottomBarViewModel = viewModel() // ViewModel for Bottom Bar
) {
    val isChatStarted by viewModel.isChatStarted.observeAsState(false) // Observe the chat start state

    Scaffold(
        topBar = {
            CustomTopBar(viewModel = bottomBarViewModel) // Add the custom top bar with ViewModel
        },
        bottomBar = {
            BottomNavBar(viewModel = bottomBarViewModel) // Pass the ViewModel here
        },
        content = { paddingValues -> // Add paddingValues to respect top/bottom bars
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (isChatStarted) {
                    ChatContentAfterStart(viewModel = viewModel) // Show chat UI when chat has started
                } else {
                    InitialChatScreenContent(viewModel) // Show initial welcome screen
                }
            }
        }
    )
}


@Composable
fun InitialChatScreenContent(viewModel: ChatViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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

        Text(
            text = "Start chatting with Synapse AI now.\nYou can ask me anything",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.startChat() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(colorResource(id = R.color.teal_green).value)
            ),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(text = "Start Chat", color = Color.White, fontSize = 18.sp)
        }
    }
}


@Composable
fun InitialChatUI(viewModel: ChatViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.synapse_logo), // Replace with your image
            contentDescription = "Synapse Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Capabilities",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { /* Handle option click */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text(
                text = "Answer all your questions.\n(Just ask me anything you like!)",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { /* Handle option click */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text(
                text = "Generate all the text you want.\n(essays, articles, reports, stories, & more)",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { /* Handle option click */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text(
                text = "Conversational AI\n(I can talk to you like a natural human)",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "These are just a few examples of what I can do",
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun ChatContentAfterStart(viewModel: ChatViewModel) {
    val chatMessages by viewModel.chatMessages.observeAsState(listOf())
    val chatInput by remember { mutableStateOf(viewModel.chatInput) }
    val showInitialUI = chatMessages.isEmpty() && chatInput.isBlank()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Scrollable chat content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()) // Make chat content scrollable
                .padding(16.dp)
                .align(Alignment.TopStart), // Ensure the chat content aligns at the top
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showInitialUI) {
                InitialChatUI(viewModel)
            }

            // Show chat messages
            if (chatMessages.isNotEmpty()) {
                for ((sender, message) in chatMessages) {
                    Text(
                        text = "$sender: $message",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .align(if (sender == "User") Alignment.End else Alignment.Start),
                        fontSize = 16.sp,
                        color = if (sender == "User") Color.Blue else Color.Green,
                        fontWeight = if (sender == "User") FontWeight.Bold else FontWeight.Normal,
                        textAlign = if (sender == "User") TextAlign.End else TextAlign.Start
                    )
                }
            }
        }

        // Fixed position for the chat input field at the bottom
        ChatInputField(
            viewModel = viewModel,
            isChatInputEmpty = chatInput.isBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter) // Align the input field to the bottom
                .padding(8.dp) // Add some padding around the input field
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInputField(viewModel: ChatViewModel, isChatInputEmpty: Boolean, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            viewModel.onImageSelected(it.toString()) // Convert URI to String and update ViewModel
        }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Load and show the selected image using Coil
        viewModel.selectedImageUri?.let { imageUri ->
            Image(
                painter = rememberAsyncImagePainter(model = imageUri),
                contentDescription = "Selected Image",
                modifier = Modifier.size(56.dp)
            )
        }

        TextField(
            value = viewModel.chatInput,
            onValueChange = { viewModel.onChatInputChanged(it) },
            modifier = Modifier
                .weight(1f)
                .height(56.dp),
            placeholder = { Text(text = "Ask me anything...", color = Color.LightGray) },
            leadingIcon = {
                IconButton(onClick = { imagePickerLauncher.launch("image/*") }) {
                    Icon(painter = painterResource(id = R.drawable.picture_icon), contentDescription = "Select Image", tint = colorResource(
                        id = R.color.teal_green
                    ))
                }
            },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Gray),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = { viewModel.sendMessage() }),
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = {
                if (isChatInputEmpty && viewModel.selectedImageUri == null) {
                    // Handle mic input
                } else {
                    viewModel.sendMessage()
                }
            },
            modifier = Modifier
                .size(56.dp)
                .background(
                    colorResource(id = R.color.teal_green),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Icon(painter = painterResource(id = if (isChatInputEmpty) R.drawable.mic_icon else R.drawable.send_icon),
                contentDescription = if (isChatInputEmpty) "Voice Input" else "Send Message",
                tint = Color.White)
        }
    }
}
