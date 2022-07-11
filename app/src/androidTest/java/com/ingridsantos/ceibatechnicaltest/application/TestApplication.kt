package com.ingridsantos.ceibatechnicaltest.application

import android.app.Application
import com.ingridsantos.ceibatechnicaltest.presentation.di.localStorageModule
import com.ingridsantos.ceibatechnicaltest.presentation.di.postsModule
import com.ingridsantos.ceibatechnicaltest.presentation.di.usersModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestApplication)
            modules(
                listOf(
                    localStorageModule,
                    usersModule,
                    postsModule
                )
            )
        }
    }
}