package uk.ac.tees.mad.s3525839.recipenest.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.ac.tees.mad.s3525839.recipenest.ui.viewmodel.FavoritesViewModel

@Composable
fun FavoritesScreen(favoritesViewModel: FavoritesViewModel = viewModel()) {
    val favoriteRecipes by favoritesViewModel.favoriteRecipes.collectAsState()

    if (favoriteRecipes.isEmpty()) {
        Text("You have no favorite recipes yet.")
    } else {
        LazyColumn {
            items(favoriteRecipes) { recipe ->
                Text(recipe.title)
            }
        }
    }
}
