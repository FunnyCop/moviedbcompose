package com.logan.moviedbcompose.ui.composable.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.logan.moviedbcompose.ui.composable.toolbar.searchbar.SearchBar
import com.logan.moviedbcompose.ui.composable.toolbar.searchbar.SearchIconButton

@ExperimentalComposeUiApi
@Composable
fun TopToolbar(title: String, isLightMode: Boolean) {

    val containerColor = if (isLightMode) Color.White else Color.Black
    val contentColor = if (isLightMode) Color.Black else Color.White
    var showSearchBar by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(containerColor)) {

        SmallTopAppBar(

            title = { Text(title, color = contentColor) },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor),
            actions = { SearchIconButton(contentColor) { showSearchBar = !showSearchBar } }

        )

        if (showSearchBar) SearchBar(contentColor) { showSearchBar = false }

    }

}