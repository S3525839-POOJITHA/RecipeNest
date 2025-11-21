package uk.ac.tees.mad.s3525839.recipenest.data

import uk.ac.tees.mad.s3525839.recipenest.data.local.RecipeDao
import uk.ac.tees.mad.s3525839.recipenest.data.remote.SpoonacularApiService
import uk.ac.tees.mad.s3525839.recipenest.data.remote.responses.RecipeInformationResponse
import uk.ac.tees.mad.s3525839.recipenest.model.Recipe

class RecipeRepository(
    private val recipeDao: RecipeDao,
    private val spoonacularApiService: SpoonacularApiService,
    private val apiKey: String
) {

    suspend fun searchRecipes(query: String): List<Recipe> {
        val recipes = spoonacularApiService.searchRecipes(query, apiKey).recipes.map { 
            Recipe(it.id!!, it.title!!, it.image!!)
        }
        if (recipes.isNotEmpty()) {
            recipeDao.insertRecipes(recipes)
        }
        return recipes
    }

    suspend fun getRandomRecipes(): List<Recipe> {
        val recipes = spoonacularApiService.getRandomRecipes(apiKey).recipes.map { 
            Recipe(it.id!!, it.title!!, it.image!!)
        }
        if (recipes.isNotEmpty()) {
            recipeDao.insertRecipes(recipes)
        }
        return recipes
    }

    suspend fun getRecipeInformation(id: Int): RecipeInformationResponse {
        return spoonacularApiService.getRecipeInformation(id, apiKey)
    }
}
