package com.example.cozybite.data.repository

import com.example.cozybite.data.model.Category
import com.example.cozybite.data.model.Meal
import com.example.cozybite.data.remote.MealApi

class MealRepository(
    private val api: MealApi
) {

    suspend fun getRandomMeal(): Meal?{
        return api.getRandomMeal().meals?.firstOrNull()
    }

     suspend fun getCategories(): List<Category>{
        return api.getCategories().categories
    }


    suspend fun searchMeals(query: String): List<Meal> {
        return api.searchMeals(query = query).meals ?: emptyList()
    }

    suspend fun getMealDetails(id: String): Meal? {
        return api.getMealDetails(id)
            .meals
            ?.firstOrNull()
    }



}