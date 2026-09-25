package com.example.cozybite.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {

        lateinit var database: AppDatabase

        fun initialize(context: Context) {
            database = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "cozybyte_database"
            ).build()
        }
    }
}