package com.khue.foody.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khue.foody.models.Result
import com.khue.foody.util.Constants.Companion.FAVORITES_RECIPES_TABLE


@Entity(tableName = FAVORITES_RECIPES_TABLE)
data class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)