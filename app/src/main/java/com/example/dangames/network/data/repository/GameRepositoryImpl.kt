package com.example.dangames.network.data.repository

import com.example.dangames.network.core.ApiClient
import com.example.dangames.network.data.dto.BaseDto
import com.example.dangames.network.data.dto.Dto
import com.example.dangames.network.data.dto.mapper.GameMapper.toModel
import com.example.dangames.network.domain.model.BaseModel
import com.example.dangames.network.domain.model.Model
import com.example.dangames.network.domain.repository.GameRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class GameRepositoryImpl(
    private val client: HttpClient,
) : GameRepository {
    override suspend fun getHomeData(): Result<List<Model.Game>> {
        return try {
            val path = ApiClient.BASE_URL + "games?key=" + ApiClient.API_KEY + "&page_size=8"
            val games = client.get(path)
                .body<BaseDto<Dto.Game>>()
                .toModel { it.toModel() as Model.Game }
            Result.success(games.results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getGenres(): Result<List<Model.Genre>> {
        return try {
            val path = ApiClient.BASE_URL + "genres?key=" + ApiClient.API_KEY
            val genres = client.get(path)
                .body<BaseDto<Dto.Genre>>()
                .toModel { it.toModel() as Model.Genre }
                .results
            Result.success(genres)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getGameById(id: Int): Result<Model.Game> {
        return try {
            val gamePath = ApiClient.BASE_URL + "games/$id?key=" + ApiClient.API_KEY
            val screenshotsPath =
                ApiClient.BASE_URL + "games/$id/screenshots?key=" + ApiClient.API_KEY
            val game = client.get(gamePath)
                .body<Dto.Game>()
                .toModel() as Model.Game
            val screenshots = client.get(screenshotsPath)
                .body<BaseDto<Dto.Screenshot>>()
                .toModel { it.toModel() as Model.Screenshot }
                .results
            Result.success(
                game.copy(shortScreenshots = screenshots)
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getGameAdditions(id: Int): Result<List<Model.Game>> {
        return try {
            val path = ApiClient.BASE_URL + "games/$id/additions?key=" + ApiClient.API_KEY
            val additions = client.get(path)
                .body<BaseDto<Dto.Game>>()
                .toModel { it.toModel() as Model.Game }
                .results
            Result.success(additions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getGameSeries(id: Int): Result<BaseModel<Model.Game>> {
        return try {
            val path = ApiClient.BASE_URL + "games/$id/game-series?key=" + ApiClient.API_KEY
            val games = client.get(path)
                .body<BaseDto<Dto.Game>>()
                .toModel { it.toModel() as Model.Game }
            Result.success(games)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getGames(
        query: String,
        genres: List<Int>,
        page: Int,
    ): Result<BaseModel<Model.Game>> {
        return try {
            val path = StringBuilder()
                .append(ApiClient.BASE_URL)
                .append("games?key=" + ApiClient.API_KEY)
                .append(if (query.isNotEmpty()) "&search=$query" else "")
                .append(if (genres.isNotEmpty()) "&genres=${genres.joinToString(", ") { it.toString() }}" else "")
                .append(if (page != 0) "&page=$page" else "")
                .append("&page_size=20")
            val games = client.get(path.toString())
                .body<BaseDto<Dto.Game>>()
                .toModel { it.toModel() as Model.Game }
            Result.success(games)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}