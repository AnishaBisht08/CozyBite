package com.example.cozybite.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cozybite.ui.components.CategoryItem
import com.example.cozybite.ui.components.MealItem
import com.example.cozybite.ui.theme.DarkBrown
import com.example.cozybite.ui.theme.Text
import com.example.cozybite.viewmodel.MealViewModel

@Composable
fun HomeScreen(
    onSearchClick: () -> Unit,
    onRecipeClick: (String) -> Unit,
) {

    val viewModel: MealViewModel = viewModel()
    val randomMeals by viewModel.randomMeals.collectAsState()
    val categories by viewModel.categories.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.getRandomMeals()
        viewModel.getCategories()
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {


        item {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CozyBite",
                    style = MaterialTheme.typography.headlineSmall,
                    color = DarkBrown,
                    fontWeight = FontWeight.Bold
                )

                IconButton(onClick = { onSearchClick() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search recipes",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "What do you want to eat today.🤤",
                style = MaterialTheme.typography.titleMedium,
                color = Text,
                fontWeight = FontWeight.SemiBold
            )
        }


        item {
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Categories",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Text
                )

                Text(
                    text = "See all",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(categories.take(10)) { category ->
                    CategoryItem(category = category)
                }
            }
        }


        item {
            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Popular Recipes",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Text
                )

                Text(
                    text = "See all",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }


        val chunkedMeals = randomMeals.chunked(2)
        itemsIndexed(chunkedMeals) { _, recipePair ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                recipePair.forEach { meal ->
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        MealItem(
                            meal = meal,
                            onClick = { onRecipeClick(meal.idMeal) }
                        )
                    }
                }
                if (recipePair.size == 1) {
                   Box(modifier = Modifier.weight(1f)) {}
                }
            }
        }

    }
}