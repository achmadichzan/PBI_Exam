package com.sipalingandroid.pbiexam.ui.presentation.exam

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ExamViewModel: ViewModel() {

    var isDialogShown by mutableStateOf(false)
        private set

    var isRefreshing by mutableStateOf(false)
        private set

    fun reloadBySwipe(onRefresh: () -> Unit) {
        viewModelScope.launch {
            isRefreshing = true
            onRefresh()
        }
    }

    fun stopReload() {
        viewModelScope.launch {
            isRefreshing = false
        }
    }

    fun onExamClick() {
        isDialogShown = true
    }

    fun onDismissDialog() {
        isDialogShown = false
    }
}