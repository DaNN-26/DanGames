package com.example.dangames.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavDestination {
    @Serializable
    data object Home : NavDestination
    @Serializable
    class AllGames(val query: String = "", val genres: List<Int> = emptyList()) : NavDestination
    @Serializable
    class GameDetails(val id: Int) : NavDestination
    @Serializable
    class GameSeries(val id: Int) : NavDestination
    @Serializable
    data object Search : NavDestination
    @Serializable
    data object Favorites : NavDestination
}