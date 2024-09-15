package com.jabama.challenge.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.jabama.challenge.ui.navigation.GithubScreens.AUTHENTICATION_SCREEN
import com.jabama.challenge.ui.navigation.GithubScreens.SEARCH_SCREEN

class GithubNavigationActions(private val navController: NavController) {

    fun navigateToAuthentication() {
        navController.navigate(
            route = AUTHENTICATION_SCREEN
        ) {
            this.popUpTo(id = navController.graph.findStartDestination().id) {
                inclusive = true
                saveState = true
            }
            launchSingleTop = true
        }
    }

    fun navigateToSearch() {
        navController.navigate(
            route = SEARCH_SCREEN
        ) {
            this.popUpTo(id = navController.graph.findStartDestination().id) {
                inclusive = true
                saveState = true
            }
            launchSingleTop = true
        }
    }
}