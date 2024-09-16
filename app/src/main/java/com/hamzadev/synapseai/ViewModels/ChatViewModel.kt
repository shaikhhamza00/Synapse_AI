package com.hamzadev.synapseai.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.hamzadev.synapseai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _welcomeMessage = MutableLiveData("Welcome to Synapse AI 👋")
    val welcomeMessage: LiveData<String> get() = _welcomeMessage

    private val _isChatStarted = MutableLiveData(false)
    val isChatStarted: LiveData<Boolean> get() = _isChatStarted

    private val _chatMessages = MutableLiveData<List<Pair<String, String>>>()
    val chatMessages: LiveData<List<Pair<String, String>>> get() = _chatMessages

    var chatInput: String by mutableStateOf("")

    // New variable to store the selected image
    var selectedImageUri: String? by mutableStateOf(null)

    fun startChat() {
        _isChatStarted.value = true
        _chatMessages.value = listOf()
    }

    fun onChatInputChanged(newInput: String) {
        chatInput = newInput
    }

    // Handle image selection
    fun onImageSelected(imageUri: String) {
        selectedImageUri = imageUri
    }

    fun sendMessage() {
        val userMessage = chatInput
        val imageUri = selectedImageUri

        if (userMessage.isNotBlank() || imageUri != null) {
            // Clear the input and image selection after sending the message
            chatInput = ""
            selectedImageUri = null

            // Update chat with text and/or image
            val currentMessages = _chatMessages.value ?: listOf()
            val newMessage = when {
                userMessage.isNotBlank() && imageUri != null -> "User sent: $userMessage with an image: $imageUri"
                userMessage.isNotBlank() -> "User: $userMessage"
                else -> "User sent an image: $imageUri"
            }
            _chatMessages.value = currentMessages + ("User" to newMessage)

            viewModelScope.launch {
                val responseText = generateResponseFromGemini(userMessage)
                _chatMessages.value = _chatMessages.value!! + ("AI" to responseText)
            }
        }
    }

    // Function to communicate with Gemini API
    private suspend fun generateResponseFromGemini(userMessage: String): String {
        return try {
            val generativeModel = GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = BuildConfig.API_KEY
            )
            val response = generativeModel.generateContent(userMessage)
            response.text ?: "Sorry, I didn't get that."
        } catch (e: Exception) {
            "Error occurred: ${e.message}"
        }
    }
}


// Mock function to simulate a response from an AI system
private suspend fun generateResponseFromGemini(userMessage: String): String {
    return "AI Response to: $userMessage"
}
