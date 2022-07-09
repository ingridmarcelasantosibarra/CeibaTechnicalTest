package com.ingridsantos.ceibatechnicaltest.presentation.users.state

import com.ingridsantos.ceibatechnicaltest.domain.entities.User

sealed class LocalUsersState {
    data class SuccessUsers(val users: List<User>) : LocalUsersState()
    object EmptyUsers : LocalUsersState()
}
