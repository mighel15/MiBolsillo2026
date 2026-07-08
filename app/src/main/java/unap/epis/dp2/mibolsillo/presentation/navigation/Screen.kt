package unap.epis.dp2.mibolsillo.presentation.navigation

sealed class Screens(val route: String)
{
    object Home: Screens("home")
    object Login: Screens("login")

}
