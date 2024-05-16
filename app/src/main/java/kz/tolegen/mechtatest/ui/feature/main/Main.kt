package kz.tolegen.mechtatest.ui.feature.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kz.tolegen.mechtatest.NavScreen
import kz.tolegen.mechtatest.ui.feature.detail.DetailScreen
import kz.tolegen.mechtatest.ui.feature.home.HomeScreen

@Composable
fun MechtaTestMainScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavScreen.Home.route) {
        composable(route = NavScreen.Home.route) {
            HomeScreen(
                viewModel = hiltViewModel(),
                onNavigationRequested = { code ->
                    navController.navigate("${NavScreen.Detail.route}/$code")
                }
            )
        }

        composable(
            route = NavScreen.Detail.routeWithArgument,
            arguments = listOf(
                navArgument(NavScreen.Detail.smartphone_code) {
                    type = NavType.StringType
                }
            )
        ) {
            DetailScreen(
                viewModel = hiltViewModel(),
                pressOnBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}