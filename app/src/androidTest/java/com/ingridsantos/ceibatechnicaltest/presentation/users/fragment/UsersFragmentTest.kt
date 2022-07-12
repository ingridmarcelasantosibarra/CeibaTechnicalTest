package com.ingridsantos.ceibatechnicaltest.presentation.users.fragment

import android.os.SystemClock
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.ingridsantos.ceibatechnicaltest.R
import com.ingridsantos.ceibatechnicaltest.base.BaseUITest
import com.ingridsantos.ceibatechnicaltest.data.local.TechnicalTestRoomDatabase
import com.ingridsantos.ceibatechnicaltest.data.local.dao.UserDao
import com.ingridsantos.ceibatechnicaltest.data.local.entities.LocalUser
import com.ingridsantos.ceibatechnicaltest.idlresource.waitUntilViewIsNotDisplayed
import com.ingridsantos.ceibatechnicaltest.network.FILE_SUCCESS_GET_USERS
import com.ingridsantos.ceibatechnicaltest.network.mockResponse
import com.ingridsantos.ceibatechnicaltest.util.checkViewWithTextIsDisplayedAtPosition
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.QueueDispatcher
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.koin.core.component.inject
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4ClassRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UsersFragmentTest : BaseUITest(dispatcher = QueueDispatcher()) {

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    private val successGetUsers: MockResponse
        get() = mockResponse(FILE_SUCCESS_GET_USERS, HttpURLConnection.HTTP_OK)

    private val technicalTestRoomDatabase: TechnicalTestRoomDatabase by inject()

    private val userDao: UserDao by inject()

    private fun launchFragment() {
        launchFragmentInContainer(
            fragmentArgs = null,
            themeResId = R.style.Theme_CeibaTechnicalTest_NoActionBar
        ) {
            UsersFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }
    }

    @Before
    fun setup() {
        prepareData()
        launchFragment()
    }

    private fun prepareData() {
        val localUser = LocalUser(
            id = 1,
            username = "ingrid",
            phone = "123",
            email = "ims@gmail.com"
        )
        val listLocalUser = listOf(localUser)
        every { technicalTestRoomDatabase.userDao() } answers { userDao }
        coEvery { userDao.deleteAll() } answers { 1 }
        coEvery { userDao.insertAll(listLocalUser) } answers { listOf(1, 2, 3) }
        coEvery { userDao.getUsers() } answers { flowOf(listLocalUser) }
    }

    @Test
    @SmallTest
    fun whenResponseServiceIs200AndGetPosts() {
        enqueueResponses(successGetUsers)
        waitUntilViewIsNotDisplayed(ViewMatchers.withId(R.id.pgbUsers))
        checkViewWithTextIsDisplayedAtPosition(
            recyclerId = R.id.rcvUsers,
            position = 0,
            text = "Bret"
        )
        SystemClock.sleep(15000)
    }
}
