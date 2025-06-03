package com.example.dangames.db.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.dangames.db.domain.entity.FavoriteGameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorite_game ORDER BY name")
    fun getFavorites(): Flow<List<FavoriteGameEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_game WHERE id = :id)")
    fun isFavorite(id: Int): Flow<Boolean>

    @Insert
    suspend fun insertFavorite(favoriteGameEntity: FavoriteGameEntity)

    @Delete
    suspend fun deleteFavorite(favoriteGameEntity: FavoriteGameEntity)
}