package com.ingridsantos.ceibatechnicaltest.domain.usecases

import com.ingridsantos.ceibatechnicaltest.domain.repositories.PostsUserRepository
import com.ingridsantos.ceibatechnicaltest.domain.repositories.UsersRepository

class UsersUC(
    private val userRepository: UsersRepository
) {

    operator fun invoke() = userRepository.getUsers()
}