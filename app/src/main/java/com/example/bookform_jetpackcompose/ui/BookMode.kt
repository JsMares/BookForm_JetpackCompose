package com.example.bookform_jetpackcompose.ui

// Class to define possible moods
sealed class BookMode {
    data object Create : BookMode()
    data class View(val bookId: Int) : BookMode()
    data class Edit(val bookId: Int) : BookMode()
}