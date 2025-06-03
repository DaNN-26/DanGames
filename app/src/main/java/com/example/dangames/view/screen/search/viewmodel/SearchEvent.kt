package com.example.dangames.view.screen.search.viewmodel

import com.example.dangames.network.domain.model.Model

sealed class SearchEvent {
    class UpdateQuery(val query: String): SearchEvent()
    class SelectGenre(val genre: Model.Genre): SearchEvent()
}