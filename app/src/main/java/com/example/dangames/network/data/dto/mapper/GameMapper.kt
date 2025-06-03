package com.example.dangames.network.data.dto.mapper

import com.example.dangames.network.data.dto.BaseDto
import com.example.dangames.network.data.dto.Dto
import com.example.dangames.network.domain.model.BaseModel
import com.example.dangames.network.domain.model.Model

object GameMapper {
    fun<T : Dto, C : Model>BaseDto<T>.toModel(itemMapper: (T) -> C) =
        BaseModel(
            count = count,
            results = results.map(itemMapper)
        )

    fun Dto.toModel() : Model = when (this) {
        is Dto.Game -> toModel()
        is Dto.Platform -> toModel()
        is Dto.Esrb -> toModel()
        is Dto.Genre -> toModel()
        is Dto.Screenshot -> toModel()
        is Dto.Store -> toModel()
        is Dto.Tag -> toModel()
    }

    private fun Dto.Game.toModel() =
        Model.Game(
            id = id,
            name = name,
            description = description,
            released = released,
            tba = tba,
            backgroundImage = backgroundImage,
            rating = rating,
            metacritic = metacritic,
            platforms = platforms?.map { it.toModel() } ?: emptyList(),
            genres = genres?.map { it.toModel() } ?: emptyList(),
            stores = stores?.map { it.toModel() } ?: emptyList(),
            tags = tags?.map { it.toModel() } ?: emptyList(),
            esrb = esrb?.toModel(),
            gameSeriesCount = gameSeriesCount,
            additionsCount = additionsCount
        )

    private fun Dto.Genre.toModel() = Model.Genre(id, name)

    private fun Dto.Platform.toModel() = Model.Info(platform.id, platform.name)

    private fun Dto.Store.toModel() = Model.Info(store.id, store.name)

    private fun Dto.Tag.toModel() = Model.Info(id, name)

    private fun Dto.Esrb.toModel() = Model.Info(id, name)

    private fun Dto.Screenshot.toModel() = Model.Screenshot(id, image)
}