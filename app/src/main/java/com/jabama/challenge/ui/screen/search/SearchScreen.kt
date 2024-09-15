package com.jabama.challenge.ui.screen.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SearchScreen() {

    Text(
        modifier = Modifier.fillMaxSize(),
        text = "Search",
        textAlign = TextAlign.Center
    )
}