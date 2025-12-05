package uk.ac.tees.mad.s3525839.recipenest.data

import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.s3525839.recipenest.data.local.RecipeDao
import uk.ac.tees.mad.s3525839.recipenest.data.remote.SpoonacularApiService
import uk.ac.tees.mad.s3525839.recipenest.data.remote.responses.RecipeInformationResponse
import uk.ac.tees.mad.s3525839.recipenest.model.Recipe

class RecipeRepository(
    private val recipeDao: RecipeDao,
    private val spoonacularApiService: SpoonacularApiService,
    private val apiKey: String
) {

    fun getCustomRecipes(): Flow<List<Recipe>> {
        return recipeDao.getCustomRecipes()
    }

    suspend fun getRecipeById(id: Int): Recipe? {
        return recipeDao.getRecipeById(id)
    }

    suspend fun deleteRecipeById(id: Int) {
        recipeDao.deleteRecipeById(id)
    }

    suspend fun searchRecipes(query: String): List<Recipe> {
        val recipes = spoonacularApiService.searchRecipes(query, apiKey).recipes.map { 
            Recipe(it.id!!, it.title!!, it.image!!)
        }
        if (recipes.isNotEmpty()) {
            recipeDao.insertAll(recipes)
        }
        return recipes
    }

    suspend fun getRandomRecipes(): List<Recipe> {
        val recipes = spoonacularApiService.getRandomRecipes(apiKey).recipes.map { 
            Recipe(it.id!!, it.title!!, it.image!!)
        }
        if (recipes.isNotEmpty()) {
            recipeDao.insertAll(recipes)
        }
        return recipes
    }

    suspend fun getRecipeInformation(id: Int): RecipeInformationResponse {
        return spoonacularApiService.getRecipeInformation(id, apiKey)
    }
}
