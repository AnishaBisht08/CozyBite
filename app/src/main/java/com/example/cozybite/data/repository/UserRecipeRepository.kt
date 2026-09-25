package com.example.cozybite.data.repository

import com.example.cozybite.data.model.UserRecipe
import com.google.firebase.firestore.FirebaseFirestore

class UserRecipeRepository {

    private val db = FirebaseFirestore.getInstance()

    fun addRecipe(recipe: UserRecipe, onResult: (Boolean) -> Unit) {

        val id = db.collection("recipes").document().id

        val newRecipe = recipe.copy(
            id = id
        )

        db.collection("recipes")
            .document(id)
            .set(newRecipe)
            .addOnSuccessListener {
                onResult(true)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }


    fun getUserRecipes(
        onResult: (List<UserRecipe>) -> Unit
    ) {
        db.collection("recipes")
            .get()
            .addOnSuccessListener { result ->

                val recipes = result.documents.mapNotNull {
                    it.toObject(UserRecipe::class.java)
                }

                onResult(recipes)
            }
    }
}