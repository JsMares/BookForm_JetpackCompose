package com.example.bookform_jetpackcompose.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class BookViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState
    
    fun onEvent(event: BookEvent) {
        when (event) {
            is BookEvent.OnChangeTitle -> {
                onChangeTitle(title = event.title)
            }

            is BookEvent.OnChangeAuthor -> {
                onChangeAuthor(author = event.author)
            }

            is BookEvent.OnChangeIsRead -> {
                onChangeIsRead(isRead = event.isRead)
            }

            is BookEvent.OnChangeMode -> {
                onChangeMode(mode = event.mode)
            }

            is BookEvent.OnShowDialog -> { showDialog() }

            is BookEvent.OnCloseDialog -> { closeDialog() }

            BookEvent.OnSaveClick -> { onSave() }
        }
    }
    
    private fun onChangeTitle(title: String) {
        _uiState.update { 
            it.copy(title = title)
        }
    }

    private fun onChangeAuthor(author: String) {
        _uiState.update {
            it.copy(author = author)
        }
    }

    private fun onChangeIsRead(isRead: Boolean) {
        _uiState.update {
            it.copy(isRead = isRead)
        }
    }

    private fun onChangeMode(mode: BookMode) {
        _uiState.update {
            it.copy(mode = mode)
        }
    }

    private fun showDialog() {
        _uiState.update {
            it.copy(showDialog = true)
        }
    }

    private fun closeDialog() {
        _uiState.update {
            it.copy(showDialog = false)
        }
    }

    private fun onSave() {  }
}