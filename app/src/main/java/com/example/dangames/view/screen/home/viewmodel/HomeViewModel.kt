package com.example.dangames.view.screen.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dangames.network.domain.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val gameRepository: GameRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetData -> getHomeData()
        }
    }

    private fun getHomeData() {
        _state.update { it.copy(isError = false) }
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.getHomeData()
                .onSuccess { games ->
                    _state.update { it.copy(games = games) }
                }.onFailure { e ->
                    Log.e("HomeViewModel", "${e.message}")
                    _state.update { it.copy(isError = true) }
                }
        }
    }
}