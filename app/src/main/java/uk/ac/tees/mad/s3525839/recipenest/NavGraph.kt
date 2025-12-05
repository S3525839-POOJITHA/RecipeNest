package uk.ac.tees.mad.s3525839.recipenest

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uk.ac.tees.mad.s3525839.recipenest.auth.LoginScreen
import uk.ac.tees.mad.s3525839.recipenest.auth.SignupScreen
import uk.ac.tees.mad.s3525839.recipenest.ui.AddRecipeScreen
import uk.ac.tees.mad.s3525839.recipenest.ui.HomeScreen
import uk.ac.tees.mad.s3525839.recipenest.ui.RecipeDetailScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("addRecipe") { AddRecipeScreen { navController.popBackStack() } }
        composable(
            "recipeDetail/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            RecipeDetailScreen(backStackEntry.arguments?.getInt("recipeId"), navController)
        }
    }
}
