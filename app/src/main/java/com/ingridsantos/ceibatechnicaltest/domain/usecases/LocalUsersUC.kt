package com.ingridsantos.ceibatechnicaltest.domain.usecases

import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import com.ingridsantos.ceibatechnicaltest.domain.repositories.local.LocalUsersRepository

class LocalUsersUC(
    private val localUsersRepository: LocalUsersRepository
) {

    fun getUsers() = localUsersRepository.getUsers()

    suspend fun insertAll(users: List<User>) = localUsersRepository.insertAll(users)
}
