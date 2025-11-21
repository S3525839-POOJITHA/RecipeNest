package uk.ac.tees.mad.s3525839.recipenest.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uk.ac.tees.mad.s3525839.recipenest.data.remote.responses.RandomRecipesResponse
import uk.ac.tees.mad.s3525839.recipenest.data.remote.responses.RecipeInformationResponse
import uk.ac.tees.mad.s3525839.recipenest.data.remote.responses.RecipeSearchResponse

interface SpoonacularApiService {
    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String
    ): RecipeSearchResponse

    @GET("recipes/{id}/information")
    suspend fun getRecipeInformation(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String,
        @Query("includeNutrition") includeNutrition: Boolean = true
    ): RecipeInformationResponse

    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int = 20
    ): RandomRecipesResponse
}
