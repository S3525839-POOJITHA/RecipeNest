package uk.ac.tees.mad.s3525839.recipenest

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.s3525839.recipenest.auth.LoginScreen
import uk.ac.tees.mad.s3525839.recipenest.auth.SignupScreen
import uk.ac.tees.mad.s3525839.recipenest.ui.HomeScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("home") { HomeScreen() }
    }
}
