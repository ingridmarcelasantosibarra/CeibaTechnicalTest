package com.ingridsantos.ceibatechnicaltest.presentation.posts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingridsantos.ceibatechnicaltest.data.repositories.usecases.PostsUC
import com.ingridsantos.ceibatechnicaltest.presentation.posts.state.PostsState
import com.ingridsantos.ceibatechnicaltest.utils.handleViewModelExceptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PostsViewModel(
    private val postsUC: PostsUC
) : ViewModel() {

    private val _postsFlow =
        MutableStateFlow<PostsState>(PostsState.HideLoading)
    val postsFlow: StateFlow<PostsState>
        get() = _postsFlow

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
                    PostsState.Error(domainException.message)
                }
                .collect {
                    _postsFlow.value = PostsState.Success(it)
                }
        }
    }
}
