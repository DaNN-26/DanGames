package com.example.dangames.view.screen.game_series.viewmodel

sealed class GameSeriesEvent {
    class GetGameSeries(val id: Int) : GameSeriesEvent()
}