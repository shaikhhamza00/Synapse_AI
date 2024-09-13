package com.hamzadev.synapseai.Screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.hamzadev.synapseai.R
import com.hamzadev.synapseai.Screens.ui.theme.SynapseAITheme
import com.hamzadev.synapseai.ViewModels.BottomBarViewModel

@Composable
fun BottomNavBar(viewModel: BottomBarViewModel = viewModel()) {
    // Observe the selected tab index from the ViewModel
    val selectedTab by viewModel.selectedTab.observeAsState(0)

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = Color.White,
        modifier = Modifier
            .padding(bottom = 30.dp) // Padding from bottom to avoid overlapping with system buttons
            .clip(RoundedCornerShape(16.dp)) // Rounded corners for bottom bar
    ) {
        val items = listOf("Chat", "AI Assistants", "History", "Settings")
        val icons = listOf(
            R.drawable.chat_icon,
            R.drawable.ai_assistant,
            R.drawable.chat_history,
            R.drawable.settings_iocn
        )

        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = icons[index]), // Use painterResource for vector drawables
                        contentDescription = item,
                        tint = if (selectedTab == index) Color(colorResource(id = R.color.teal_green).value) else Color.Gray // Tint selected icon with mint green, others gray
                    )
                },
                selected = selectedTab == index, // Use ViewModel state for selection
                onClick = {
                    viewModel.onTabSelected(index) // Update ViewModel state when tab is clicked
                },
                selectedContentColor = Color.White, // Text color for selected tab (if there is any text)
                unselectedContentColor = Color.Gray  // Text color for unselected tab
            )
        }
    }
}