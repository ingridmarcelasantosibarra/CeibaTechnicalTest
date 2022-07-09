package com.ingridsantos.ceibatechnicaltest.data.repositories

import com.ingridsantos.ceibatechnicaltest.data.endpoints.PostsUserApi
import com.ingridsantos.ceibatechnicaltest.data.models.PostsDTO
import com.ingridsantos.ceibatechnicaltest.domain.entities.Posts
import com.ingridsantos.ceibatechnicaltest.domain.repositories.DomainExceptionRepository
import com.ingridsantos.ceibatechnicaltest.domain.repositories.PostsUserRepository
import com.ingridsantos.ceibatechnicaltest.utils.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PostsUserRepositoryImpl(
    private val postsUserApi: PostsUserApi,
    private val mapPosts: Mapper<PostsDTO, Posts>,
    private val domainExceptionRepository: DomainExceptionRepository
) : PostsUserRepository {

    override fun getPosts(userId: Int): Flow<List<Posts>> {
        return flow {
            val posts = postsUserApi.getPosts(userId).map {
                mapPosts(it)
            }
            emit(posts)
        }.catch {
            throw domainExceptionRepository.manageError(it)
        }
    }
}
