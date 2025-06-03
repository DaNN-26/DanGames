package com.example.dangames.view.screen.home.viewmodel

import com.example.dangames.network.domain.model.Model

data class HomeState(
    val games: List<Model.Game> = emptyList(),
    val isError: Boolean = false
)
