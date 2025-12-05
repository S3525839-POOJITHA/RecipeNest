package uk.ac.tees.mad.s3525839.recipenest.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uk.ac.tees.mad.s3525839.recipenest.data.local.RecipeDatabase
import uk.ac.tees.mad.s3525839.recipenest.model.Recipe
import java.io.File
import java.io.FileOutputStream

class AddRecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val recipeDao = RecipeDatabase.getDatabase(application).recipeDao()

    fun addRecipe(title: String, ingredients: String, instructions: String, imageUri: Uri?, onRecipeAdded: () -> Unit) {
        viewModelScope.launch {
            val imagePath = imageUri?.let { saveImageToInternalStorage(it) }
            val newRecipe = Recipe(
                title = title,
                image = imagePath ?: "",
                ingredients = ingredients,
                instructions = instructions,
                isCustom = true
            )
            recipeDao.insertRecipe(newRecipe)
            onRecipeAdded()
        }
    }

    private fun saveImageToInternalStorage(uri: Uri): String {
        val context = getApplication<Application>().applicationContext
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.filesDir, "${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        return file.absolutePath
    }
}
