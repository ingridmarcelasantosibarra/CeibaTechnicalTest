package com.ingridsantos.ceibatechnicaltest.domain.repositories

import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    fun getUsers(): Flow<List<User>>
}
