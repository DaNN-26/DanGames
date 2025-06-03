package com.example.dangames.view.screen.all_games.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dangames.network.domain.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AllGamesViewModel(
    private val gameRepository: GameRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(AllGamesState())
    val state = _state.asStateFlow()

    fun onEvent(event: AllGamesEvent) {
        when (event) {
            is AllGamesEvent.GetGames -> getGames(event.query, event.genres)
            is AllGamesEvent.ChangePage -> _state.update {
                it.copy(
                    currentPage = event.page,
                    model = null
                )
            }
        }
    }

    private fun getGames(query: String, genres: List<Int>) {
        _state.update { it.copy(isError = false) }
        viewModelScope.launch(Dispatchers.IO) {
            val result = if (state.value.currentPage == 1)
                gameRepository.getGames(query, genres)
            else gameRepository.getGames(query, genres, state.value.currentPage)
            result.onSuccess { response ->
                _state.update {
                    it.copy(
                        model = response,
                        pagesCount = response.count / 20
                    )
                }
            }.onFailure { e ->
                Log.d("AllGamesViewModel", "${e.message}")
                _state.update { it.copy(isError = true) }
            }
        }
    }
}