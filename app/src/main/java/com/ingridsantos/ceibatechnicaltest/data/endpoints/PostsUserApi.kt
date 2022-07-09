package com.ingridsantos.ceibatechnicaltest.data.endpoints

import com.ingridsantos.ceibatechnicaltest.data.models.PostsDTO
import retrofit2.http.POST
import retrofit2.http.Query

interface PostsUserApi {

    @POST("/posts")
    suspend fun getPosts(
        @Query("userId") userId: Int,
    ): List<PostsDTO>
}
