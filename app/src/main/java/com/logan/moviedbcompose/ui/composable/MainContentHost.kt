package com.logan.moviedbcompose.ui.composable

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.logan.moviedbcompose.R
import com.logan.moviedbcompose.ui.composable.toolbar.TopToolbar

@ExperimentalComposeUiApi
@Composable
fun MainContentHost() {

    val systemUiController = rememberSystemUiController()
    val isLightMode = MaterialTheme.colors.isLight

    SideEffect {

        systemUiController.setSystemBarsColor(

            color = if (isLightMode) Color.White else Color.Black,
            darkIcons = isLightMode

        )

    }

    TopToolbar(stringResource(R.string.Home), isLightMode)

}