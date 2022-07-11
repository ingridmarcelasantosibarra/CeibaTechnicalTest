package com.ingridsantos.ceibatechnicaltest.presentation.users.viewmodel

import androidx.lifecycle.ViewModel
import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import com.ingridsantos.ceibatechnicaltest.domain.usecases.FilterUsersUC

class FilterUsersViewModel(
    private val filterUsersUC: FilterUsersUC
) : ViewModel() {

    fun getFilterReference(
        filter: String,
        users: List<User>?
    ): List<User> {
        return filterUsersUC.invoke(
            filter = filter,
            users = users
        )
    }
}
