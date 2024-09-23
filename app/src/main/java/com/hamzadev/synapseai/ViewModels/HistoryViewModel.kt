package com.hamzadev.synapseai.ViewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.hamzadev.synapseai.model.ChatMessage

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val db = FirebaseFirestore.getInstance()
    private val chatCollectionRef = db.collection("chats")
    private var listenerRegistration: ListenerRegistration? = null

    // LiveData to store a list of chat messages (conversations)
    private val _chatMessages = MutableLiveData<List<ChatMessage>>()
    val chatMessages: LiveData<List<ChatMessage>> get() = _chatMessages

    // LiveData to store messages of a specific conversation
    private val _conversationMessages = MutableLiveData<List<ChatMessage>>()
    val conversationMessages: LiveData<List<ChatMessage>> get() = _conversationMessages

    // Initialize ViewModel by fetching the available conversations
    init {
        fetchChatMessagesFromFirebase()
    }

    // Fetch the list of all conversations from Firestore
    private fun fetchChatMessagesFromFirebase() {
        listenerRegistration = chatCollectionRef.orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("HistoryViewModel", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val groupedMessages = snapshot.documents.mapNotNull { document ->
                        document.toObject(ChatMessage::class.java) // Use mapNotNull to filter nulls
                    }.groupBy { it.conversationId }
                        .map { it.value.first() }

                    // Update LiveData with the grouped messages
                    _chatMessages.value = groupedMessages
                }
            }
    }

    // Fetch all messages for a specific conversationId
    fun fetchMessagesByConversationId(conversationId: String) {
        chatCollectionRef.whereEqualTo("conversationId", conversationId)
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                // Extract all messages for the specified conversationId
                val messages = result.mapNotNull { document -> // Use mapNotNull here as well
                    document.toObject(ChatMessage::class.java)
                }

                // Update LiveData with the fetched messages
                _conversationMessages.value = messages
            }
            .addOnFailureListener { e ->
                Log.w("HistoryViewModel", "Error fetching conversation messages", e)
            }
    }

    // Clear listener when ViewModel is cleared
    override fun onCleared() {
        super.onCleared()
        listenerRegistration?.remove()
    }
}
