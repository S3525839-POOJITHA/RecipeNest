package uk.ac.tees.mad.s3525839.recipenest.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import uk.ac.tees.mad.s3525839.recipenest.ui.viewmodel.RecipeDetailViewModel

@Composable
fun RecipeDetailScreen(
    recipeId: Int?,
    navController: NavController,
    recipeDetailViewModel: RecipeDetailViewModel = viewModel()
) {
    val isFavorite by recipeDetailViewModel.isFavorite.collectAsState()
    val isCustomRecipe by recipeDetailViewModel.isCustomRecipe.collectAsState()
    val recipe by recipeDetailViewModel.recipe.collectAsState()

    LaunchedEffect(recipeId) {
        recipeId?.let { recipeDetailViewModel.getRecipeDetails(it) }
    }

    if (recipe == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                recipe!!.image?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = recipe!!.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                recipe!!.title?.let { Text(text = it, style = androidx.compose.material3.MaterialTheme.typography.headlineMedium) }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Button(onClick = { recipeDetailViewModel.toggleFavorite() }) {
                        Text(if (isFavorite) "Unsave" else "Save")
                    }
                    if (isCustomRecipe) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { 
                            recipeId?.let { 
                                recipeDetailViewModel.deleteRecipe(it) { 
                                    navController.popBackStack() 
                                } 
                            } 
                        }) {
                            Text("Delete")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Ingredients", style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)
            }
            recipe!!.ingredients?.let {
                items(it) {
                    it.original?.let { Text(it) }
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Instructions", style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)
            }
            recipe!!.instructions?.let {
                items(it.flatMap { it.steps ?: emptyList() }) {
                    Text("${it.number}. ${it.step}")
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Nutrition", style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)
            }
            recipe!!.nutrition?.nutrients?.let {
                items(it) {
                    Text("${it.name}: ${it.amount} ${it.unit}")
                }
            }
        }
    }
}
