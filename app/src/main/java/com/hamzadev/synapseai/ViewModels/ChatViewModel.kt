package com.hamzadev.synapseai.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatViewModel : ViewModel() {
    // Manage the UI state, chat data, or future functionalities here
    // For now, this will be just a placeholder

    // Example state you might want to manage
    private val _welcomeMessage = MutableLiveData("Welcome to ConversAI 👋")
    val welcomeMessage: LiveData<String> get() = _welcomeMessage

    fun startChat() {
        // Handle start chat logic
    }
}
