package com.example.dangames.view.screen.home.viewmodel

sealed class HomeEvent {
    data object GetData : HomeEvent()
}