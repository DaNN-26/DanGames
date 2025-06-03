package com.example.dangames.view.screen.game_details.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dangames.db.domain.repository.FavoritesRepository
import com.example.dangames.network.domain.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameDetailsViewModel(
    private val gameRepository: GameRepository,
    private val favoritesRepository: FavoritesRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(GameDetailsState())
    val state = _state.asStateFlow()

    fun onEvent(event: GameDetailsEvent) {
        when (event) {
            is GameDetailsEvent.GetGameDetails -> getGameDetails(event.gameId)
            is GameDetailsEvent.UpdateFavorite -> updateFavorite()
        }
    }

    private fun getGameDetails(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.getGameById(id).onSuccess { game ->
                favoritesRepository.isFavorite(id).collect { isFavorite ->
                    _state.update { it.copy(currentGame = game.copy(isFavorite = isFavorite)) }
                    getGameAdditions(id)
                }
            }.onFailure {
                Log.d("GameDetailsViewModel", "onFailure: $it")
            }
        }
    }

    private suspend fun getGameAdditions(id: Int) {
        gameRepository.getGameAdditions(id).onSuccess { additions ->
            val updatedAdditions = additions.filter { it.backgroundImage != null }
            _state.update { it.copy(currentGame = it.currentGame?.copy(additions = updatedAdditions)) }
        }.onFailure {
            Log.d("GameDetailsViewModel", "onFailure: $it")
        }
    }

    private fun updateFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentGame = state.value.currentGame
                if (currentGame != null) {
                    if (currentGame.isFavorite)
                        favoritesRepository.deleteFavorite(currentGame)
                    else
                        favoritesRepository.insertFavorite(currentGame)
                    _state.update { it.copy(currentGame = currentGame.copy(isFavorite = !currentGame.isFavorite)) }
                }
            } catch (e: Exception) {
                Log.d("GameDetailsViewModel", "updateFavorite: $e")
            }
        }
    }
}