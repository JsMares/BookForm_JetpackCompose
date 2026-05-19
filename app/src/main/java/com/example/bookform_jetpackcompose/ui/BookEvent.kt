package com.example.bookform_jetpackcompose.ui

sealed class BookEvent {
    data class OnChangeTitle(val title: String) : BookEvent()
    data class OnChangeAuthor(val author: String) : BookEvent()
    data class OnChangeIsRead(val isRead: Boolean) : BookEvent()

    data class OnChangeMode(val mode: BookMode) : BookEvent()

    data object OnShowDialog : BookEvent()
    data object OnCloseDialog : BookEvent()

    data class OnBookClick(val bookId: Int) : BookEvent()
    data object OnSaveClick : BookEvent()
}