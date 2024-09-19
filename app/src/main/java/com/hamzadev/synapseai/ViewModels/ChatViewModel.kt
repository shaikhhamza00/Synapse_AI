package com.hamzadev.synapseai.ViewModels

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import com.hamzadev.synapseai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.Locale

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    private val _welcomeMessage = MutableLiveData("Welcome to Synapse AI 👋")
    val welcomeMessage: LiveData<String> get() = _welcomeMessage

    var isChatStarted by mutableStateOf(false)

    private val _chatMessages = MutableLiveData<List<ChatMessage>>()
    val chatMessages: LiveData<List<ChatMessage>> get() = _chatMessages

    var chatInput: String by mutableStateOf("")

    init {
        _chatMessages.value = listOf()
    }

    fun startChat() {
        isChatStarted = true
        _chatMessages.value = listOf()
    }

    fun onChatInputChanged(newInput: String) {
        chatInput = newInput
    }

    fun sendMessage() {
        val userMessage = chatInput
        if (userMessage.isNotBlank()) {
            chatInput = ""
            val currentMessages = _chatMessages.value ?: listOf()
            val chatMessage = ChatMessage(sender = "User", content = userMessage, isVoiceNote = false)
            _chatMessages.value = currentMessages + chatMessage

            viewModelScope.launch {
                val responseText = generateResponseFromGemini(userMessage)
                val aiMessage = ChatMessage(sender = "AI", content = responseText.toString(), isVoiceNote = false)
                _chatMessages.value = _chatMessages.value!! + aiMessage
            }
        }
    }

    private suspend fun generateResponseFromGemini(userMessage: String): AnnotatedString  {
        return try {
            val generativeModel = GenerativeModel(modelName = "gemini-1.5-flash", apiKey = BuildConfig.API_KEY)
            val response = generativeModel.generateContent(userMessage)
            Log.d("ChatViewModel", "API Response: ${response.text}")

            // Process the response to apply the formatting for headings and bold text
            formatResponseText(response.text ?: "Sorry, I didn't get that.")
        } catch (e: Exception) {
            Log.e("ChatViewModel", "API Error: ${e.message}")
            buildAnnotatedString { append("Error occurred: ${e.message}") }
        }
    }


    private fun formatResponseText(text: String): AnnotatedString {
        return buildAnnotatedString {
            var start = 0

            // Split text into lines
            text.lines().forEachIndexed { index, line ->
                var currentLine = line

                // Handle headings (## heading)
                if (currentLine.startsWith("##")) {
                    val headingText = currentLine.removePrefix("##").trim()
                    if (index > 0) append("\n")  // Add newline before headings except for the first line
                    withStyle(style = SpanStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)) {
                        append(headingText)
                    }
                    append("\n")  // Add newline after heading
                    return@forEachIndexed
                }

                // Handle bold text (**bold**)
                var lineStart = 0
                while (lineStart < currentLine.length) {
                    val boldStartIndex = currentLine.indexOf("**", lineStart)
                    if (boldStartIndex == -1) {
                        append(currentLine.substring(lineStart))
                        break
                    }
                    append(currentLine.substring(lineStart, boldStartIndex))
                    val boldEndIndex = currentLine.indexOf("**", boldStartIndex + 2)
                    if (boldEndIndex == -1) {
                        append(currentLine.substring(boldStartIndex))  // Append the rest if no closing `**`
                        break
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(currentLine.substring(boldStartIndex + 2, boldEndIndex))
                    }
                    lineStart = boldEndIndex + 2
                }
                append("\n")  // Add newline after each line
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
    }
}

// Data class to represent chat messages (only text-based, no voice notes)
data class ChatMessage(
    val sender: String,
    val content: String,
    val isVoiceNote: Boolean
)