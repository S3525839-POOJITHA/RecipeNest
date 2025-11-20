package uk.ac.tees.mad.s3525839.recipenest.data.remote.responses

import com.squareup.moshi.Json

data class RecipeSearchResponse(
    @Json(name = "results")
    val recipes: List<Recipe>
)

data class Recipe(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "image")
    val image: String
)
