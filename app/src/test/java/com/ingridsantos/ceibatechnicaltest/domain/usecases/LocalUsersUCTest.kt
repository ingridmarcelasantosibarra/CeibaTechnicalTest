package com.ingridsantos.ceibatechnicaltest.domain.usecases

import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import com.ingridsantos.ceibatechnicaltest.domain.repositories.local.LocalUsersRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LocalUsersUCTest {

    private val localUsersRepository = mockk<LocalUsersRepository>()

    private lateinit var localUsersUC: LocalUsersUC

    @Before
    fun setup() {
        localUsersUC = LocalUsersUC(localUsersRepository)
    }

    @Test
    fun whenGetUsersShouldReturnUsers() {
        runBlocking {
            val user = mockk<User>()
            val users = listOf(user)

            every { localUsersRepository.getUsers() } returns flowOf(users)

            localUsersUC.getUsers()

            verify { localUsersRepository.getUsers() }
            confirmVerified(user)
        }
    }

    @Test
    fun whenInsertAllUsersReturnSuccess() {
        runBlocking {
            val usersSuccess = listOf(1L, 2L)
            val user = mockk<User>()
            val users = listOf(user)

            coEvery { localUsersRepository.insertAll(users) } returns usersSuccess

            localUsersUC.insertAll(users)

            coVerify { localUsersRepository.insertAll(users) }
            confirmVerified(localUsersRepository, user)
        }
    }

    @Test
    fun whenDeleteAllSuccess() {
        val deleteReturn = (0..11).random()
        runBlocking {
            coEvery { localUsersRepository.deleteAll() } returns deleteReturn

            localUsersUC.deleteAll()

            coVerify { localUsersRepository.deleteAll() }
        }
    }
}
