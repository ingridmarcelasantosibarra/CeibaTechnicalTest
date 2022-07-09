package com.ingridsantos.ceibatechnicaltest.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ingridsantos.ceibatechnicaltest.data.local.dao.UserDao
import com.ingridsantos.ceibatechnicaltest.data.local.entities.LocalUser

@Database(
    entities = [
        LocalUser::class
    ],
    version = 1,
    exportSchema = false
)

abstract class TechnicalTestRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TechnicalTestRoomDatabase::class.java,
                "technical_test_database.db"
            )
                .fallbackToDestructiveMigration()
                .setJournalMode(JournalMode.TRUNCATE)
                .build()
    }
}
