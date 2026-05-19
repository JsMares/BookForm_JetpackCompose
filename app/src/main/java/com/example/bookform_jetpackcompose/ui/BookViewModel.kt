package com.example.bookform_jetpackcompose.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.log

class BookViewModel: ViewModel() {
    companion object {
        var lastId: Int = 0
    }

    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState

    // Variable temporal para mostrar libros. En siguientes actualizaciones se sustituirá por Combine
    val showBooks = _uiState.value.books
    
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

            is BookEvent.OnShowBookClick -> {
                onLoadDataBook(event.bookId)
            }

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

    private fun onSave() { 
        val mode = uiState.value.mode
        
        when (mode) {
            BookMode.Create -> {
                addBook()
                clearFields()
                closeDialog()
            }
            is BookMode.Edit -> TODO()
            is BookMode.View -> {
                return
            }
        }
    }

    private fun addBook() {
        lastId++
        val id = lastId

        val state = _uiState.value

        val data = BookModel(
            id = id,
            title = state.title,
            author = state.author,
            isRead = state.isRead
        )

        _uiState.update {
            it.copy(
                books = it.books + data
            )
        }
    }

    private fun clearFields() {
        _uiState.update {
            it.copy(
                title = "",
                author = "",
                isRead = false
            )
        }
    }

    private fun onLoadDataBook(bookId: Int) {
        Log.d("BOOK_ID:", bookId.toString())
    }
}