package uk.ac.tees.mad.s3525839.recipenest.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.s3525839.recipenest.data.local.RecipeDatabase
import uk.ac.tees.mad.s3525839.recipenest.model.FavoriteRecipe

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteRecipeDao = RecipeDatabase.getDatabase(application).favoriteRecipeDao()

    private val _favoriteRecipes = MutableStateFlow<List<FavoriteRecipe>>(emptyList())
    val favoriteRecipes: StateFlow<List<FavoriteRecipe>> = _favoriteRecipes

    init {
        viewModelScope.launch {
            _favoriteRecipes.value = favoriteRecipeDao.getAll()
        }
    }
}
