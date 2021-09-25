package com.khue.foody.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khue.foody.models.FoodRecipe
import com.khue.foody.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
data class RecipesEntity(
    val foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}