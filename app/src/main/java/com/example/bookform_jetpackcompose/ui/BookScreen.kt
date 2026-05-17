package com.example.bookform_jetpackcompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookform_jetpackcompose.R
import com.example.bookform_jetpackcompose.ui.components.HeaderDialog
import com.example.bookform_jetpackcompose.ui.components.MainButtonCustom
import com.example.bookform_jetpackcompose.ui.components.OutlinedTextFieldCustom
import com.example.bookform_jetpackcompose.ui.components.SwitchCustom

@Preview(showBackground = true)
@Composable
fun BookScreen(
    bookViewModel: BookViewModel = viewModel()
) {
    val uiState by bookViewModel.uiState.collectAsStateWithLifecycle()

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

    val isRead = mode is BookMode.View

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
                    readOnly = isRead
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

