package com.example.dangames.view.screen.favorites.viewmodel

import com.example.dangames.network.domain.model.Model

data class FavoritesState(
    val favoriteGames: List<Model.Game> = emptyList(),
)
