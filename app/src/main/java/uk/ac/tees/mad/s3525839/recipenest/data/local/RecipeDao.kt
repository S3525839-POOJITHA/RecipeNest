package uk.ac.tees.mad.s3525839.recipenest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uk.ac.tees.mad.s3525839.recipenest.model.Recipe

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)

    @Query("SELECT * FROM recipes WHERE title LIKE :query")
    suspend fun searchRecipes(query: String): List<Recipe>

    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<Recipe>
}
