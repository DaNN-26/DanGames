package com.example.dangames.view.screen.favorites.viewmodel

sealed class FavoritesEvent {
    data object GetFavoriteGames : FavoritesEvent()
}