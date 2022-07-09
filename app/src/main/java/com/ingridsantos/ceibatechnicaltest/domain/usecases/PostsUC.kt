package com.ingridsantos.ceibatechnicaltest.domain.usecases

import com.ingridsantos.ceibatechnicaltest.domain.repositories.PostsUserRepository

class PostsUC(
    private val postsUserRepository: PostsUserRepository
) {

    operator fun invoke(userId: Int) = postsUserRepository.getPosts(userId)
}
