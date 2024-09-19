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
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
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
    bottomBarViewModel: BottomBarViewModel = viewModel()
) {
    // Observe the selected tab index
    val selectedTab by bottomBarViewModel.selectedTab.observeAsState(0)

    Scaffold(
        topBar = {
            CustomTopBar(viewModel = bottomBarViewModel)
        },
        bottomBar = {
            BottomNavBar(viewModel = bottomBarViewModel)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (selectedTab) {
                    0 -> {
                        if (viewModel.isChatStarted) {
                            ChatContentAfterStart(viewModel)
                        } else {
                            InitialChatScreenContent(viewModel)
                        }
                    }
                    1 -> {
                        // Replace with your AI Assistants screen content
//                        AIAssistantsScreen()
                    }
                    2 -> {
                        // Navigate to HistoryScreen
                        HistoryScreen(viewModel = bottomBarViewModel)
                    }
                    3 -> {
                        // Replace with your Settings screen content
//                        SettingsScreen()
                    }
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
            .padding(horizontal = 16.dp),
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
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text(
                text = "Answer all your questions.\n(Just ask me anything you like!)",
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { /* Handle option click */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text(
                text = "Generate all the text you want.\n(essays, articles, reports, stories, & more)",
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { /* Handle option click */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text(
                text = "Conversational AI\n(I can talk to you like a natural human)",
                color = Color.DarkGray,
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
    val chatMessages by viewModel.chatMessages.observeAsState(emptyList())
    val chatInput by remember { mutableStateOf(viewModel.chatInput) }
    val showInitialUI = chatMessages.isEmpty() && chatInput.isBlank()

    // Create LazyListState for managing scroll position
    val listState = rememberLazyListState()

    // Use LazyColumn to manage scrolling more efficiently
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentPadding = PaddingValues(bottom = 80.dp) // Add padding to prevent overlap with input field
        ) {
            if (showInitialUI) {
                item {
                    InitialChatUI(viewModel)
                }
            }

            items(chatMessages) { (sender, message) ->
                val isUser = sender == "User"
                val backgroundColor = if (isUser) Color(0xFFDCF8C6) else Color(0xFFEAEAEA)
                val textColor = if (isUser) Color.Black else Color.DarkGray

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = backgroundColor,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp)
                            .widthIn(max = 250.dp)
                    ) {
                        Text(
                            text = message,
                            fontSize = 16.sp,
                            color = textColor,
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        }

        // Automatically scroll to the start of the latest message when chatMessages change
        LaunchedEffect(chatMessages) {
            val itemCount = chatMessages.size
            if (itemCount > 0) {
                // Scroll to the end of the latest message
                listState.animateScrollToItem(itemCount - 1)
            }
        }

        ChatInputField(
            viewModel = viewModel,
            isChatInputEmpty = chatInput.isBlank(),
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.BottomCenter) // Align ChatInputField to the bottom
        )
    }
}













@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInputField(viewModel: ChatViewModel, isChatInputEmpty: Boolean, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = viewModel.chatInput,
            onValueChange = { viewModel.onChatInputChanged(it) },
            modifier = Modifier
                .weight(1f)
                .height(56.dp),
            placeholder = { Text(text = "Ask me anything...", color = Color.Gray) },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.LightGray),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = { viewModel.sendMessage() }),
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = {
                viewModel.sendMessage()
            },
            modifier = Modifier
                .size(56.dp)
                .background(
                    colorResource(id = R.color.teal_green),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Icon(
                painter = painterResource(
                    R.drawable.send_icon
                ),
                contentDescription = "Send Butotn",
                tint = Color.White
            )
        }
    }
}
