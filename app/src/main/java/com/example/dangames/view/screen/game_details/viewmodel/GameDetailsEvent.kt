package com.example.dangames.view.screen.game_details.viewmodel

sealed class GameDetailsEvent {
    class GetGameDetails(val gameId: Int) : GameDetailsEvent()
    data object UpdateFavorite : GameDetailsEvent()
}