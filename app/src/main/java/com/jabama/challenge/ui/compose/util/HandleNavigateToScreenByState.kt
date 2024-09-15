package com.jabama.challenge.ui.compose.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
internal fun HandleNavigateToScreenByState(
    isEligibleForNavigation: Boolean,
    navigateToScreen: () -> Unit
) {
    LaunchedEffect(key1 = isEligibleForNavigation) {
        if (isEligibleForNavigation) {
            navigateToScreen()
        }
    }
}