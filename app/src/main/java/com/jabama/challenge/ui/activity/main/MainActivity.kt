package com.jabama.challenge.ui.activity.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.jabama.challenge.ui.theme.GithubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubTheme {
                GithubNavGraph(
                    modifier = Modifier
                        .safeDrawingPadding()
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                )
            }
        }
    }
}