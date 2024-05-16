package kz.tolegen.mechtatest


sealed class NavScreen(val route: String) {

    data object Home : NavScreen("Home")

    data object Detail : NavScreen("Detail") {

        const val routeWithArgument: String = "Detail/{smartphone_code}"

        const val smartphone_code: String = "smartphone_code"
    }
}