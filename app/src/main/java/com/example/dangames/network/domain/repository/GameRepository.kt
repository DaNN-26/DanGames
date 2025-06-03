package com.example.dangames.network.domain.repository

import com.example.dangames.network.domain.model.BaseModel
import com.example.dangames.network.domain.model.Model

interface GameRepository {
    suspend fun getHomeData(): Result<List<Model.Game>>
    suspend fun getGenres(): Result<List<Model.Genre>>
    suspend fun getGameById(id: Int): Result<Model.Game>
    suspend fun getGameAdditions(id: Int): Result<List<Model.Game>>
    suspend fun getGameSeries(id: Int): Result<BaseModel<Model.Game>>
    suspend fun getGames(
        query: String = "",
        genres: List<Int> = emptyList(),
        page: Int = 0
    ): Result<BaseModel<Model.Game>>
}