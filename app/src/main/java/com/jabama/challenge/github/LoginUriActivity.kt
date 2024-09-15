package com.jabama.challenge.github

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.jabama.challenge.domain.repository.AuthenticationRepository
import kotlinx.android.synthetic.main.login_uri_activity.token
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class LoginUriActivity : Activity() {
    private val repository: AuthenticationRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_uri_activity)
    }

    override fun onResume() {
        super.onResume()

        val intent = intent
        if (Intent.ACTION_VIEW == intent.action) {
            val uri = intent.data
            val codeParameter = uri?.getQueryParameter("code") ?: ""
            codeParameter.takeIf { it.isNotEmpty() }?.let { code ->
                val accessTokenJob = CoroutineScope(Dispatchers.IO).launch {
                    repository.fetchNewAccessToken(code = code)
                }

                accessTokenJob.invokeOnCompletion {
                    CoroutineScope(Dispatchers.Main).launch {
                        token.text = repository.getAccessToken()?: ""
                        this.cancel()
                        accessTokenJob.cancelAndJoin()
                    }
                }
            } ?: run { finish() }
        }


    }
}