package com.khue.foody.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.khue.foody.data.DataStoreRepository
import com.khue.foody.data.MealAnDietType
import com.khue.foody.util.Constants
import com.khue.foody.util.Constants.Companion.API_KEY
import com.khue.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.khue.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.khue.foody.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.khue.foody.util.Constants.Companion.QUERY_ADD_RECIPES_INFORMATION
import com.khue.foody.util.Constants.Companion.QUERY_API_KEY
import com.khue.foody.util.Constants.Companion.QUERY_DIET
import com.khue.foody.util.Constants.Companion.QUERY_NUMBER
import com.khue.foody.util.Constants.Companion.QUERY_SEARCH
import com.khue.foody.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {


    private lateinit var mealAndDietType: MealAnDietType

    var networkStatus = false
    var backOnline = false

    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    var readBackOnline = dataStoreRepository.readBackOnline

    fun saveMealAndDietType() =
        viewModelScope.launch(Dispatchers.IO) {
            if (this@RecipesViewModel::mealAndDietType.isInitialized) {
                dataStoreRepository.saveMealAnDietType(
                    mealAndDietType.selectedMealType,
                    mealAndDietType.selectedMealTypeId,
                    mealAndDietType.selectedDietType,
                    mealAndDietType.selectedDietTypeId
                )
            }
        }

    fun saveMealAndDietTypeTemp(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        mealAndDietType = MealAnDietType(
            mealType,
            mealTypeId,
            dietType,
            dietTypeId
        )
    }

    private fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPES_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"
        queries[Constants.QUERY_ADD_RECIPE_NUTRITION] = "true"

        if (this@RecipesViewModel::mealAndDietType.isInitialized) {
            queries[QUERY_TYPE] = mealAndDietType.selectedMealType
            queries[QUERY_DIET] = mealAndDietType.selectedDietType
        } else {
            queries[QUERY_TYPE] = DEFAULT_MEAL_TYPE
            queries[QUERY_DIET] = DEFAULT_DIET_TYPE
        }

        return queries
    }

    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPES_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"
        queries[Constants.QUERY_ADD_RECIPE_NUTRITION] = "true"

        return queries
    }

    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (networkStatus) {
            if (backOnline) {
                Toast.makeText(getApplication(), "We're back online", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }
}