package com.ingridsantos.ceibatechnicaltest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ingridsantos.ceibatechnicaltest.data.local.entities.LocalUser
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(localUsers: List<LocalUser>): List<Long>

    @Query("SELECT * FROM user")
    fun getUsers(): Flow<List<LocalUser>>
}
