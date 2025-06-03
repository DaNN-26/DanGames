package com.example.dangames.view.screen.search.viewmodel

import com.example.dangames.network.domain.model.Model

data class SearchState(
    val query: String = "",
    val genres: List<Model.Genre> = emptyList(),
)