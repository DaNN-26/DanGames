package com.example.dangames.network.domain.model

sealed class Model {
    data class Game(
        val id: Int = 0,
        val name: String,
        val description: String = "",
        val isFavorite: Boolean = false,
        val released: String? = null,
        val tba: Boolean = false,
        val backgroundImage: String?,
        val rating: Double = 0.0,
        val metacritic: Int? = 0,
        val platforms: List<Info> = emptyList(),
        val genres: List<Genre> = emptyList(),
        val stores: List<Info> = emptyList(),
        val tags: List<Info> = emptyList(),
        val esrb: Info? = null,
        val shortScreenshots: List<Screenshot>? = null,
        val additions: List<Game> = emptyList(),
        val gameSeriesCount: Int = 0,
        val additionsCount: Int = 0,
    ) : Model()

    data class Genre(
        val id: Int,
        val name: String,
        val selected: Boolean = false
    ) : Model()

    data class Info(
        val id: Int,
        val name: String
    ) : Model()

    data class Screenshot(
        val id: Int,
        val image: String
    ) : Model()
}
