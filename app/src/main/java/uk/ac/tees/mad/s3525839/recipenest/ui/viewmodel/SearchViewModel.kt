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
import uk.ac.tees.mad.s3525839.recipenest.model.Recipe

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val recipeRepository: RecipeRepository

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

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

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            try {
                _recipes.value = recipeRepository.searchRecipes(query)
            } catch (e: Exception) {
                Toast.makeText(getApplication(), "Failed to fetch recipes: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
