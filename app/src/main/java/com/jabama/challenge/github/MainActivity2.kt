package com.jabama.challenge.github

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jabama.challenge.data.remote.auth.CLIENT_ID
import com.jabama.challenge.data.remote.auth.REDIRECT_URI
import com.jabama.challenge.github.databinding.ActivityMainBinding


class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.authorize.setOnClickListener { _ ->
            val url = "https://github.com/login/oauth/authorize?client_id=$CLIENT_ID&redirect_uri=$REDIRECT_URI&scope=repo user&state=0"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
    }
}
