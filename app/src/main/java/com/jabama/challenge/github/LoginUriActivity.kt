package com.jabama.challenge.github

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.jabama.challenge.domain.repository.AuthenticationRepository
import com.jabama.challenge.domain.usecase.FetchNewAccessTokenUseCase
import com.jabama.challenge.domain.usecase.GetAccessTokenUseCase
import com.jabama.challenge.github.databinding.LoginUriActivityBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class LoginUriActivity : Activity() {
    private val getAccessTokenUseCase: GetAccessTokenUseCase by inject()
    private val fetchNewAccessTokenUseCase: FetchNewAccessTokenUseCase by inject()

    private lateinit var binding: LoginUriActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginUriActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        val intent = intent
        if (Intent.ACTION_VIEW == intent.action) {
            val uri = intent.data
            val codeParameter = uri?.getQueryParameter("code") ?: ""
            codeParameter.takeIf { it.isNotEmpty() }?.let { code ->
                val accessTokenJob = CoroutineScope(Dispatchers.IO).launch {
                   fetchNewAccessTokenUseCase.execute(code = code)
                }

                accessTokenJob.invokeOnCompletion {
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.token.text = getAccessTokenUseCase.execute()?: ""
                        this.cancel()
                        accessTokenJob.cancelAndJoin()
                    }
                }
            } ?: run { finish() }
        }


    }
}