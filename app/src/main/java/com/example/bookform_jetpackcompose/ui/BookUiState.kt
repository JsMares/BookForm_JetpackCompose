package com.example.bookform_jetpackcompose.ui

data class BookUiState(
    val title: String = "",
    val author: String = "",
    val isRead: Boolean = false,

    val showDialog: Boolean = false,

    val mode: BookMode = BookMode.Create
)
