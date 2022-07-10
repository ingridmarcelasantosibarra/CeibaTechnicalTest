package com.ingridsantos.ceibatechnicaltest.domain.usecases

import com.ingridsantos.ceibatechnicaltest.domain.entities.User

class FilterUsersUC {

    operator fun invoke(
        filter: CharSequence,
        fastOrders: List<User>?
    ): List<User> {
        return if (fastOrders.isNullOrEmpty().not()) {
            fastOrders!!.filter {
                it.username.startsWith(filter, true)
            }
        } else {
            listOf()
        }
    }
}
