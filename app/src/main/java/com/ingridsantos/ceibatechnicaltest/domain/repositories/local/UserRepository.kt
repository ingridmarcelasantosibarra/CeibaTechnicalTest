package com.ingridsantos.ceibatechnicaltest.domain.repositories.local

import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUser(userId: Int): Flow<User>
}
