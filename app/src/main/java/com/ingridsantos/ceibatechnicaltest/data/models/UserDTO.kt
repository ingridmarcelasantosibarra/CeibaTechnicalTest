package com.ingridsantos.ceibatechnicaltest.data.models

import com.squareup.moshi.Json

data class UserDTO(
    @Json(name = "id")
    val id: Int,
    @Json(name = "username")
    val username: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "phone")
    val phone: String
)
