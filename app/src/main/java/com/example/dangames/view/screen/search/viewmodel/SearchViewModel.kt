package com.example.dangames.view.screen.search.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dangames.network.domain.model.Model
import com.example.dangames.network.domain.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val gameRepository: GameRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    init {
        getData()
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateQuery -> _state.update { it.copy(query = event.query) }
            is SearchEvent.SelectGenre -> selectGenre(event.genre)
        }
    }

    private fun getData() {
        viewModelScope.launch {
            gameRepository.getGenres()
                .onSuccess { genres ->
                    _state.update { it.copy(genres = genres) }
                }.onFailure { e ->
                    Log.d("SearchViewModel", "getData: $e")
                }
        }
    }

    private fun selectGenre(genre: Model.Genre) {
        val updatedGenre = genre.copy(selected = !genre.selected)
        val updatedGenres = state.value.genres.map { if(it.id == genre.id) updatedGenre else it }
        _state.update { it.copy(genres = updatedGenres) }
    }
}