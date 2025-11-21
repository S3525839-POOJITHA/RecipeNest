package uk.ac.tees.mad.s3525839.recipenest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uk.ac.tees.mad.s3525839.recipenest.model.FavoriteRecipe

@Dao
interface FavoriteRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteRecipe: FavoriteRecipe)

    @Query("SELECT * FROM favorite_recipes")
    suspend fun getAll(): List<FavoriteRecipe>

    @Query("SELECT * FROM favorite_recipes WHERE id = :id")
    suspend fun getById(id: Int): FavoriteRecipe?

    @Query("DELETE FROM favorite_recipes WHERE id = :id")
    suspend fun deleteById(id: Int)
}
