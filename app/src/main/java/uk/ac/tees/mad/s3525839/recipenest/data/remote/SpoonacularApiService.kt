package uk.ac.tees.mad.s3525839.recipenest.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import uk.ac.tees.mad.s3525839.recipenest.data.remote.responses.RecipeSearchResponse

interface SpoonacularApiService {
    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String
    ): RecipeSearchResponse
}
