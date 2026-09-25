package com.example.cozybite.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.cozybite.data.local.FavoriteEntity
import com.example.cozybite.ui.components.IngredientsSection
import com.example.cozybite.ui.theme.Text
import com.example.cozybite.viewmodel.MealViewModel

@Composable
fun DetailScreen(
    detailId: String,
    onBackClick: () -> Unit = {}
) {

    val viewModel: MealViewModel = viewModel()
    val context = LocalContext.current

    val meal by viewModel.mealDetail.collectAsState()
    var isFavorite by remember { mutableStateOf(false) }



    LaunchedEffect(detailId) {
        viewModel.getMealDetails(detailId)
        isFavorite = viewModel.isFavorite(detailId)
    }

    if (meal == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(28.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        return
    }

    val recipe = meal!!

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                AsyncImage(
                    model = recipe.strMealThumb,
                    contentDescription = recipe.strMeal,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Black.copy(alpha = 0.5f),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }

                    IconButton(
                        onClick = {

                            val favorite = FavoriteEntity(
                                id = recipe.idMeal,
                                name = recipe.strMeal,
                                image = recipe.strMealThumb ?: ""
                            )

                            if (isFavorite) {
                                viewModel.removeFavorite(favorite)
                            } else {
                                viewModel.addFavorite(favorite)
                            }

                            isFavorite = !isFavorite
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Black.copy(alpha = 0.5f),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = if (isFavorite)
                                Icons.Default.Favorite
                            else
                                Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite"
                        )
                    }
                }
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(
                    text = recipe.strMeal,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Text
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    recipe.strCategory?.let {
                        Text(
                            text = it,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    recipe.strArea?.let {
                        Text(
                            text = "• $it",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        item {

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Text(
                text = "🥗 Ingredients",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )
        }

        item {

            IngredientsSection(recipe)
        }


        item {

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            Text(
                text = "👩‍🍳 Instructions",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = recipe.strInstructions ?: "No instructions available.",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 15.sp,
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }


        item {

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Button(
                    onClick = {
                        recipe.strYoutube?.let { url ->
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                url.toUri()
                            )
                            context.startActivity(intent)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null
                    )

                    Spacer(
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )

                    Text("Watch Recipe Video")
                }

        }

        item {
            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }
}