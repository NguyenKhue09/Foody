package com.khue.foody.data.database

import androidx.room.*
import com.khue.foody.data.database.entities.FavoritesEntity
import com.khue.foody.data.database.entities.FoodJokeEntity
import com.khue.foody.data.database.entities.RecipesEntity


@Database(
    entities = [RecipesEntity::class, FavoritesEntity::class, FoodJokeEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract  class RecipesDatabase: RoomDatabase() {

    abstract fun recipesDao() : RecipesDao

//    companion object {
//
//        @Volatile
//        private  var INSTANCE: RecipesDatabase? =  null
//
//        fun getDatabase(context: Context): RecipesDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    RecipesDatabase::class.java,
//                    "recipes_database"
//                ).fallbackToDestructiveMigration().build()
//
//                INSTANCE = instance
//
//                instance
//            }
//        }
//    }

}