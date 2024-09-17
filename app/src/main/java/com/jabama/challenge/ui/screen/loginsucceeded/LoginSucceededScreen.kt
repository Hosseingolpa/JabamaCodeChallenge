package com.jabama.challenge.ui.screen.loginsucceeded

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jabama.challenge.github.R
import com.jabama.challenge.ui.theme.GithubTheme

@Composable
fun LoginSucceededScreen(
    state: LoginSucceedUiState,
    onFinishActivityClick: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LoginImage(accessTokenState = state.accessTokenState)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = getDescriptionResourceByState(state.accessTokenState)),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.weight(1f))

        LoginSucceedButton(
            accessToken = state.accessTokenState,
            onFinishActivityClick = onFinishActivityClick
        )

    }

}

@Composable
private fun LoginImage(
    accessTokenState: AccessTokenState
) {
    Column(
        modifier = Modifier.size(180.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedContent(
            targetState = accessTokenState,
            label = ""
        ) {
            when (it) {
                AccessTokenState.Success -> SuccessImage()
                AccessTokenState.Error -> ErrorImage()
                AccessTokenState.Loading -> LoadingImage()
            }
        }
    }
}

@Composable
private fun LoadingImage() {
    Image(
        modifier = Modifier.size(150.dp),
        painter = painterResource(id = R.drawable.loading),
        contentDescription = null
    )
}

@Composable
private fun SuccessImage() {
    Image(
        modifier = Modifier.size(150.dp),
        painter = painterResource(id = R.drawable.success),
        contentDescription = null
    )
}

@Composable
private fun ErrorImage() {
    Image(
        modifier = Modifier.size(150.dp),
        painter = painterResource(id = R.drawable.error),
        contentDescription = null
    )
}

@Composable
private fun LoginSucceedButton(
    accessToken: AccessTokenState,
    onFinishActivityClick: () -> Unit
) {
    val buttonTitleResource = remember(accessToken) {
        getButtonTitleResourceByState(accessToken)
    }

    val isButtonVisible = remember(buttonTitleResource) {
        buttonTitleResource != null
    }

    AnimatedVisibility(
        visible = isButtonVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        buttonTitleResource?.let { titleResource ->
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.Black
                ),
                onClick = onFinishActivityClick
            ) {
                Text(
                    text = stringResource(id = titleResource),
                    fontSize = 18.sp
                )
            }
        }
    }
}

private fun getDescriptionResourceByState(accessTokenState: AccessTokenState): Int {
    return when (accessTokenState) {
        AccessTokenState.Success -> R.string.you_are_logged_in_successfully
        AccessTokenState.Error -> R.string.login_failed
        AccessTokenState.Loading -> R.string.please_wait_for_login
    }
}

private fun getButtonTitleResourceByState(accessTokenState: AccessTokenState): Int? {
    return when (accessTokenState) {
        AccessTokenState.Success -> R.string.go_to_search
        AccessTokenState.Error -> R.string.back
        AccessTokenState.Loading -> null
    }
}

@Preview
@Composable
private fun LoginSucceededScreenPreview() {
    GithubTheme {
        LoginSucceededScreen(
            state = LoginSucceedUiState(),
            onFinishActivityClick = {}
        )
    }
}