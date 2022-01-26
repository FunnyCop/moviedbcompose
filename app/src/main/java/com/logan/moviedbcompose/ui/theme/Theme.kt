package com.logan.moviedbcompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(

    primary = Purple200,
    primaryVariant = Purple700,
    onPrimary = Color.White,

    secondary = Teal200,
    onSecondary = Color.Black,

    background = Color.Black,
    onBackground = Color.White,

    surface = Color.Black,
    onSurface = Color.White

)

private val LightColorPalette = lightColors(

    primary = Purple500,
    primaryVariant = Purple700,
    onPrimary = Color.White,

    secondary = Teal200,
    onSecondary = Color.Black,

    background = Color.White,
    onBackground = Color.Black,

    surface = Color.White,
    onSurface = Color.Black

)

@Composable
fun MovieDBComposeTheme(

    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit

) {

    MaterialTheme(

        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content

    )

}