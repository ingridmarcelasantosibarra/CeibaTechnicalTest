package com.ingridsantos.ceibatechnicaltest.data.endpoints

import retrofit2.http.POST
import retrofit2.http.Query

interface PostsUserApi {

    @POST("/posts")
    suspend fun getPosts(
        @Query("userId") userId: Int,
    )
}
