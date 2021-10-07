package com.khue.foody.data.database

import androidx.room.*
import com.khue.foody.data.database.entities.FavoritesEntity
import com.khue.foody.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity)

    /** When data changes, you usually want to take some action, such as displaying the updated data in the UI.
    *This means you have to observe the data so when it changes, you can react.
    *To observe data changes you will use Flow from kotlinx-coroutines.
    *Use a return value of type Flow in your method description, and Room
    *generates all necessary code to update the Flow when the database is updated.*/
    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Query("SELECT * FROM favorites_recipes_table ORDER BY id ASC")
    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>>

    @Delete
    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM favorites_recipes_table")
    suspend fun deleteAllFavoriteRecipes()

}