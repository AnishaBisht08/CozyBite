package com.example.cozybite.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cozybite.ui.screens.CreateRecipeScreen
import com.example.cozybite.ui.screens.CustomSplashScreen
import com.example.cozybite.ui.screens.DetailScreen
import com.example.cozybite.ui.screens.FavoriteScreen
import com.example.cozybite.ui.screens.HomeScreen
import com.example.cozybite.ui.screens.LoginScreen
import com.example.cozybite.ui.screens.SearchScreen
import com.example.cozybite.ui.screens.SignUpScreen
import com.example.cozybite.ui.screens.UserRecipeScreen
import com.example.cozybite.ui.screens.WelcomeScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    val currentUser = FirebaseAuth.getInstance().currentUser


    val startDestination =
        if (currentUser != null) {
            Screen.Home.route
        } else {
            Screen.Splash.route
        }


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    val bottomBarRoutes = listOf(
        Screen.Home.route,
        Screen.Search.route,
        Screen.Favorite.route,
        Screen.UserRecipe.route,
    )

    Scaffold(
        bottomBar = {

            if (currentRoute in bottomBarRoutes) {

                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.background,
                ) {

                    NavigationBarItem(
                        selected = currentRoute == Screen.Home.route,
                        onClick = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) {
                                    saveState = true
                                }
                                launchSingleTop = true

                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home"
                            )
                        },
                        label = {
                            Text("Home")
                        }
                    )

                    NavigationBarItem(
                        selected = currentRoute == Screen.Search.route,
                        onClick = {
                            navController.navigate(Screen.Search.route) {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        },
                        label = {
                            Text("Search")
                        }
                    )

                    NavigationBarItem(
                        selected = currentRoute == Screen.Favorite.route,
                        onClick = {
                            navController.navigate(Screen.Favorite.route) {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Saved"
                            )
                        },
                        label = {
                            Text("Favorite")
                        }
                    )

                    NavigationBarItem(
                        selected = currentRoute == Screen.UserRecipe.route,
                        onClick = {
                            navController.navigate(Screen.UserRecipe.route) {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Book,
                                contentDescription = "My recipes"
                            )
                        },
                        label = {
                            Text("My Recipes")
                        }
                    )
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(Screen.Welcome.route) {

                WelcomeScreen(
                    onGetStarted = {

                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Welcome.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(Screen.Login.route) {

                LoginScreen(

                    onLoginClick = {

                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                    },

                    onSignUpClick = {
                        navController.navigate(Screen.Signup.route)
                    },

                    onGoogleClick = {

                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                    },

                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.Signup.route) {

                SignUpScreen(

                    onSignUpClick = {

                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Signup.route) {
                                inclusive = true
                            }
                        }
                    },

                    onBackClick = {
                        navController.popBackStack()
                    },

                    onGoogleClick = {

                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Signup.route) {
                                inclusive = true
                            }
                        }
                    },

                    onLoginClick = {
                        navController.navigate(Screen.Login.route)
                    }
                )
            }


            composable(Screen.Splash.route) {

                CustomSplashScreen(
                    onFinished = {

                        navController.navigate(Screen.Welcome.route) {

                            popUpTo(Screen.Splash.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(Screen.Home.route) {

                HomeScreen(

                    onSearchClick = {
                        navController.navigate(Screen.Search.route)
                    },

                    onRecipeClick = { id ->

                        navController.navigate("detail/$id")
                    }
                )
            }

            composable(Screen.Search.route) {

                SearchScreen(

                    onRecipeClick = { id ->

                        navController.navigate("detail/$id")
                    }
                )
            }


            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    onRecipeClick = { id ->

                        navController.navigate("detail/$id")
                    }
                )
            }

            composable(Screen.UserRecipe.route) {
                UserRecipeScreen(
                    navController = navController
                )
            }


            composable(Screen.CreateRecipe.route) {
                CreateRecipeScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onRecipeAdded = {
                        navController.popBackStack()
                    }
                )
            }


            composable(
                route = Screen.Detail.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->

                val detailId =
                    backStackEntry.arguments?.getString("id") ?: ""

                Log.d(
                    "RECIPE",
                    "Meal ID : $detailId"
                )

                DetailScreen(
                    detailId = detailId,
                    onBackClick = { navController.popBackStack() }
                )
            }


        }
    }
}
