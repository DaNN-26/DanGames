package com.example.dangames.db.domain.repository

import com.example.dangames.network.domain.model.Model
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun getFavorites(): Flow<List<Model.Game>>
    suspend fun isFavorite(id: Int): Flow<Boolean>
    suspend fun insertFavorite(game: Model.Game)
    suspend fun deleteFavorite(game: Model.Game)
}