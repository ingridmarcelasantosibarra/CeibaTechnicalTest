package com.ingridsantos.ceibatechnicaltest.domain.usecases

import com.ingridsantos.ceibatechnicaltest.domain.entities.Post
import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import com.ingridsantos.ceibatechnicaltest.domain.exceptions.DomainException
import com.ingridsantos.ceibatechnicaltest.domain.repositories.PostsUserRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PostsUCTest {

    private val postsUserRepository = mockk<PostsUserRepository>()
    private lateinit var postsUC: PostsUC

    @Before
    fun setup() {
        postsUC = PostsUC(postsUserRepository)
    }

    @Test
    fun whenGetPostsShouldReturnsPosts() {
        runBlocking {
            val post = mockk<Post>()
            val posts = listOf(post)
            every { postsUserRepository.getPosts(1) } returns flowOf(posts)

            postsUC.invoke(1).collect { postsList ->
                assertEquals(postsList, posts)
            }

            verify { postsUserRepository.getPosts(1) }
            confirmVerified(postsUserRepository)
        }
    }

    @Test
    fun whenGetPostsIsCalledReturnException() {
        runBlocking {
            val exception = DomainException()
            val flow = flow<List<Post>> {
                throw exception
            }

            every { postsUserRepository.getPosts(1) } returns flow

            postsUC.invoke(1).catch { error ->
                assert(error is DomainException)
            }.collect()

            verify { postsUserRepository.getPosts(1) }
        }
    }
}
