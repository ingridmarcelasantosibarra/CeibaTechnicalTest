package com.ingridsantos.ceibatechnicaltest.presentation.users.fragment

import android.os.SystemClock
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.SmallTest
import com.ingridsantos.ceibatechnicaltest.R
import com.ingridsantos.ceibatechnicaltest.base.BaseUITest
import com.ingridsantos.ceibatechnicaltest.data.local.TechnicalTestRoomDatabase
import com.ingridsantos.ceibatechnicaltest.data.local.dao.UserDao
import com.ingridsantos.ceibatechnicaltest.data.local.entities.LocalUser
import com.ingridsantos.ceibatechnicaltest.idlresource.waitUntilViewIsNotDisplayed
import com.ingridsantos.ceibatechnicaltest.network.FILE_SUCCESS_GET_POSTS
import com.ingridsantos.ceibatechnicaltest.network.mockResponse
import com.ingridsantos.ceibatechnicaltest.util.checkViewWithIdAndTextIsDisplayed
import com.ingridsantos.ceibatechnicaltest.util.checkViewWithTextIsDisplayedAtPosition
import io.mockk.every
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.QueueDispatcher
import org.junit.Before
import org.junit.Test
import org.koin.core.component.inject
import java.net.HttpURLConnection

class PostsFragmentTest : BaseUITest(dispatcher = QueueDispatcher()) {

    private val technicalTestRoomDatabase: TechnicalTestRoomDatabase by inject()
    private val userDao: UserDao by inject()
    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    private val successGetFastOrders: MockResponse
        get() = mockResponse(FILE_SUCCESS_GET_POSTS, HttpURLConnection.HTTP_OK)

    val bundle = bundleOf("userId" to 1)

    private fun launchFragment() {
        launchFragmentInContainer(
            fragmentArgs = bundle,
            themeResId = R.style.Theme_CeibaTechnicalTest_NoActionBar
        ) {
            PostsFragment().also { fragment ->
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
        val user = LocalUser(
            id = 1,
            username = "ingrid",
            phone = "123456",
            email = "marcesntos@gmail.com"
        )
        every { technicalTestRoomDatabase.userDao() } returns userDao
        every { userDao.getInfoUser(1) } returns flowOf(user)
    }

    @Test
    @SmallTest
    fun whenResponseServiceIs200AndGetPosts() {
        enqueueResponses(successGetFastOrders)
        waitUntilViewIsNotDisplayed(withId(R.id.pgbPosts))
        checkViewWithTextIsDisplayedAtPosition(
            recyclerId = R.id.rcvPost,
            position = 0,
            text = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit"
        )
        SystemClock.sleep(15000)
    }
}
