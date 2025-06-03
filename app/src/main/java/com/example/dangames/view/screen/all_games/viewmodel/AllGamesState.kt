package com.example.dangames.view.screen.all_games.viewmodel

import com.example.dangames.network.domain.model.BaseModel
import com.example.dangames.network.domain.model.Model

data class AllGamesState(
    val model: BaseModel<Model.Game>? = null,
    val query: String = "",
    val genres: List<Model.Genre> = emptyList(),
    val currentPage: Int = 1,
    val pagesCount: Int = 0,
    val isError: Boolean = false
)
