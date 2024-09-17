package com.jabama.challenge.ui.activity.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jabama.challenge.ui.navigation.GithubDestinationScreens
import com.jabama.challenge.ui.navigation.GithubNavigationActions
import com.jabama.challenge.ui.screen.auth.AuthenticationScreen
import com.jabama.challenge.ui.screen.auth.AuthenticationViewModel
import com.jabama.challenge.ui.screen.search.SearchScreen
import com.jabama.challenge.ui.screen.search.SearchViewModel
import com.jabama.challenge.ui.screen.splash.SplashScreen
import com.jabama.challenge.ui.screen.splash.SplashViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun GithubNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = GithubDestinationScreens.SPLASH_ROUTE,
    navAction: GithubNavigationActions = remember(navController) {
        GithubNavigationActions(navController)
    }
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        splashScreenComposable(navAction = navAction)

        authenticationScreenComposable(navAction = navAction)

        searchScreenComposable(navAction = navAction)

    }
}

fun NavGraphBuilder.splashScreenComposable(
    navAction: GithubNavigationActions,
) {
    composable(route = GithubDestinationScreens.SPLASH_ROUTE) {

        val viewModel = koinViewModel<SplashViewModel>()
        val state by viewModel.uiState.collectAsState()

        SplashScreen(
            state = state,
            navigateToAuthenticationScreen = {
                navAction.navigateToAuthentication()
            },
            navigateToSearchScreen = {
                navAction.navigateToSearch()
            }
        )
    }
}


fun NavGraphBuilder.authenticationScreenComposable(
    navAction: GithubNavigationActions
) {
    composable(route = GithubDestinationScreens.AUTHENTICATION_ROUTE) {
        val viewModel = koinViewModel<AuthenticationViewModel>()
        val state by viewModel.uiState.collectAsState()
        AuthenticationScreen(
            state = state,
            onLoginClick = viewModel::onLoginClick,
            navigateToSearchScreen = {
                navAction.navigateToSearch()
            }
        )
    }
}

fun NavGraphBuilder.searchScreenComposable(
    navAction: GithubNavigationActions
) {
    composable(route = GithubDestinationScreens.SEARCH_ROUTE) {
        val viewModel = koinViewModel<SearchViewModel>()
        val state by viewModel.uiState.collectAsState()
        SearchScreen(
            state = state,
            onQueryChange = viewModel::onQueryChange,
            onRetryClick = viewModel::onRetryClick,
            navigateToAuthenticationScreen = {
                navAction.navigateToAuthentication()
            }
        )
    }
}
