package com.sipalingandroid.pbiexam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    private val loadingState = MutableStateFlow(true)
    val isLoading = loadingState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)
            loadingState.value = false
        }
    }
}