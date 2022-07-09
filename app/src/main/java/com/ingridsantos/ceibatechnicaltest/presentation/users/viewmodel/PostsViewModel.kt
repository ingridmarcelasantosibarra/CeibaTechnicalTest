package com.ingridsantos.ceibatechnicaltest.presentation.users.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingridsantos.ceibatechnicaltest.domain.usecases.PostsUC
import com.ingridsantos.ceibatechnicaltest.domain.usecases.LocalUserUC
import com.ingridsantos.ceibatechnicaltest.presentation.users.state.InfoUserState
import com.ingridsantos.ceibatechnicaltest.presentation.users.state.PostsState
import com.ingridsantos.ceibatechnicaltest.utils.handleViewModelExceptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PostsViewModel(
    private val postsUC: PostsUC,
    private val localUserUC: LocalUserUC
) : ViewModel() {

    private val _postsFlow =
        MutableStateFlow<PostsState>(PostsState.HideLoading)
    val postsFlow: StateFlow<PostsState>
        get() = _postsFlow

    private val _infoUserState = MutableLiveData<InfoUserState>()
    val infoUserState: LiveData<InfoUserState>
        get() = _infoUserState

    fun getPosts(userId: Int) {
        viewModelScope.launch {
            postsUC.invoke(userId)
                .onStart {
                    _postsFlow.value = PostsState.Loading
                }
                .onCompletion {
                    _postsFlow.value = PostsState.HideLoading
                }
                .handleViewModelExceptions { domainException ->
                    _postsFlow.value = PostsState.Error(domainException.message)
                }
                .collect {
                    _postsFlow.value = PostsState.Success(it)
                }
        }
    }

    fun getUser(userId: Int) {
        viewModelScope.launch {
            localUserUC.invoke(userId).collect {
                _infoUserState.value = InfoUserState.SuccessUser(it)
            }
        }
    }
}
