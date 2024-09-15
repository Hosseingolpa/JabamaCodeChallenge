package com.jabama.challenge.ui.compose.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

@Composable
fun ChangeLayoutDirection(
    layoutDirection: LayoutDirection = LayoutDirection.Rtl,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection ) {
        content()
    }
}