package com.example.dangames.db.data.mapper

import com.example.dangames.db.domain.entity.FavoriteGameEntity
import com.example.dangames.network.domain.model.Model

object FavoriteGameMapper {
    fun FavoriteGameEntity.toModel() =
        Model.Game(
            id = id,
            name = name,
            backgroundImage = backgroundImage,
            metacritic = metacritic,
            genres = genres,
        )

    fun Model.Game.toEntity() =
        FavoriteGameEntity(
            id = id,
            name = name,
            backgroundImage = backgroundImage ?: "",
            metacritic = metacritic,
            genres = genres
        )
}