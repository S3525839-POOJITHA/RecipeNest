package uk.ac.tees.mad.s3525839.recipenest.data.remote.responses

import com.squareup.moshi.Json

data class RandomRecipesResponse(
    @Json(name = "recipes")
    val recipes: List<Recipe>
)
