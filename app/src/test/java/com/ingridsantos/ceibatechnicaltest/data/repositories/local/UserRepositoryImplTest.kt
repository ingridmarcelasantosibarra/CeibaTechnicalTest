package com.ingridsantos.ceibatechnicaltest.data.repositories.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ingridsantos.ceibatechnicaltest.core.CoroutinesTestRule
import com.ingridsantos.ceibatechnicaltest.data.local.TechnicalTestRoomDatabase
import com.ingridsantos.ceibatechnicaltest.data.local.dao.UserDao
import com.ingridsantos.ceibatechnicaltest.data.local.entities.LocalUser
import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import com.ingridsantos.ceibatechnicaltest.utils.Mapper
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class UserRepositoryImplTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private val technicalTestRoomDatabase = mockk<TechnicalTestRoomDatabase>()
    private val mapToUser = mockk<Mapper<LocalUser, User>>()
    private lateinit var userRepositoryImpl: UserRepositoryImpl

    @Before
    fun setup() {
        userRepositoryImpl = UserRepositoryImpl(
            technicalTestRoomDatabase = technicalTestRoomDatabase,
            mapToUser = mapToUser
        )
    }

    @Test
    fun getUser() = runBlocking {
        val userDao = mockk<UserDao>()
        val user = mockk<User>()
        val localUser = mockk<LocalUser>()

        every { technicalTestRoomDatabase.userDao() } returns userDao
        every { userDao.getInfoUser(1) } returns flowOf(localUser)
        every { mapToUser.invoke(localUser) } returns user

        userRepositoryImpl.getUser(1).collect { result ->
            assertEquals(result, user)
        }

        verify { technicalTestRoomDatabase.userDao() }
        verify { userDao.getInfoUser(1) }
        verify { mapToUser.invoke(localUser) }
        verify { user.equals(user) }
        confirmVerified(userDao, user, technicalTestRoomDatabase)
    }
}
