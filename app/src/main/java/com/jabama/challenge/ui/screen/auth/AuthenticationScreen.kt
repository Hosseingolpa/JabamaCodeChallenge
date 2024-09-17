package com.jabama.challenge.ui.screen.auth

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jabama.challenge.data.remote.auth.CLIENT_ID
import com.jabama.challenge.data.remote.auth.REDIRECT_URI
import com.jabama.challenge.github.R
import com.jabama.challenge.ui.compose.util.HandleNavigateToScreenByState
import com.jabama.challenge.ui.theme.GithubTheme

@Composable
fun AuthenticationScreen(
    state: AuthenticationUiState,
    onLoginClick: () -> Unit,
    navigateToSearchScreen: () -> Unit,
) {
    val context = LocalContext.current

    fun navigateToAuthenticationWithWeb() {
        val url = "https://github.com/login/oauth/authorize?client_id=$CLIENT_ID&redirect_uri=$REDIRECT_URI&scope=repo user&state=0"
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }.let { intent ->
            context.startActivity(intent)
        }
    }

    HandleNavigateToScreenByState(
        isEligibleForNavigation = state.shouldNavigateToAuthenticationWithWeb,
        navigateToScreen = ::navigateToAuthenticationWithWeb
    )

    HandleNavigateToScreenByState(
        isEligibleForNavigation = state.shouldNavigateToSearchScreen,
        navigateToScreen = navigateToSearchScreen
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(id = R.drawable.profile),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.for_searching_github_repository_we_need_to_login),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.weight(1f))


        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Black
            ),
            onClick = onLoginClick
        ) {
            Text(
                text = stringResource(id = R.string.login),
                fontSize = 18.sp
            )
        }
    }
}

@Preview
@Composable
private fun AuthenticationScreenPreview() {
    GithubTheme {
        AuthenticationScreen(
            state = AuthenticationUiState(),
            onLoginClick = {},
            navigateToSearchScreen = {}
        )
    }
}