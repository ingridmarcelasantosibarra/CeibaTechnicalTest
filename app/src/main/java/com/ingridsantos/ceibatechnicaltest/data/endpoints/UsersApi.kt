package com.ingridsantos.ceibatechnicaltest.data.endpoints

import com.ingridsantos.ceibatechnicaltest.data.models.UserDTO
import retrofit2.http.GET

interface UsersApi {

    @GET("/users")
    suspend fun getUsers(): List<UserDTO>
}
