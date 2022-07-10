package com.ingridsantos.ceibatechnicaltest.data.repositories

import com.ingridsantos.ceibatechnicaltest.data.endpoints.PostsUserApi
import com.ingridsantos.ceibatechnicaltest.data.models.PostsDTO
import com.ingridsantos.ceibatechnicaltest.domain.entities.Post
import com.ingridsantos.ceibatechnicaltest.domain.exceptions.UnknownError
import com.ingridsantos.ceibatechnicaltest.domain.repositories.DomainExceptionRepository
import com.ingridsantos.ceibatechnicaltest.utils.Mapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class PostsUserRepositoryImplTest {

    private val postsUserApi = mockk<PostsUserApi>()

    private val mapPost = mockk<Mapper<PostsDTO, Post>>()

    private val domainExceptionRepository = mockk<DomainExceptionRepository>()

    private lateinit var postsUserRepositoryImpl: PostsUserRepositoryImpl

    @Before
    fun setUp() {
        postsUserRepositoryImpl = PostsUserRepositoryImpl(
            mapPost = mapPost,
            domainExceptionRepository = domainExceptionRepository,
            postsUserApi = postsUserApi
        )
    }

    @Test
    fun getPosts() = runBlocking {
        val postsDTO = mockk<PostsDTO>()
        val post = mockk<Post>()
        val listPostsDTO = listOf(postsDTO)
        val listPost = listOf(post)

        coEvery { postsUserApi.getPosts(1) } returns listPostsDTO
        every { mapPost.invoke(postsDTO) } returns post

        postsUserRepositoryImpl.getPosts(1).collect {
            assertEquals(it, listPost)
        }

        coVerify { postsUserApi.getPosts(1) }
        verify { mapPost.invoke(postsDTO) }
        confirmVerified(postsDTO, post, postsUserApi, mapPost)
    }

    @Test
    fun getPostsReturnException() = runBlocking {
        val exception: HttpException = mockk()
        coEvery { postsUserApi.getPosts(1) } throws exception
        every { domainExceptionRepository.manageError(any()) } returns UnknownError()

        postsUserRepositoryImpl.getPosts(1).catch {
            assert(it is UnknownError)
        }.collect()

        coVerify { postsUserApi.getPosts(1) }
        verify { domainExceptionRepository.manageError(any()) }
        confirmVerified(postsUserApi)
    }
}
