package com.example.bookform_jetpackcompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookform_jetpackcompose.R
import com.example.bookform_jetpackcompose.ui.components.HeaderDialog
import com.example.bookform_jetpackcompose.ui.components.MainButtonCustom
import com.example.bookform_jetpackcompose.ui.components.OutlinedTextFieldCustom
import com.example.bookform_jetpackcompose.ui.components.SwitchCustom

@Composable
fun BookScreen(
    bookViewModel: BookViewModel = viewModel()
) {
    val uiState by bookViewModel.uiState.collectAsStateWithLifecycle()
    val books = uiState.books

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Mi Biblioteca",
            style = MaterialTheme.typography.titleLarge
        )

        MainButtonCustom(
            text = "REGISTRAR NUEVO LIBRO",
            onClick = {
                bookViewModel.onEvent(
                    BookEvent.OnShowDialog
                )
            }
        )

        LazyColumn(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            if (books.isEmpty()) {
                item {
                    MessageBook()
                }
            } else {
                items(books) { book ->
                    ItemBook(
                        title = book.title,
                        author = book.author,
                        isRead = book.isRead
                    ) {
                        bookViewModel.onEvent(
                            BookEvent.OnBookClick(bookId = book.id)
                        )
                    }
                }
            }
        }
    }

    BookForm(
        uiState = uiState,
        onEvent = bookViewModel::onEvent
    )
}

@Composable
private fun BookForm(
    uiState: BookUiState,
    onEvent: (BookEvent) -> Unit,
) {
    val mode = uiState.mode

    val titleDialog = when (mode) {
        BookMode.Create -> "Registrar Nuevo Libro"
        is BookMode.Edit -> "Editar Detalles del Libro"
        is BookMode.View -> "Detalles del Libro"
    }

    val titleButton = when (mode) {
        BookMode.Create -> "REGISTRAR LIBRO"
        is BookMode.Edit -> "GUARDAR CAMBIOS"
        is BookMode.View -> "MODIFICAR DETALLES"
    }

    val actionButton = when (mode) {
        is BookMode.View -> {
            {
                onEvent(
                    BookEvent.OnChangeMode(BookMode.Edit(bookId = 0))
                )
            }
        }

        else -> {
            {
                onEvent(
                    BookEvent.OnSaveClick
                )
            }
        }
    }

    val isReadOnly = mode is BookMode.View

    if (uiState.showDialog) {
        Dialog(
            onDismissRequest = {
                onEvent(
                    BookEvent.OnCloseDialog
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HeaderDialog(title = titleDialog)

                OutlinedTextFieldCustom(
                    value = uiState.title,
                    onValueChange = {
                        onEvent(
                            BookEvent.OnChangeTitle(it)
                        )
                    },
                    label = stringResource(R.string.label_title_book),
                    readOnly = isReadOnly
                )

                OutlinedTextFieldCustom(
                    value = uiState.author,
                    onValueChange = {
                        onEvent(
                            BookEvent.OnChangeAuthor(it)
                        )
                    },
                    label = stringResource(R.string.label_name_author)
                )

                SwitchCustom(
                    text = "Libro leido:",
                    checked = uiState.isRead,
                    onChangeChecked = {
                        onEvent(
                            BookEvent.OnChangeIsRead(it)
                        )
                    }
                )

                MainButtonCustom(
                    text = titleButton,
                    onClick = actionButton
                )
            }
        }
    }
}

@Composable
private fun ItemBook(title: String, author: String, isRead: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick() }
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = author,
                style = MaterialTheme.typography.bodyLarge
            )
            
            Text(
                text = if (isRead) "Leido" else "No Leido",
                style = MaterialTheme.typography.bodyLarge,
                color = if (isRead) Color.Green else Color.Red
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}

@Composable
private fun MessageBook() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No Se Encontraron Libros",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}