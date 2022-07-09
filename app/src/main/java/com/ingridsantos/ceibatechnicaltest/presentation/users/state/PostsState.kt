package com.ingridsantos.ceibatechnicaltest.presentation.users.state

import com.ingridsantos.ceibatechnicaltest.domain.entities.Post

sealed class PostsState {
    object Loading : PostsState()
    object HideLoading : PostsState()
    data class Success(val posts: List<Post>) : PostsState()
    data class Error(val message: String) : PostsState()
}
