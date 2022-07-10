package com.ingridsantos.ceibatechnicaltest.domain.usecases

import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import com.ingridsantos.ceibatechnicaltest.domain.exceptions.DomainException
import com.ingridsantos.ceibatechnicaltest.domain.repositories.local.UserRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LocalUserUCTest {

    private val userRepository = mockk<UserRepository>()
    private lateinit var localUserUC: LocalUserUC

    @Before
    fun setup() {
        localUserUC = LocalUserUC(userRepository)
    }

    @Test
    fun whenGetLocalUserIsCalledReturnUser() {
        runBlocking {
            val user = mockk<User>()
            every { userRepository.getUser(1) } returns flowOf(user)

            localUserUC.invoke(1)

            verify { userRepository.getUser(1) }
        }
    }

    @Test
    fun whenGetLocalUserIsCalledReturnException() {
        runBlocking {
            val exception = DomainException()
            val flow = flow<User> {
                throw exception
            }

            every { userRepository.getUser(1) } returns flow

            localUserUC.invoke(1).catch { error ->
                assert(error is DomainException)
            }.collect()

            verify { userRepository.getUser(1) }
        }
    }
}
