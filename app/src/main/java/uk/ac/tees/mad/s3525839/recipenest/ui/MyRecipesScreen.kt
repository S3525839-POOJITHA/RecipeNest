package uk.ac.tees.mad.s3525839.recipenest.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import uk.ac.tees.mad.s3525839.recipenest.viewmodel.MyRecipesViewModel

@Composable
fun MyRecipesScreen(navController: NavController) {
    val viewModel: MyRecipesViewModel = viewModel()
    val myRecipes by viewModel.myRecipes.collectAsState()

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(myRecipes) { recipe ->
            RecipeListItem(recipe = recipe, onClick = {
                navController.navigate("recipeDetail/${recipe.id}")
            })
        }
    }
}
