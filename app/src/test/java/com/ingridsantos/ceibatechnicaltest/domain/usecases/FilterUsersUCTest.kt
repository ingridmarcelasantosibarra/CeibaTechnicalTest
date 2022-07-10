package com.ingridsantos.ceibatechnicaltest.domain.usecases

import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FilterUsersUCTest {

    private lateinit var filterUsersUC: FilterUsersUC

    @Before
    fun setup() {
        filterUsersUC = FilterUsersUC()
    }

    @Test
    fun whenUsersFilterIsCalledShouldReturnUser() {
        val user = mockk<User>()
        val users = listOf(user)

        every { user.username } returns "ingrid"

        val result = filterUsersUC.invoke("i", users)

        assertEquals(result, users)
        verify(exactly = 1) { user.username }
        confirmVerified(user)
    }

    @Test
    fun whenUsersFilterIsCalledShouldReturnEmpty() {
        val user = mockk<User>()
        val users = listOf(user)

        every { user.username } returns "ingrid"

        val result = filterUsersUC.invoke("m", users)

        assertEquals(result, listOf<User>())
        verify(exactly = 1) { user.username }
        confirmVerified(user)
    }

    @Test
    fun whenUsersFilterIsCalledShouldReturnEmptyCase2() {
        val users = listOf<User>()

        val result = filterUsersUC.invoke("m", users)

        assertEquals(result, listOf<User>())
    }
}
