package com.example.bookform_jetpackcompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bookform_jetpackcompose.R
import com.example.bookform_jetpackcompose.ui.components.HeaderDialog
import com.example.bookform_jetpackcompose.ui.components.MainButtonCustom
import com.example.bookform_jetpackcompose.ui.components.OutlinedTextFieldCustom
import com.example.bookform_jetpackcompose.ui.components.SwitchCustom

@Composable
fun BookScreen() {

}

@Composable
private fun BookForm() {
    var value by remember { mutableStateOf("") }
    
    /*Dialog(
        onDismissRequest = { }
    ) {

    }*/

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HeaderDialog("Registrar Nuevo Libro")
        
        OutlinedTextFieldCustom(
            value = value,
            onValueChange = { value = it },
            label = stringResource(R.string.label_title_book)
        )

        OutlinedTextFieldCustom(
            value = value,
            onValueChange = { value = it },
            label = stringResource(R.string.label_name_author)
        )

        SwitchCustom(
            text = "Libro leido:",
            checked = false
        ) { }

        MainButtonCustom("REGISTRAR") { }
    }
}

