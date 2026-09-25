package com.example.cozybite.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cozybite.ui.components.FavoriteRecipeCard
import com.example.cozybite.viewmodel.MealViewModel

@Composable
fun FavoriteScreen(
    onRecipeClick: (String) -> Unit
) {

    val viewModel: MealViewModel = viewModel()

    val favorites by viewModel.favorites.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(favorites) { recipe ->

            FavoriteRecipeCard(
                recipe = recipe,
                onClick = {
                    onRecipeClick(recipe.id)
                },
                onRemove = {
                    viewModel.removeFavorite(recipe)
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

}