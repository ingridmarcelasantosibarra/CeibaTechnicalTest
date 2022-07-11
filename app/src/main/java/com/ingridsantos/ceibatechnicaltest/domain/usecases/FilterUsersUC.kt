package com.ingridsantos.ceibatechnicaltest.domain.usecases

import com.ingridsantos.ceibatechnicaltest.domain.entities.User

class FilterUsersUC {

    operator fun invoke(
        filter: CharSequence,
        users: List<User>?
    ): List<User> {
        return if (users.isNullOrEmpty().not()) {
            users!!.filter {
                it.username.startsWith(filter, true)
            }
        } else {
            listOf()
        }
    }
}
