package uk.ac.tees.mad.s3525839.recipenest.ui.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import uk.ac.tees.mad.s3525839.recipenest.BuildConfig
import uk.ac.tees.mad.s3525839.recipenest.data.RecipeRepository
import uk.ac.tees.mad.s3525839.recipenest.data.local.RecipeDatabase
import uk.ac.tees.mad.s3525839.recipenest.data.remote.SpoonacularApiService
import uk.ac.tees.mad.s3525839.recipenest.data.remote.responses.Ingredient
import uk.ac.tees.mad.s3525839.recipenest.data.remote.responses.Instruction
import uk.ac.tees.mad.s3525839.recipenest.data.remote.responses.RecipeInformationResponse
import uk.ac.tees.mad.s3525839.recipenest.data.remote.responses.Step
import uk.ac.tees.mad.s3525839.recipenest.model.FavoriteRecipe

class RecipeDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteRecipeDao = RecipeDatabase.getDatabase(application).favoriteRecipeDao()
    private val recipeRepository: RecipeRepository

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    private val _isCustomRecipe = MutableStateFlow(false)
    val isCustomRecipe: StateFlow<Boolean> = _isCustomRecipe

    private val _recipe = MutableStateFlow<RecipeInformationResponse?>(null)
    val recipe: StateFlow<RecipeInformationResponse?> = _recipe

    init {
        val apiKey = BuildConfig.API_KEY
        val recipeDao = RecipeDatabase.getDatabase(application).recipeDao()
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        val spoonacularApiService = retrofit.create(SpoonacularApiService::class.java)
        recipeRepository = RecipeRepository(recipeDao, spoonacularApiService, apiKey)
    }

    fun getRecipeDetails(recipeId: Int) {
        viewModelScope.launch {
            try {
                val localRecipe = recipeRepository.getRecipeById(recipeId)
                if (localRecipe != null && localRecipe.isCustom) {
                    _isCustomRecipe.value = true
                    _recipe.value = RecipeInformationResponse(
                        id = localRecipe.id,
                        title = localRecipe.title,
                        image = localRecipe.image,
                        ingredients = localRecipe.ingredients?.split("\n")?.map { Ingredient(it) },
                        instructions = localRecipe.instructions?.let { listOf(Instruction(steps = it.split("\n").mapIndexed { index, step -> Step(index + 1, step) })) }
                    )
                } else {
                    _isCustomRecipe.value = false
                    _recipe.value = recipeRepository.getRecipeInformation(recipeId)
                }
                isFavorite(recipeId)
            } catch (e: Exception) {
                Toast.makeText(getApplication(), "Failed to fetch recipe details: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun deleteRecipe(recipeId: Int, onRecipeDeleted: () -> Unit) {
        viewModelScope.launch {
            recipeRepository.deleteRecipeById(recipeId)
            onRecipeDeleted()
        }
    }

    private fun isFavorite(recipeId: Int) {
        viewModelScope.launch {
            _isFavorite.value = favoriteRecipeDao.getById(recipeId) != null
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            recipe.value?.let { recipeInfo ->
                recipeInfo.id?.let { recipeId ->
                    if (_isFavorite.value) {
                        favoriteRecipeDao.deleteById(recipeId)
                        _isFavorite.value = false
                    } else {
                        val title = recipeInfo.title
                        val image = recipeInfo.image
                        if (title != null && image != null) {
                            favoriteRecipeDao.insert(FavoriteRecipe(recipeId, title, image))
                            _isFavorite.value = true
                        } else {
                            Toast.makeText(getApplication(), "Cannot save recipe with missing information.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}
