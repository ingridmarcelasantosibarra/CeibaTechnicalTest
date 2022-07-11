package com.ingridsantos.ceibatechnicaltest.presentation.users.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ingridsantos.ceibatechnicaltest.core.CoroutinesTestRule
import com.ingridsantos.ceibatechnicaltest.domain.entities.Post
import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import com.ingridsantos.ceibatechnicaltest.domain.exceptions.DomainException
import com.ingridsantos.ceibatechnicaltest.domain.usecases.LocalUsersUC
import com.ingridsantos.ceibatechnicaltest.domain.usecases.UsersUC
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

class UsersViewModelTest {

    private val usersUC = mockk<UsersUC>()
    private val localUsersUC = mockk<LocalUsersUC>()

    private lateinit var usersViewModel: UsersViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = CoroutinesTestRule()

    @Before
    fun setup() {
        usersViewModel = UsersViewModel(
            usersUC = usersUC,
            localUsersUC = localUsersUC
        )
    }

    @Test
    fun getUsersShouldReturnSuccess() {
        runBlocking {
            val user = mockk<User>()
            val listUser = listOf(user)
            val successResult = flow {
                emit(listUser)
            }

            every { usersUC.invoke() } returns successResult

            usersViewModel.getUsers()

            verify { usersUC.invoke() }
            spyk(successResult).collect()
        }
    }

    @Test
    fun getUsersShouldReturnError() {
        runBlocking {
            val exception = DomainException()
            val flow = flow<List<User>> {
                throw exception
            }

            every { usersUC.invoke() } returns flow

            usersViewModel.getUsers()

            verify { usersUC.invoke() }
            spyk(flow).catch { }
        }
    }

    @Test
    fun getLocalUserShouldReturnUsers() {
        runBlocking {
            val user = mockk<User>()
            val successResult = flow {
                emit(listOf(user))
            }

            every { localUsersUC.getUsers() } returns successResult

            usersViewModel.getLocalUsers()

            verify { localUsersUC.getUsers() }
            spyk(successResult).collect()
        }
    }

    @Test
    fun getLocalUserShouldReturnEmptyList() {
        runBlocking {
            val successResult = flow {
                emit(listOf<User>())
            }

            every { localUsersUC.getUsers() } returns successResult

            usersViewModel.getLocalUsers()

            verify { localUsersUC.getUsers() }
            spyk(successResult).collect()
        }
    }
}