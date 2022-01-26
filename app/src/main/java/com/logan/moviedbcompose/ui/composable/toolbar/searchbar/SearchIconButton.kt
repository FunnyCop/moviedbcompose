package com.logan.moviedbcompose.ui.composable.toolbar.searchbar

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.logan.moviedbcompose.R

@Composable
fun SearchIconButton(contentColor: Color, hideShowSearchBar: () -> Unit) {

    IconButton(onClick = hideShowSearchBar) { Icon(

        painter = painterResource(R.drawable.ic_baseline_search_24),
        contentDescription = stringResource(R.string.Search),
        tint = contentColor

    ) }

}