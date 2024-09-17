package com.jabama.challenge.ui.activity.loginsucceeded

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.jabama.challenge.ui.screen.loginsucceeded.LoginSucceedViewModel
import com.jabama.challenge.ui.screen.loginsucceeded.LoginSucceededScreen
import com.jabama.challenge.ui.theme.GithubTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginSucceededActivity : ComponentActivity() {

    private val viewModel: LoginSucceedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubTheme {
                val state by viewModel.uiState.collectAsState()
                LoginSucceededScreen(
                    state = state,
                    onFinishActivityClick = {
                        finish()
                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val intent = intent
        if (Intent.ACTION_VIEW == intent.action) {
            val uri = intent.data
            val codeParameter = uri?.getQueryParameter("code") ?: ""
            codeParameter.takeIf { it.isNotEmpty() }?.let { code ->
               viewModel.updateCode(code = code)
            }
        }
    }
}