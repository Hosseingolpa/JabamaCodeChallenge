package com.jabama.challenge.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.unit.LayoutDirection
import com.jabama.challenge.ui.compose.common.ChangeLayoutDirection


private val DarkColorScheme = darkColorScheme(
    primary = Blue_A_200,
    secondary = Blue_A_200,
    tertiary = ColorAccentDark,
    background = DarkGray,
    surface = DarkGray,
    onPrimary = DarkGray,
    onSecondary = White,
    onBackground = White,
    onSurface = White,
)

private val LightColorScheme = lightColorScheme(
    primary = Blue_900,
    secondary = Blue_900,
    tertiary = ColorAccentLight,
    background = White,
    surface = White,
    onPrimary = White,
    onSecondary = DarkGray,
    onBackground = DarkGray,
    onSurface = DarkGray,
)

@Composable
fun GithubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    ChangeLayoutDirection(layoutDirection = LayoutDirection.Ltr) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = MetroTypography,
            shapes = Shapes,
            content = content,
        )
    }

}