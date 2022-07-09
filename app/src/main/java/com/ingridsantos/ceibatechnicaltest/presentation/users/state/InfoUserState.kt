package com.ingridsantos.ceibatechnicaltest.presentation.users.state

import com.ingridsantos.ceibatechnicaltest.domain.entities.User

sealed class InfoUserState {
    data class SuccessUser(val user: User) : InfoUserState()
}
