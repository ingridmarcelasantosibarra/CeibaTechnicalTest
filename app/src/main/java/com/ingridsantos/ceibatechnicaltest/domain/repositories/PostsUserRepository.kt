package com.ingridsantos.ceibatechnicaltest.domain.repositories

import com.ingridsantos.ceibatechnicaltest.domain.entities.Post
import kotlinx.coroutines.flow.Flow

interface PostsUserRepository {

    fun getPosts(
        userId: Int
    ): Flow<List<Post>>
}
