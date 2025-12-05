package uk.ac.tees.mad.s3525839.recipenest.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String, val label: String, val icon: @Composable () -> Unit) {
    object Home : Screen("home_content", "Home", { Icon(Icons.Default.Home, contentDescription = null) })
    object Search : Screen("search", "Search", { Icon(Icons.Default.Search, contentDescription = null) })
    object MyRecipes : Screen("my_recipes", "My Recipes", { Icon(Icons.Default.List, contentDescription = null) })
    object Favorites : Screen("favorites", "Favorites", { Icon(Icons.Default.Favorite, contentDescription = null) })
    object Profile : Screen("profile", "Profile", { Icon(Icons.Default.Person, contentDescription = null) })
}

@Composable
fun HomeScreen(mainNavController: NavController) {
    val navController = rememberNavController()
    val items = listOf(Screen.Home, Screen.Search, Screen.MyRecipes, Screen.Favorites, Screen.Profile)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { mainNavController.navigate("addRecipe") }) {
                Icon(Icons.Default.Add, contentDescription = "Add Recipe")
            }
        },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = screen.icon,
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeContentScreen(mainNavController) }
            composable(Screen.Search.route) { SearchScreen(mainNavController) }
            composable(Screen.MyRecipes.route) { MyRecipesScreen(mainNavController) }
            composable(Screen.Favorites.route) { FavoritesScreen(mainNavController) }
            composable(Screen.Profile.route) { ProfileScreen() }
        }
    }
}
