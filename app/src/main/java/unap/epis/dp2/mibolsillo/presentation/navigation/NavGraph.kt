package unap.epis.dp2.mibolsillo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import unap.epis.dp2.mibolsillo.presentation.screens.home.HomeScreen
import unap.epis.dp2.mibolsillo.presentation.screens.login.LoginScreen
import unap.epis.dp2.mibolsillo.presentation.screens.main.MainScreen

@Composable
fun NavGraph() {

    var navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Login.route,
    ){
        composable(Screens.Login.route) {
            LoginScreen(navController)
        }
        composable(Screens.Home.route) {
            HomeScreen()
        }
        composable(Screens.Main.route) {
            MainScreen()
        }
    }

}