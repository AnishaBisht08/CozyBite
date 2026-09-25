package com.example.cozybite.data.remote

import com.example.cozybite.data.model.CategoryResponse
import com.example.cozybite.data.model.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface MealApi {

    @GET("random.php")
    suspend fun getRandomMeal(): MealResponse

    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse


    @GET("search.php")
    suspend fun searchMeals(
        @Query("s") query: String
    ): MealResponse


    @GET("lookup.php")
    suspend fun getMealDetails(
        @Query("i") id: String
    ): MealResponse

}