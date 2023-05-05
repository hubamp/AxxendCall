package com.example.axxendvideocall.make_call.presentation.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Breen111,
    onPrimary = Breen003,
    primaryContainer = Breen002,
    onPrimaryContainer = Breen001,
    inversePrimary = Green111,
    secondary = Green111,
    onSecondary = Green002,
    secondaryContainer = Green001,
    onSecondaryContainer = Green001,
    tertiary = Breen001,
    onTertiary = Breen003,
    tertiaryContainer = Breen002,
    onTertiaryContainer = Breen111,
    background = Breen001,
    onBackground = Breen111,
    surface = Breen111,
    onSurface = Breen111,
    surfaceVariant = Breen111,
    onSurfaceVariant = Breen111,
    inverseSurface = Breen111,
    inverseOnSurface = Breen111,
    error = Red111,
    onError = Breen111,
    errorContainer = Red001,
    onErrorContainer = Breen111,
    outline = Breen111,

)

private val DarkColorScheme = LightColorScheme

@Composable
fun AxxendVideoCallTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.onPrimaryContainer.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}