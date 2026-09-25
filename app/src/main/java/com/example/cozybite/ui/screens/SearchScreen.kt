package com.example.cozybite.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cozybite.ui.components.SearchMealItem
import com.example.cozybite.ui.theme.SecondaryText
import com.example.cozybite.viewmodel.MealViewModel

@Composable
fun SearchScreen(onRecipeClick: (String) -> Unit) {
    val viewModel: MealViewModel = viewModel()
    val searchResults by viewModel.searchResult.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))


        OutlinedTextField(
            value =
                searchQuery,
            onValueChange = {
                searchQuery = it
            },
            modifier =
                Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("Search recipes...") },
            leadingIcon = {

                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                if (searchQuery.isNotBlank()) {
                    viewModel.searchMeals(searchQuery.trim())
                }
            }),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                cursorColor = MaterialTheme.colorScheme.secondary,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedLeadingIconColor = SecondaryText
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (searchQuery.isBlank()) {

            Text(
                textAlign = TextAlign.Center,
                text = "Search for your favorite recipe",
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                color = SecondaryText
            )
        }

        else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(searchResults) { meal ->
                    SearchMealItem(
                        meal = meal,
                        onClick = { onRecipeClick(meal.idMeal) })
                }
            }
        }
    }
}