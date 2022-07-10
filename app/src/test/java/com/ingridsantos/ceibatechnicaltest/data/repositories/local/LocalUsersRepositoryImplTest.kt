package com.ingridsantos.ceibatechnicaltest.data.repositories.local

import com.ingridsantos.ceibatechnicaltest.data.local.TechnicalTestRoomDatabase
import com.ingridsantos.ceibatechnicaltest.data.local.dao.UserDao
import com.ingridsantos.ceibatechnicaltest.data.local.entities.LocalUser
import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import com.ingridsantos.ceibatechnicaltest.utils.Mapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class LocalUsersRepositoryImplTest {

    private val technicalTestRoomDatabase = mockk<TechnicalTestRoomDatabase>()

    private val mapToLocalUser: Mapper<List<User>, List<LocalUser>> = mockk()

    private val mapToUsers: Mapper<List<LocalUser>, List<User>> = mockk()

    private lateinit var localUsersRepositoryImpl: LocalUsersRepositoryImpl

    @Before
    fun setup() {
        localUsersRepositoryImpl = LocalUsersRepositoryImpl(
            technicalTestRoomDatabase = technicalTestRoomDatabase,
            mapToLocalUser = mapToLocalUser,
            mapToUsers = mapToUsers
        )
    }

    @Test
    fun insetAllUsers() = runBlocking {
        val usersReturn = listOf((0..10L).random())
        val user = mockk<User>()
        val localUser = mockk<LocalUser>()
        val listUser = listOf(user)
        val listLocalUser = listOf(localUser)
        val userDao = mockk<UserDao>()

        every { mapToLocalUser.invoke(listUser) } returns listLocalUser
        every { technicalTestRoomDatabase.userDao() } returns userDao
        coEvery { userDao.insertAll(listLocalUser) } returns usersReturn

        val result = localUsersRepositoryImpl.insertAll(listUser)

        assertEquals(result, usersReturn)
        verify { mapToLocalUser.invoke(listUser) }
        verify { technicalTestRoomDatabase.userDao() }
        coVerify { userDao.insertAll(listLocalUser) }
        confirmVerified(user, localUser, userDao)
    }

    @Test
    fun deleteAllUsers() = runBlocking {
        val deleteReturn = (0..11).random()
        val userDao = mockk<UserDao>()

        every { technicalTestRoomDatabase.userDao() } returns userDao
        coEvery { userDao.deleteAll() } returns deleteReturn

        localUsersRepositoryImpl.deleteAll()

        verify { technicalTestRoomDatabase.userDao() }
        coVerify { userDao.deleteAll() }
        confirmVerified(userDao)
    }

    @Test
    fun getUsers() = runBlocking {
        val userDao = mockk<UserDao>()
        val localUser = mockk<LocalUser>()
        val user = mockk<User>()
        val listLocalUser = listOf(localUser)
        val listUser = listOf(user)

        every { technicalTestRoomDatabase.userDao() } returns userDao
        coEvery { userDao.getUsers() } returns flowOf(listLocalUser)
        every { mapToUsers.invoke(listLocalUser) } returns listUser

        localUsersRepositoryImpl.getUsers().collect { result ->
            assertEquals(result, listUser)
        }

        verify { technicalTestRoomDatabase.userDao() }
        coVerify { userDao.getUsers() }
        verify { mapToUsers.invoke(listLocalUser) }
        confirmVerified(userDao, user, localUser)
    }

    @After
    fun tearDown() {
        confirmVerified(mapToUsers, mapToLocalUser, technicalTestRoomDatabase)
    }
}
