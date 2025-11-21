package uk.ac.tees.mad.s3525839.recipenest.data.remote.responses

import com.squareup.moshi.Json

data class RecipeInformationResponse(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "image") val image: String? = null,
    @Json(name = "extendedIngredients") val ingredients: List<Ingredient>? = null,
    @Json(name = "analyzedInstructions") val instructions: List<Instruction>? = null,
    @Json(name = "nutrition") val nutrition: Nutrition? = null
)

data class Ingredient(
    @Json(name = "original") val original: String? = null
)

data class Instruction(
    @Json(name = "name") val name: String? = null,
    @Json(name = "steps") val steps: List<Step>? = null
)

data class Step(
    @Json(name = "number") val number: Int? = null,
    @Json(name = "step") val step: String? = null
)

data class Nutrition(
    @Json(name = "nutrients") val nutrients: List<Nutrient>? = null
)

data class Nutrient(
    @Json(name = "name") val name: String? = null,
    @Json(name = "amount") val amount: Double? = null,
    @Json(name = "unit") val unit: String? = null
)
