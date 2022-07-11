package com.ingridsantos.ceibatechnicaltest.presentation.users.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ingridsantos.ceibatechnicaltest.core.CoroutinesTestRule
import com.ingridsantos.ceibatechnicaltest.domain.entities.Post
import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import com.ingridsantos.ceibatechnicaltest.domain.exceptions.DomainException
import com.ingridsantos.ceibatechnicaltest.domain.usecases.LocalUserUC
import com.ingridsantos.ceibatechnicaltest.domain.usecases.PostsUC
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostsViewModelTest {

    private val postsUC = mockk<PostsUC>()

    private val localUserUC = mockk<LocalUserUC>()

    private lateinit var postsViewModel: PostsViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        postsViewModel = PostsViewModel(
            postsUC = postsUC,
            localUserUC = localUserUC
        )
    }

    @Test
    fun getPostsShouldReturnSuccess() {
        runBlocking {
            val post = mockk<Post>()
            val listPost = listOf(post)
            val successResult = flow {
                emit(listPost)
            }

            every { postsUC.invoke(1) } returns successResult

            postsViewModel.getPosts(1)

            verify { postsUC.invoke(1) }
            spyk(successResult).collect()
        }
    }

    @Test
    fun getPostsShouldReturnError() {
        runBlocking {
            val exception = DomainException()
            val flow = flow<List<Post>> {
                throw exception
            }

            every { postsUC.invoke(1) } returns flow

            postsViewModel.getPosts(1)

            verify { postsUC.invoke(1) }
            spyk(flow).catch { }
        }
    }

    @Test
    fun getUser() {
        runBlocking {
            val user = mockk<User>()
            val successResult = flow {
                emit(user)
            }

            every { localUserUC.invoke(1) } returns successResult

            postsViewModel.getUser(1)

            verify { localUserUC.invoke(1) }
            spyk(successResult).collect()
        }
    }
}
