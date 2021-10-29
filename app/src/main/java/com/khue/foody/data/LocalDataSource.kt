package com.khue.foody.data

import androidx.annotation.WorkerThread
import com.khue.foody.data.database.RecipesDao
import com.khue.foody.data.database.entities.FavoritesEntity
import com.khue.foody.data.database.entities.FoodJokeEntity
import com.khue.foody.data.database.entities.RecipesEntity
import com.khue.foody.models.FoodJoke
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    @WorkerThread
    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    @WorkerThread
    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>> {
       return recipesDao.readFavoriteRecipes()
    }

    @WorkerThread
    fun readFoodJoke(): Flow<List<FoodJokeEntity>> {
        return recipesDao.readFoodJoke()
    }

    @WorkerThread
    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    @WorkerThread
    suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity) {
        recipesDao.insertFavoriteRecipe(favoritesEntity)
    }

    @WorkerThread
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        recipesDao.insertFoodJoke(foodJokeEntity)
    }

    @WorkerThread
    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        recipesDao.deleteFavoriteRecipe(favoritesEntity)
    }

    @WorkerThread
    suspend fun deleteAllFavoriteRecipes() {
        recipesDao.deleteAllFavoriteRecipes()
    }
}