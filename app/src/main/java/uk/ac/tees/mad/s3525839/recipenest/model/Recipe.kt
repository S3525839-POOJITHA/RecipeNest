package uk.ac.tees.mad.s3525839.recipenest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val image: String,
    val ingredients: String? = null,
    val instructions: String? = null,
    val isCustom: Boolean = false
)
