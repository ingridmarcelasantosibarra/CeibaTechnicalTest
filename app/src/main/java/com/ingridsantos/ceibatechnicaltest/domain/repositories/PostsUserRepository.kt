package com.ingridsantos.ceibatechnicaltest.domain.repositories

import com.ingridsantos.ceibatechnicaltest.domain.entities.Posts
import kotlinx.coroutines.flow.Flow

interface PostsUserRepository {

    fun getPosts(
        userId: Int
    ): Flow<List<Posts>>
}
