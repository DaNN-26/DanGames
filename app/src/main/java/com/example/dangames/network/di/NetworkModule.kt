package com.example.dangames.network.di

import com.example.dangames.network.core.ApiClient
import com.example.dangames.network.data.repository.GameRepositoryImpl
import com.example.dangames.network.domain.repository.GameRepository
import io.ktor.client.HttpClient
import org.koin.dsl.module

val NetworkModule = module {
    single<HttpClient> { ApiClient.client }
    single<GameRepository> { GameRepositoryImpl(get()) }
}