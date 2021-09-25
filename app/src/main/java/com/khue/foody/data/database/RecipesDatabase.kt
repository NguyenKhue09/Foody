package com.khue.foody.data.database

import android.content.Context
import androidx.room.*


@Database(entities = [RecipesEntity::class], version = 1, exportSchema = false)
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