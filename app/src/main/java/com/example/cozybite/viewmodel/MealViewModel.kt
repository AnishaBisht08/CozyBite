package com.example.cozybite.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cozybite.data.local.FavoriteEntity
import com.example.cozybite.data.model.Category
import com.example.cozybite.data.model.Meal
import com.example.cozybite.data.model.UserRecipe
import com.example.cozybite.data.remote.RetrofitApi
import com.example.cozybite.data.repository.FavoriteRepository
import com.example.cozybite.data.repository.MealRepository
import com.example.cozybite.data.repository.UserRecipeRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MealViewModel : ViewModel() {

    private val repository = MealRepository(api = RetrofitApi.getApi)
    private val favoriteRepository = FavoriteRepository()
    private val userRecipeRepository = UserRecipeRepository()

    private val _randomMeals = MutableStateFlow<List<Meal>>(emptyList())
    val randomMeals: StateFlow<List<Meal>> = _randomMeals

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories


    private val _searchResult = MutableStateFlow<List<Meal>>(emptyList())
    val searchResult: StateFlow<List<Meal>> = _searchResult


    private val _mealDetail = MutableStateFlow<Meal?>(null)
    val mealDetail: StateFlow<Meal?> = _mealDetail

    private val _userRecipes = MutableStateFlow<List<UserRecipe>>(emptyList())
    val userRecipes: StateFlow<List<UserRecipe>> = _userRecipes

    fun getUserRecipes() {
        userRecipeRepository.getUserRecipes { recipes ->
            _userRecipes.value = recipes
        }
    }

    fun addUserRecipe(
        name: String,
        imageUrl: String,
        category: String,
        ingredients: String,
        instructions: String
    ) {

        val userId = FirebaseAuth.getInstance()
            .currentUser?.uid ?: return

        val recipe = UserRecipe(
            userId = userId,
            name = name,
            imageUrl = imageUrl,
            category = category,
            ingredients = ingredients,
            instructions = instructions
        )

        userRecipeRepository.addRecipe(recipe) { success ->

            if (success) {
                println("Recipe added")
            } else {
                println("Failed to add recipe")
            }
        }
    }


    val favorites = favoriteRepository.favorites

    fun addFavorite(recipe: FavoriteEntity) {
        viewModelScope.launch {
            favoriteRepository.addFavorite(recipe)
        }
    }

    fun removeFavorite(recipe: FavoriteEntity) {
        viewModelScope.launch {
            favoriteRepository.removeFavorite(recipe)
        }
    }

    suspend fun isFavorite(id: String): Boolean {
        return favoriteRepository.isFavorite(id)

    }


    fun getMealDetails(id: String) {
        viewModelScope.launch {
            try {
                val response = repository.getMealDetails(id)
                _mealDetail.value = response
            } catch (e: Exception) {
                Log.e("MEAL", "Error: ${e.message}")
            }
        }
    }


    fun getRandomMeals() {

        viewModelScope.launch {
            val meals = mutableListOf<Meal>()


            repeat(10) {
                repository.getRandomMeal()?.let {
                    meals.add(it)
                }
            }

            _randomMeals.value = meals

        }
    }


    fun getCategories() {

        viewModelScope.launch {

            _categories.value = repository.getCategories()

        }
    }


    fun searchMeals(query: String) {
        viewModelScope.launch {
            _searchResult.value = repository.searchMeals(query)
        }
    }


}