package com.hamzadev.synapseai.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {
    fun startTimeout(onTimeOut: () -> Unit){
        viewModelScope.launch {
            delay(3000)
            onTimeOut()
        }
    }
}