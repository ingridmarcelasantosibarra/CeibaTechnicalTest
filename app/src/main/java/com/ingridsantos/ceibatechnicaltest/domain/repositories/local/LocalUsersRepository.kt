package com.ingridsantos.ceibatechnicaltest.domain.repositories.local

import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface LocalUsersRepository {

    suspend fun insertAll(users: List<User>): List<Long>

    suspend fun deleteAll(): Int

    fun getUsers(): Flow<List<User>>
}
