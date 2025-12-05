package uk.ac.tees.mad.s3525839.recipenest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import uk.ac.tees.mad.s3525839.recipenest.data.RecipeRepository
import uk.ac.tees.mad.s3525839.recipenest.data.local.RecipeDatabase
import uk.ac.tees.mad.s3525839.recipenest.data.remote.SpoonacularApiService
import uk.ac.tees.mad.s3525839.recipenest.model.Recipe

class MyRecipesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RecipeRepository

    init {
        val recipeDao = RecipeDatabase.getDatabase(application).recipeDao()
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        val spoonacularApiService = retrofit.create(SpoonacularApiService::class.java)
        repository = RecipeRepository(recipeDao, spoonacularApiService, "") // API key not needed for custom recipes
    }

    val myRecipes: StateFlow<List<Recipe>> = repository.getCustomRecipes()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
