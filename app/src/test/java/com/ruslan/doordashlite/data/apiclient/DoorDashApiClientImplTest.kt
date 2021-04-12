package com.ruslan.doordashlite.data.apiclient

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ruslan.doordashlite.CoroutineTestRule
import com.ruslan.doordashlite.data.DoorDashApi
import com.ruslan.doordashlite.data.model.Restaurant
import com.ruslan.doordashlite.data.model.RestaurantDetails
import com.ruslan.doordashlite.constants.DoorDashConstants.DEFAULT_LAT
import com.ruslan.doordashlite.constants.DoorDashConstants.DEFAULT_LNG
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
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
import org.mockito.Mockito.`when`
import retrofit2.Response
import java.io.IOException
import javax.net.ssl.HttpsURLConnection

//todo add more coverage

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class DoorDashApiClientImplTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutinesRule = CoroutineTestRule()

    @Mock
    lateinit var doorDashApiClient: DoorDashApiClient

    @Mock
    private lateinit var restaurantList: List<Restaurant>

    @Mock
    private lateinit var restaurant: Restaurant

    @Mock
    private lateinit var restaurantResponse: Response<List<Restaurant>>

    @Mock
    private lateinit var restaurantDetailsResponse : Response<RestaurantDetails>

    private var doorDashApi: DoorDashApi = mock()

    private val testDispatcher = TestCoroutineDispatcher()


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        doorDashApiClient = DoorDashApiClientImpl(doorDashApi)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun getAllRestaurantsTest() {
        runBlockingTest(testDispatcher) {
            `when`(
                doorDashApi.fetchAllRestaurants(
                    DEFAULT_LAT,
                    DEFAULT_LNG
                )
            ).thenAnswer {restaurantList}
        }

        restaurantResponse = mockk()
        every { restaurantResponse.code() } returns HttpsURLConnection.HTTP_OK
        every { restaurantResponse.message() } returns ""
        every { restaurantResponse.errorBody() } returns null
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun fetchRestaurantTest() {
        runBlockingTest(testDispatcher) {
            `when`(
                doorDashApi.fetchRestaurant(3)
            ).thenAnswer {restaurant}
        }

        restaurantDetailsResponse = mockk()
        every { restaurantDetailsResponse.code() } returns HttpsURLConnection.HTTP_OK
        every { restaurantDetailsResponse.message() } returns ""
        every { restaurantDetailsResponse.errorBody() } returns null
    }

    private inline fun <reified T> mock(): T = Mockito.mock(T::class.java)
}
