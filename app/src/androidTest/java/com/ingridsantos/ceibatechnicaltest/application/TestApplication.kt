package com.ingridsantos.ceibatechnicaltest.application

import android.app.Application
import com.ingridsantos.ceibatechnicaltest.di.localStorageTestModule
import com.ingridsantos.ceibatechnicaltest.di.networkTestModule
import com.ingridsantos.ceibatechnicaltest.di.postsTestModule
import com.ingridsantos.ceibatechnicaltest.di.usersTestModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestApplication)
            modules(
                listOf(
                    networkTestModule,
                    localStorageTestModule,
                    usersTestModule,
                    postsTestModule
                )
            )
        }
    }
}
