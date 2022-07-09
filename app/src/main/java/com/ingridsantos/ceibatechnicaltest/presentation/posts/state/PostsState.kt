package com.ingridsantos.ceibatechnicaltest.presentation.posts.state

import com.ingridsantos.ceibatechnicaltest.domain.entities.Posts

sealed class PostsState {
    object Loading : PostsState()
    object HideLoading : PostsState()
    data class Success(val posts: List<Posts>) : PostsState()
    data class Error(val message: String) : PostsState()
}
