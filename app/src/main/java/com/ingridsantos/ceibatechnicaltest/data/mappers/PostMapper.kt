package com.ingridsantos.ceibatechnicaltest.data.mappers

import com.ingridsantos.ceibatechnicaltest.data.models.PostsDTO
import com.ingridsantos.ceibatechnicaltest.domain.entities.Post
import com.ingridsantos.ceibatechnicaltest.utils.Mapper

val mapToPost: Mapper<PostsDTO, Post> = { postDTO ->
    with(postDTO) {
        Post(
            id = id,
            body = body,
            title = title,
            userId = userId
        )
    }
}
