package com.khue.foody.data

import com.khue.foody.data.network.FoodRecipesApi
import com.khue.foody.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject


//https://developer.android.com/training/dependency-injection/hilt-android#define-bindings
class RemoteDataSource @Inject constructor(
    // lấy hàm provideApiService từ network module
    // dựa trên cùng kiểu dữ liệu trả vể
    private val foodRecipesApi: FoodRecipesApi
) {

   suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }
}
