package com.example.cozybite.ui.navigation

sealed class Screen(val route: String) {
    object Splash: Screen("splash")
    object Welcome: Screen("welcome")
    object Login: Screen("login")
    object Signup: Screen("signup")
    object Home: Screen("home")
    object Search: Screen("search")

    object Detail : Screen("detail/{id}")
    object Favorite : Screen("favorite")
    object CreateRecipe : Screen("create_recipe")
    object UserRecipe : Screen("user_recipe")

}