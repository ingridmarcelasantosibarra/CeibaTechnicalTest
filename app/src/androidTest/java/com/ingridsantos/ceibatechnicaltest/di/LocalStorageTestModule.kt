package com.ingridsantos.ceibatechnicaltest.di

import androidx.room.Room
import com.ingridsantos.ceibatechnicaltest.data.local.TechnicalTestRoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localStorageTestModule = module {

    single {
        Room.inMemoryDatabaseBuilder(androidContext(), TechnicalTestRoomDatabase::class.java).build()
    }
}
