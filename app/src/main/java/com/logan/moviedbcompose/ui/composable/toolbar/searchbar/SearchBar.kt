package com.logan.moviedbcompose.ui.composable.toolbar.searchbar

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.logan.moviedbcompose.R

@Composable
fun SearchBar(contentColor: Color, hide: () -> Unit) {

    val focusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()
    val interactionSource = remember { MutableInteractionSource() }
    var text by remember { mutableStateOf("") }

    Row( modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp, 16.dp, 16.dp) ) {

        OutlinedTextField(

            value = text,
            onValueChange = { text = it },
            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
            label = { Text(stringResource(R.string.Search), color = contentColor) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions { hide(); focusManager.clearFocus() },
            singleLine = true,
            interactionSource = interactionSource,
            colors = TextFieldDefaults.outlinedTextFieldColors(contentColor)

        )

    }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

}