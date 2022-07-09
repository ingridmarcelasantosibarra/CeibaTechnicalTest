package com.ingridsantos.ceibatechnicaltest.presentation.di

import com.ingridsantos.ceibatechnicaltest.data.local.TechnicalTestRoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val localStorageModule: Module = module {
    single { TechnicalTestRoomDatabase.buildDatabase(androidContext()) }
}
