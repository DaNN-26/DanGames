package com.example.dangames.db.core

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dangames.db.data.converters.FavoritesConverters
import com.example.dangames.db.domain.dao.FavoritesDao
import com.example.dangames.db.domain.entity.FavoriteGameEntity

@Database(
    entities = [
        FavoriteGameEntity::class,
    ],
    version = 1
)
@TypeConverters(FavoritesConverters::class)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun dao(): FavoritesDao
}