package com.example.cozybite.data.repository

import com.example.cozybite.data.local.AppDatabase
import com.example.cozybite.data.local.FavoriteEntity


class FavoriteRepository {

    private val favoriteDao = AppDatabase.database.favoriteDao()

    val favorites = favoriteDao.getFavorites()

    suspend fun addFavorite(recipe: FavoriteEntity) {
        favoriteDao.addFavorite(recipe)
    }

    suspend fun removeFavorite(recipe: FavoriteEntity) {
        favoriteDao.removeFavorite(recipe)
    }

    suspend fun isFavorite(id: String): Boolean{
        return favoriteDao.isFavorite(id)
    }

}