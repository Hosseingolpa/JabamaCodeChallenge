package com.jabama.challenge.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jabama.challenge.github.R
import com.jabama.challenge.ui.compose.util.HandleNavigateToScreenByState
import com.jabama.challenge.ui.theme.GithubTheme

@Composable
fun SplashScreen(
    state: SplashUiState,
    navigateToAuthenticationScreen: () -> Unit,
    navigateToSearchScreen: () -> Unit,
) {

    HandleNavigateToScreenByState(
        isEligibleForNavigation = state.shouldNavigateToAuthenticationScreen,
        navigateToScreen = navigateToAuthenticationScreen
    )

    HandleNavigateToScreenByState(
        isEligibleForNavigation = state.shouldNavigateToSearchScreen,
        navigateToScreen = navigateToSearchScreen
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.github), 
            contentDescription = null,
            modifier = Modifier.size(200.dp), 
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = stringResource(id = R.string.github),
            fontSize = 32.sp
        )
        
    }
}



@Preview
@Composable
private fun SplashScreenPreview() {
    GithubTheme {
        SplashScreen(
            state = SplashUiState(),
            navigateToAuthenticationScreen = {},
            navigateToSearchScreen = {}
        )
    }
}