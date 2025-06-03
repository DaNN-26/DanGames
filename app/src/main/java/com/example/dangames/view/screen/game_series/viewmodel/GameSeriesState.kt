package com.example.dangames.view.screen.game_series.viewmodel

import com.example.dangames.network.domain.model.BaseModel
import com.example.dangames.network.domain.model.Model

data class GameSeriesState(
    val model: BaseModel<Model.Game>? = null,
    val isError: Boolean = false
)
