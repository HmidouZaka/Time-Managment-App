package com.theprojectbyzakariadeveloper.timemanagment.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    background = lightBlack,
    onBackground = white,
    primary = darkDarkBlue,
    onPrimary = darkVolate,
    secondary = DarkRed,
    onSecondary = Darkgreen,
    primaryVariant = DarkGray,
    surface = colorGray
)

private val LightColorPalette = lightColors(
    background = white,
    onBackground = black,
    primary = darkBlue,
    onPrimary = lightVolate,
    secondary = red,
    onSecondary = green,
    primaryVariant = gray,
    surface = lightBlue
)

@Composable
fun TimeManagmentTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}