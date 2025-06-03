package com.example.dangames.db.di

import androidx.room.Room
import com.example.dangames.db.core.FavoritesDatabase
import com.example.dangames.db.data.repository.FavoritesRepositoryImpl
import com.example.dangames.db.domain.dao.FavoritesDao
import com.example.dangames.db.domain.repository.FavoritesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val DbModule = module {
    single<FavoritesDatabase> {
        Room.databaseBuilder(
            androidContext(),
            FavoritesDatabase::class.java,
            "favorites"
        ).build()
    }

    single<FavoritesDao> { get<FavoritesDatabase>().dao() }

    single<FavoritesRepository> { FavoritesRepositoryImpl(get()) }
}