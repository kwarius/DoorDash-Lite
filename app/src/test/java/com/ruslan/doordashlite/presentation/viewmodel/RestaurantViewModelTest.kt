package com.ruslan.doordashlite.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ruslan.doordashlite.CoroutineTestRule
import com.ruslan.doordashlite.data.DoorDashApi
import com.ruslan.doordashlite.data.apiclient.DoorDashApiClientImpl
import com.ruslan.doordashlite.constants.DoorDashConstants.DEFAULT_LAT
import com.ruslan.doordashlite.constants.DoorDashConstants.DEFAULT_LNG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class RestaurantViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutinesRule = CoroutineTestRule()

    @Mock
    lateinit var doorDashApiClientImpl : DoorDashApiClientImpl

    //Class to be tested
    private lateinit var restaurantViewModel: RestaurantViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    private var doorDashApi: DoorDashApi = mock()

    @Before
    fun setUp() {
        doorDashApiClientImpl = DoorDashApiClientImpl(doorDashApi)
        restaurantViewModel = RestaurantViewModel(doorDashApiClientImpl)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun getAllRestaurantsTest() =
        runBlockingTest(testDispatcher) {
            restaurantViewModel.getAllRestaurants()
            verify(doorDashApiClientImpl, times(1)).getAllRestaurants(DEFAULT_LAT,
                DEFAULT_LNG)
        }

    @Test
    fun fetchRestaurantDetailsTest() =
        runBlockingTest(testDispatcher) {
            restaurantViewModel.fetchRestaurantDetails(7)
            verify(doorDashApiClientImpl, times(1)).getRestaurantInfo(7)
        }

    private inline fun <reified T> mock(): T = Mockito.mock(T::class.java)
}