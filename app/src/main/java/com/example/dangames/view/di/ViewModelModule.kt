package com.example.dangames.view.di

import com.example.dangames.view.screen.game_details.viewmodel.GameDetailsViewModel
import com.example.dangames.view.screen.home.viewmodel.HomeViewModel
import com.example.dangames.view.screen.all_games.viewmodel.AllGamesViewModel
import com.example.dangames.view.screen.game_series.viewmodel.GameSeriesViewModel
import com.example.dangames.view.screen.search.viewmodel.SearchViewModel
import com.example.dangames.view.screen.favorites.viewmodel.FavoritesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::AllGamesViewModel)
    viewModelOf(::GameDetailsViewModel)
    viewModelOf(::GameSeriesViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::FavoritesViewModel)
}