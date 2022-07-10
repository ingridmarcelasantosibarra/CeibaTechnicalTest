package com.ingridsantos.ceibatechnicaltest.domain.usecases

import com.ingridsantos.ceibatechnicaltest.domain.entities.Post
import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import com.ingridsantos.ceibatechnicaltest.domain.exceptions.DomainException
import com.ingridsantos.ceibatechnicaltest.domain.repositories.UsersRepository
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

class UsersUCTest {
    private val userRepository = mockk<UsersRepository>()

    private lateinit var usersUC: UsersUC

    @Before
    fun setup() {
        usersUC = UsersUC(userRepository)
    }

    @Test
    fun whenGetUsersShouldReturnSuccessUsers() {
        runBlocking {
            val user = mockk<User>()
            val listUser = listOf(user)
            every { userRepository.getUsers() } returns flowOf(listUser)

            usersUC.invoke().collect { users ->
                assertEquals(users, listUser)
            }

            verify { userRepository.getUsers() }
            confirmVerified(userRepository, user)
        }
    }

    @Test
    fun whenGetUsersIsCalledReturnException() {
        runBlocking {
            val exception = DomainException()
            val flow = flow<List<User>> {
                throw exception
            }

            every { userRepository.getUsers() } returns flow

            usersUC.invoke().catch { error ->
                assert(error is DomainException)
            }.collect()

            verify { userRepository.getUsers() }
        }
    }
}
