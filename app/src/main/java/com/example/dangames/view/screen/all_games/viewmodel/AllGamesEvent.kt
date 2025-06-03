package com.example.dangames.view.screen.all_games.viewmodel

sealed class AllGamesEvent {
    class GetGames(val query: String, val genres: List<Int>) : AllGamesEvent()
    class ChangePage(val page: Int) : AllGamesEvent()
}