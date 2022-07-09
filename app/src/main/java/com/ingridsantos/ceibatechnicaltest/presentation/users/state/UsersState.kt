package com.ingridsantos.ceibatechnicaltest.presentation.users.state

import com.ingridsantos.ceibatechnicaltest.domain.entities.User

sealed class UsersState {
    object Loading : UsersState()
    object HideLoading : UsersState()
    object EmptyUsers : UsersState()
    data class Success(val users: List<User>) : UsersState()
    data class Error(val message: String) : UsersState()
}
