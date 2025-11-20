package uk.ac.tees.mad.s3525839.recipenest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey
    val id: Int,
    val title: String,
    val image: String
)
