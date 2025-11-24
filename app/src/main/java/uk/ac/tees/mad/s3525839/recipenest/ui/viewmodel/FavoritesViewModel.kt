package uk.ac.tees.mad.s3525839.recipenest.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import uk.ac.tees.mad.s3525839.recipenest.data.local.RecipeDatabase
import uk.ac.tees.mad.s3525839.recipenest.model.FavoriteRecipe

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteRecipeDao = RecipeDatabase.getDatabase(application).favoriteRecipeDao()

    val favoriteRecipes: StateFlow<List<FavoriteRecipe>> = favoriteRecipeDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
