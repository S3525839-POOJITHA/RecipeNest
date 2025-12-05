package uk.ac.tees.mad.s3525839.recipenest.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import uk.ac.tees.mad.s3525839.recipenest.model.Recipe
import uk.ac.tees.mad.s3525839.recipenest.ui.viewmodel.FavoritesViewModel

@Composable
fun FavoritesScreen(navController: NavController, favoritesViewModel: FavoritesViewModel = viewModel()) {
    val favoriteRecipes by favoritesViewModel.favoriteRecipes.collectAsState()

    if (favoriteRecipes.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("You have no favorite recipes yet.")
        }
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(favoriteRecipes) { favorite ->
                RecipeListItem(
                    recipe = Recipe(favorite.id, favorite.title, favorite.image),
                    onClick = { navController.navigate("recipeDetail/${favorite.id}") }
                )
            }
        }
    }
}
