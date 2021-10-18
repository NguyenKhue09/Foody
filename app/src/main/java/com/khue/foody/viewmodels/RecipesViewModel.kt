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
import com.khue.foody.util.Constants.Companion.QUERY_NUMBER
import com.khue.foody.util.Constants.Companion.QUERY_SEARCH
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


    private lateinit var mealAnDietType: MealAnDietType

    var networkStatus = false
    var backOnline = false

    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    var readBackOnline = dataStoreRepository.readBackOnline

    fun saveMealAndDietType() =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAnDietType(
                mealAnDietType.selectedMealType,
                mealAnDietType.selectedMealTypeId,
                mealAnDietType.selectedDietType,
                mealAnDietType.selectedDietTypeId)
        }

    fun saveMealAndDietTypeTemp(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        mealAnDietType = MealAnDietType(
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
        queries[Constants.QUERY_TYPE] = mealAnDietType.selectedMealType
        queries[Constants.QUERY_DIET] = mealAnDietType.selectedDietType
        queries[QUERY_ADD_RECIPES_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"
        queries[Constants.QUERY_ADD_RECIPE_NUTRITION] = "true"

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