package com.example.dangames.db.data.repository

import com.example.dangames.db.data.mapper.FavoriteGameMapper.toEntity
import com.example.dangames.db.data.mapper.FavoriteGameMapper.toModel
import com.example.dangames.db.domain.dao.FavoritesDao
import com.example.dangames.db.domain.entity.FavoriteGameEntity
import com.example.dangames.db.domain.repository.FavoritesRepository
import com.example.dangames.network.domain.model.Model
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val dao: FavoritesDao,
) : FavoritesRepository {
    override suspend fun getFavorites(): Flow<List<Model.Game>> =
        dao.getFavorites().map { favorites -> favorites.map { it.toModel() } }

    override suspend fun isFavorite(id: Int): Flow<Boolean> =
        dao.isFavorite(id)

    override suspend fun insertFavorite(game: Model.Game) {
        dao.insertFavorite(game.toEntity())
    }

    override suspend fun deleteFavorite(game: Model.Game) {
        dao.deleteFavorite(game.toEntity())
    }
}