package com.example.dangames.view.screen.game_series.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dangames.network.domain.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameSeriesViewModel(
    private val gameRepository: GameRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(GameSeriesState())
    val state = _state.asStateFlow()

    fun onEvent(event: GameSeriesEvent) {
        when (event) {
            is GameSeriesEvent.GetGameSeries -> getGameSeries(event.id)
        }
    }

    private fun getGameSeries(id: Int) {
        _state.update { it.copy(isError = false) }
        viewModelScope.launch {
            gameRepository.getGameSeries(id)
                .onSuccess { response ->
                    _state.update { it.copy(model = response) }
                }.onFailure { e ->
                    Log.d("GameSeriesViewModel", "getGameSeries: $e")
                    _state.update { it.copy(isError = true) }
                }
        }
    }
}