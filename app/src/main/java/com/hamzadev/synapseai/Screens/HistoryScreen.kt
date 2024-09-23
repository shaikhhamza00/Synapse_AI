package com.hamzadev.synapseai.Screens

import android.os.Bundle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hamzadev.synapseai.R
import com.hamzadev.synapseai.Screens.ui.theme.SynapseAITheme
import com.hamzadev.synapseai.ViewModels.BottomBarViewModel
import com.hamzadev.synapseai.ViewModels.HistoryViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(viewModel: HistoryViewModel = viewModel()) {
    var showHistory by remember { mutableStateOf(true) }
    var selectedConversationId by remember { mutableStateOf("") }
    val chatMessages by viewModel.chatMessages.observeAsState(emptyList())

    if (showHistory) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(chatMessages) { message ->
                HistoryItem(
                    content = message.content,
                    timestamp = formatTimestamp(message.timestamp), // Use formatted timestamp
                    onClick = {
                        selectedConversationId = message.conversationId
                        showHistory = false
                    }
                )
            }
        }
    } else {
        ChatDetailScreen(conversationId = selectedConversationId)
    }
}

@Composable
fun HistoryItem(content: String, timestamp: String, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colors.background
        ),
        border = CardDefaults.outlinedCardBorder(true),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = content,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Image(
                    painter = painterResource(id = R.drawable.next_arrow),
                    contentDescription = "History Detail Icon",
                    modifier = Modifier.size(24.dp)
                )

            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = timestamp,
                style = MaterialTheme.typography.caption.copy(color = Color.Gray)
            )
        }
    }
}


fun formatTimestamp(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()) // Customize the date pattern as needed
    return format.format(date)
}