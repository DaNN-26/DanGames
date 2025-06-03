package com.example.dangames.view.screen.favorites.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dangames.db.domain.repository.FavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    private val _state = MutableStateFlow(FavoritesState())
    val state = _state.asStateFlow()

    fun onEvent(event: FavoritesEvent) {
        when(event) {
            is FavoritesEvent.GetFavoriteGames -> getFavoriteGames()
        }
    }

    private fun getFavoriteGames() {
        Log.d("FavoritesViewModel", "getFavoriteGames")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favoritesRepository.getFavorites().collect { favorites ->
                    Log.d("FavoritesViewModel", "favorites: $favorites")
                    _state.update { it.copy(favoriteGames = favorites) }
                }
            } catch (e: Exception) {
                Log.d("FavoritesViewModel", e.toString())
            }
        }
    }
}