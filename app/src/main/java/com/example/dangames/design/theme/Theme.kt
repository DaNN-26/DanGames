package com.example.dangames.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

val colorPalette = ColorPalette(
    accent = AccentColor,
    hint = DarkHintColor,
    single = MainWhiteColor,
    opposite = MainBlackColor,
    singleAltColor = AltWhiteColor,
    oppositeAltColor = AltBlackColor,
    greenColor = GreenColor,
    yellowColor = YellowColor,
    redColor = RedColor
)

val darkColorPalette = ColorPalette(
    accent = AccentColor,
    hint = HintColor,
    single = MainBlackColor,
    opposite = MainWhiteColor,
    singleAltColor = AltBlackColor,
    oppositeAltColor = AltWhiteColor,
    greenColor = GreenColor,
    yellowColor = YellowColor,
    redColor = RedColor
)

@Composable
fun DanGamesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) darkColorPalette else colorPalette

    CompositionLocalProvider(
        value = LocalColors provides colorScheme,
        content = content
    )
}

object DanGamesTheme {
    val colors: ColorPalette
        @Composable @ReadOnlyComposable
        get() = LocalColors.current
}

internal val LocalColors = staticCompositionLocalOf<ColorPalette> {
    error("LocalColors not provided")
}