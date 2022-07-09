package com.ingridsantos.ceibatechnicaltest.domain.usecases

import com.ingridsantos.ceibatechnicaltest.domain.repositories.local.UserRepository

class LocalUserUC(
    private val userRepository: UserRepository
) {

    operator fun invoke(userId: Int) = userRepository.getUser(userId)
}
