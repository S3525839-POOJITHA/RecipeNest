package uk.ac.tees.mad.s3525839.recipenest.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import uk.ac.tees.mad.s3525839.recipenest.ui.viewmodel.HomeViewModel

@Composable
fun HomeContentScreen(navController: NavController, homeViewModel: HomeViewModel = viewModel()) {
    val recipes by homeViewModel.recipes.collectAsState()

    LazyColumn {
        items(recipes) { recipe ->
            RecipeListItem(recipe = recipe, navController = navController)
        }
    }
}
