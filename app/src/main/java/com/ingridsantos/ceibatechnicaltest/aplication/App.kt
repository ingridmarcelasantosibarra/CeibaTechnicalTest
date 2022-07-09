package com.ingridsantos.ceibatechnicaltest.aplication

import android.app.Application
import com.ingridsantos.ceibatechnicaltest.presentation.di.usersModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                usersModule
            )
        }
    }
}
