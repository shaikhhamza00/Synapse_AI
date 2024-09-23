package com.hamzadev.synapseai.model

data class ChatMessage(
    val sender: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isVoiceNote: Boolean = false,
    val conversationId: String = "" // New field
)
