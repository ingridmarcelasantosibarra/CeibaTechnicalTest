package com.ingridsantos.ceibatechnicaltest.presentation.users.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingridsantos.ceibatechnicaltest.domain.usecases.UsersUC
import com.ingridsantos.ceibatechnicaltest.presentation.users.state.UsersState
import com.ingridsantos.ceibatechnicaltest.utils.handleViewModelExceptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class UsersViewModel(
    private val usersUC: UsersUC
) : ViewModel() {

    private val _usersFlow =
        MutableStateFlow<UsersState>(UsersState.HideLoading)
    val usersFlow: StateFlow<UsersState>
        get() = _usersFlow

    fun getUsers() {
        viewModelScope.launch {
            usersUC.invoke()
                .onStart {
                    _usersFlow.value = UsersState.Loading
                }
                .onCompletion {
                    _usersFlow.value = UsersState.HideLoading
                }
                .handleViewModelExceptions { domainException ->
                    _usersFlow.value = UsersState.Error(domainException.message)
                }
                .collect {
                    _usersFlow.value = UsersState.Success(it)
                }
        }
    }
}
