package com.hamzadev.synapseai.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamzadev.synapseai.model.AIAssistant
import com.hamzadev.synapseai.repository.AIAssistantRepository

class AIAssistantViewModel : ViewModel() {

    private val repository = AIAssistantRepository()

    // LiveData for selected category and assistant list
    private val _selectedCategory = MutableLiveData("ALL")
    val selectedCategory: LiveData<String> = _selectedCategory

    private val _assistants = MutableLiveData<List<AIAssistant>>()
    val assistants: LiveData<List<AIAssistant>> = _assistants

    init {
        // Load assistants for the default category
        loadAssistantsForCategory(_selectedCategory.value ?: "ALL")
    }

    // Function to update the selected category and fetch data accordingly
    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
        loadAssistantsForCategory(category)
    }

    // Helper function to load assistants based on the category
    private fun loadAssistantsForCategory(category: String) {
        _assistants.value = if (category == "ALL") {
            repository.getAllAssistants() // Fetch all assistants from the repository
        } else {
            repository.getAssistantsForCategory(category)
        }
    }

}
