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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.launch
import java.io.File
import java.util.Locale
import java.util.UUID

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    private val db = FirebaseFirestore.getInstance()
    private val chatCollectionRef = db.collection("chats")  // Firestore collection for chats

    private val _welcomeMessage = MutableLiveData("Welcome to Synapse AI 👋")
    val welcomeMessage: LiveData<String> get() = _welcomeMessage

    var isChatStarted by mutableStateOf(false)

    private val _chatMessages = MutableLiveData<List<ChatMessage>>()
    val chatMessages: LiveData<List<ChatMessage>> get() = _chatMessages

    var chatInput: String by mutableStateOf("")
    private var conversationId: String = UUID.randomUUID().toString() // Generate a new conversation ID

    init {
        // Fetch existing chat messages from Firebase
        fetchChatMessagesFromFirebase()
    }

    fun startChat() {
        isChatStarted = true
        _chatMessages.value = listOf()
        conversationId = UUID.randomUUID().toString() // Reset conversation ID for a new chat
    }

    fun onChatInputChanged(newInput: String) {
        chatInput = newInput
    }

    fun sendMessage() {
        val userMessage = chatInput
        if (userMessage.isNotBlank()) {
            chatInput = ""
            val chatMessage = ChatMessage(sender = "User", content = userMessage, isVoiceNote = false, conversationId = conversationId)

            // Add user message to the local list and Firebase
            addMessageToFirestore(chatMessage)

            viewModelScope.launch {
                val responseText = generateResponseFromGemini(userMessage)
                val aiMessage = ChatMessage(sender = "AI", content = responseText.toString(), isVoiceNote = false, conversationId = conversationId)

                // Add AI response to the local list and Firebase
                addMessageToFirestore(aiMessage)
            }
        }
    }

    private fun addMessageToFirestore(chatMessage: ChatMessage) {
        chatCollectionRef.add(chatMessage)
            .addOnSuccessListener { documentReference ->
                val currentMessages = _chatMessages.value ?: listOf()
                _chatMessages.value = currentMessages + chatMessage
            }
            .addOnFailureListener { e ->
                Log.w("ChatViewModel", "Error adding message", e)
            }
    }

    private fun fetchChatMessagesFromFirebase() {
        chatCollectionRef.whereEqualTo("conversationId", conversationId) // Filter messages by conversationId
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                val messages = result.map { document ->
                    document.toObject(ChatMessage::class.java)
                }
                _chatMessages.value = messages
            }
            .addOnFailureListener { e ->
                Log.w("ChatViewModel", "Error fetching messages", e)
            }
    }

    private suspend fun generateResponseFromGemini(userMessage: String): AnnotatedString {
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
            text.lines().forEachIndexed { index, line ->
                if (line.startsWith("##")) {
                    withStyle(style = SpanStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)) {
                        append(line.removePrefix("##").trim())
                    }
                    append("\n")
                } else if (line.contains("**")) {
                    var start = 0
                    while (start < line.length) {
                        val boldStart = line.indexOf("**", start)
                        if (boldStart == -1) {
                            append(line.substring(start))
                            break
                        }
                        append(line.substring(start, boldStart))
                        val boldEnd = line.indexOf("**", boldStart + 2)
                        if (boldEnd == -1) {
                            append(line.substring(boldStart))
                            break
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(line.substring(boldStart + 2, boldEnd))
                        }
                        start = boldEnd + 2
                    }
                    append("\n")
                } else {
                    append(line)
                    append("\n")
                }
            }
        }
    }
}


data class ChatMessage(
    val sender: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isVoiceNote: Boolean = false,
    val conversationId: String = "" // New field
)
