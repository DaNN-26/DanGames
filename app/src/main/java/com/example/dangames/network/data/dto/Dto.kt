package com.example.dangames.network.data.dto

import com.example.dangames.network.data.dto.extras.InfoDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Dto {
    @Serializable
    data class Game(
        val id: Int = 0,
        val name: String,
        val description: String = "",
        val released: String? = null,
        val tba: Boolean = false,
        @SerialName("background_image")
        val backgroundImage: String?,
        val rating: Double = 0.0,
        val metacritic: Int? = 0,
        val platforms: List<Platform>? = null,
        val genres: List<Genre>? = null,
        val stores: List<Store>? = null,
        val tags: List<Tag>? = null,
        @SerialName("esrb_rating")
        val esrb: Esrb? = null,
        @SerialName("short_screenshots")
        val shortScreenshots: List<Screenshot>? = null,
        @SerialName("game_series_count")
        val gameSeriesCount: Int = 0,
        @SerialName("additions_count")
        val additionsCount: Int = 0,
    ) : Dto()

    @Serializable
    data class Platform(
        val platform: InfoDto
    ) : Dto()

    @Serializable
    data class Store(
        val id: Int = 0,
        val store: InfoDto
    ) : Dto()

    @Serializable
    data class Genre(
        val id: Int,
        val name: String
    ) : Dto()

    @Serializable
    data class Screenshot(
        val id: Int,
        val image: String
    ) : Dto()

    @Serializable
    data class Esrb(
        val id: Int,
        val name: String
    ) : Dto()

    @Serializable
    data class Tag(
        val id: Int,
        val name: String
    ) : Dto()
}