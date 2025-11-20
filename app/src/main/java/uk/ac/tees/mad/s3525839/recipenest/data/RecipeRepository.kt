package uk.ac.tees.mad.s3525839.recipenest.data

import uk.ac.tees.mad.s3525839.recipenest.data.local.RecipeDao
import uk.ac.tees.mad.s3525839.recipenest.data.remote.SpoonacularApiService
import uk.ac.tees.mad.s3525839.recipenest.model.Recipe

class RecipeRepository(
    private val recipeDao: RecipeDao,
    private val spoonacularApiService: SpoonacularApiService,
    private val apiKey: String
) {

    suspend fun searchRecipes(query: String): List<Recipe> {
        return try {
            val recipes = spoonacularApiService.searchRecipes(query, apiKey).recipes.map { 
                Recipe(it.id, it.title, it.image)
            }
            if (recipes.isNotEmpty()) {
                recipeDao.insertRecipes(recipes)
            }
            recipes
        } catch (e: Exception) {
            recipeDao.searchRecipes("%${query}%")
        }
    }

    suspend fun getAllRecipes(): List<Recipe> {
        return recipeDao.getAllRecipes()
    }
}
