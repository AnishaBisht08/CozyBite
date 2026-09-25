package com.example.cozybite.data.model

data class UserRecipe(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val category: String = "",
    val ingredients: String = "",
    val instructions: String = ""
)