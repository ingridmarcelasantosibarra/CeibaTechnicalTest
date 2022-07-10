package com.ingridsantos.ceibatechnicaltest.data.repositories

import com.ingridsantos.ceibatechnicaltest.data.endpoints.UsersApi
import com.ingridsantos.ceibatechnicaltest.data.models.UserDTO
import com.ingridsantos.ceibatechnicaltest.domain.entities.User
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
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class UserRepositoryImplTest {

    private val userApi = mockk<UsersApi>()

    private val mapUser = mockk<Mapper<UserDTO, User>>()

    private val domainExceptionRepository = mockk<DomainExceptionRepository>()

    private lateinit var usersRepositoryImpl: UsersRepositoryImpl

    @Before
    fun setup() {
        usersRepositoryImpl = UsersRepositoryImpl(
            userApi = userApi,
            domainExceptionRepository = domainExceptionRepository,
            mapUser = mapUser
        )
    }

    @Test
    fun getPosts() = runBlocking {
        val users = mockk<User>()
        val userDTO = mockk<UserDTO>()
        val listUserDTO = listOf(userDTO)
        val listUsers = listOf(users)

        coEvery { userApi.getUsers() } returns listUserDTO
        every { mapUser.invoke(userDTO) } returns users

        usersRepositoryImpl.getUsers().collect {
            assertEquals(it, listUsers)
        }

        coVerify { userApi.getUsers() }
        verify { mapUser.invoke(userDTO) }
        confirmVerified(userDTO, users)
    }

    @Test
    fun getUsersReturnException() = runBlocking {
        val exception: HttpException = mockk()
        coEvery { userApi.getUsers() } throws exception
        every { domainExceptionRepository.manageError(any()) } returns UnknownError()

        usersRepositoryImpl.getUsers().catch {
            assert(it is UnknownError)
        }.collect()

        coVerify { userApi.getUsers() }
        verify { domainExceptionRepository.manageError(any()) }
        confirmVerified(exception)
    }

    @After
    fun tearDown() {
        confirmVerified(userApi, domainExceptionRepository, mapUser)
    }
}
