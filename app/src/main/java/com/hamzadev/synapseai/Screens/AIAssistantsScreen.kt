package com.hamzadev.synapseai.Screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hamzadev.synapseai.R
import com.hamzadev.synapseai.Screens.ui.theme.SynapseAITheme
import com.hamzadev.synapseai.ViewModels.AIAssistantViewModel
import com.hamzadev.synapseai.model.AIAssistant

@Composable
fun AIAssistantsScreen() {
    val viewModel: AIAssistantViewModel = viewModel()
    val selectedCategory = viewModel.selectedCategory.observeAsState("ALL")
    val assistants = viewModel.assistants.observeAsState(emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            ChipSection(viewModel = viewModel)
        }
        items(assistants.value) { assistant ->
            AIAssistantCard(assistant)
        }
    }
}

@Composable
fun ChipSection(viewModel: AIAssistantViewModel) {
    val categories = listOf(
        "ALL",
        "Writing and Content Creation",
        "Storytelling and Creative Writing",
        "Business and Marketing",
        "Education and Research",
        "Healthcare and Medicine",
        "Language Translation",
        "Creative Arts and Design",
        "Programming Assistants",
        "Personal Assistants",
        "Research and Analysis"
    )

    LazyRow(modifier = Modifier.padding(16.dp)) {
        items(categories) { category ->
            Chip(
                category = category,
                isSelected = viewModel.selectedCategory.value == category,
                onChipSelected = { viewModel.setSelectedCategory(category) }
            )
        }
    }
}

@Composable
fun Chip(
    category: String,
    isSelected: Boolean,
    onChipSelected: (String) -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF5FD068) else Color(MaterialTheme.colorScheme.background.value)
    val textColor = if (isSelected) Color.White else Color(0xFF5FD068)
    val borderColor = Color(0xFF5FD068)

    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, bottomEnd = 20.dp))
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(topStart = 20.dp, bottomEnd = 20.dp)
            )
            .background(backgroundColor)
            .clickable { onChipSelected(category) }
    ) {
        Text(
            text = category,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold, // Bold text
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}


@Composable
fun AIAssistantCard(assistant: AIAssistant) {
    Card(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        border = CardDefaults.outlinedCardBorder(true)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = assistant.logo),
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = assistant.title,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = assistant.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}
