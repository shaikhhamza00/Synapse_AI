package com.hamzadev.synapseai.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BottomBarViewModel : ViewModel() {
    // Store the selected tab index
    private val _selectedTab = MutableLiveData(0)
    val selectedTab: LiveData<Int> get() = _selectedTab

    // Store the title of the top bar
    private val _topBarTitle = MutableLiveData("ConversAI")
    val topBarTitle: LiveData<String> get() = _topBarTitle

    // Function to update the selected tab and top bar title
    fun onTabSelected(index: Int) {
        _selectedTab.value = index
        _topBarTitle.value = when (index) {
            0 -> "ConversAI"
            1 -> "AI Assistants"
            2 -> "History"
            3 -> "Settings"
            else -> "ConversAI"
        }
    }
}
