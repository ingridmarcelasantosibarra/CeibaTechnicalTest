package com.ingridsantos.ceibatechnicaltest.presentation.users.viewmodel

import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import com.ingridsantos.ceibatechnicaltest.domain.usecases.FilterUsersUC
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class FilterUsersViewModelTest {

    private val filterUsersUC = mockk<FilterUsersUC>()

    private lateinit var filterUsersViewModel: FilterUsersViewModel

    @Before
    fun setUp() {
        filterUsersViewModel = FilterUsersViewModel(
            filterUsersUC = filterUsersUC
        )
    }

    @Test
    fun getFilterReference() {
        val user = mockk<User>()
        val userFilter = mockk<User>()
        val listUser = listOf(user)
        val listUserFilter = listOf(userFilter)

        every { filterUsersUC.invoke("i", listUser) } returns listUserFilter

        filterUsersViewModel.getFilterReference("i", listUser)

        verify { filterUsersUC.invoke("i", listUser) }
        confirmVerified(filterUsersUC)
    }
}
