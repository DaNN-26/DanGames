package com.example.dangames

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.dangames.db.di.DbModule
import com.example.dangames.navigation.DanGamesNavHost
import com.example.dangames.network.di.NetworkModule
import com.example.dangames.view.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication

@Composable
fun App() {
    val context = LocalContext.current.applicationContext

    KoinApplication(
        application = {
            androidContext(context)
            modules(listOf(NetworkModule, DbModule, ViewModelModule))
        }
    ) {
        DanGamesNavHost()
    }
}