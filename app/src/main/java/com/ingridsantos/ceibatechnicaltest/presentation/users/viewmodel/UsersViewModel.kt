package com.ingridsantos.ceibatechnicaltest.presentation.users.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingridsantos.ceibatechnicaltest.domain.usecases.LocalUsersUC
import com.ingridsantos.ceibatechnicaltest.domain.usecases.UsersUC
import com.ingridsantos.ceibatechnicaltest.presentation.users.state.LocalUsersState
import com.ingridsantos.ceibatechnicaltest.presentation.users.state.UsersState
import com.ingridsantos.ceibatechnicaltest.utils.handleViewModelExceptions
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class UsersViewModel(
    private val usersUC: UsersUC,
    private val localUsersUC: LocalUsersUC
) : ViewModel() {

    private val _usersState =
        MutableLiveData<UsersState>()
    val usersState: LiveData<UsersState>
        get() = _usersState

    private val _localUsersState = MutableLiveData<LocalUsersState>()
    val localUserState: LiveData<LocalUsersState>
        get() = _localUsersState

    fun getUsers() {
        viewModelScope.launch {
            usersUC.invoke()
                .onStart {
                    _usersState.value = UsersState.Loading
                }
                .onCompletion {
                    _usersState.value = UsersState.HideLoading
                }
                .handleViewModelExceptions { domainException ->
                    _usersState.value = UsersState.Error(domainException.message)
                }
                .collect {
                    if (it.isEmpty()) {
                        _usersState.value = UsersState.EmptyUsers
                        localUsersUC.deleteAll()
                    } else {
                        _usersState.value = UsersState.Success(it)
                        localUsersUC.insertAll(it)
                    }
                }
        }
    }

    fun getLocalUsers() {
        viewModelScope.launch {
            val users = localUsersUC.getUsers()
            users.collect {
                when {
                    it.isNotEmpty() -> _localUsersState.value = LocalUsersState.SuccessUsers(it)
                    it.isEmpty() -> _localUsersState.value = LocalUsersState.EmptyUsers
                }
            }
        }
    }
}
