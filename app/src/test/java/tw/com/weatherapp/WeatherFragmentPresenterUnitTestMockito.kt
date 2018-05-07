import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.invocation.InvocationOnMock
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.stubbing.Answer

import tw.com.weatherapp.interactor.IWeatherServiceInteractor
import tw.com.weatherapp.model.WeatherEntity
import tw.com.weatherapp.network.IRestRequestListener
import tw.com.weatherapp.ui.weatherdetail.WeatherDetailFragmentPresenter
import tw.com.weatherapp.ui.weatherdetail.WeatherDetailFragmentScreen

import junit.framework.Assert.assertFalse
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertTrue
import org.mockito.Matchers.any
import org.mockito.Matchers.eq
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.junit.Assert.assertThat
import org.mockito.Mockito.`when`


@RunWith(MockitoJUnitRunner::class)
class WeatherFragmentPresenterUnitTestMockito {

    private var weatherDetailFragmentPresenter: WeatherDetailFragmentPresenter? = null

    @Mock
    private val view: WeatherDetailFragmentScreen? = null

    @Mock
    private val interactor: IWeatherServiceInteractor? = null

    @Captor
    internal var argumentCaptor: ArgumentCaptor<IRestRequestListener<*>>? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        weatherDetailFragmentPresenter = WeatherDetailFragmentPresenter(interactor, view)
    }

    @Test
    @Throws(Exception::class)
    fun testGetLocation() {
        val lat = 111.0
        val lng = 222.0
        weatherDetailFragmentPresenter!!.getLocation(lat, lng)
        verify<WeatherDetailFragmentScreen>(view).showLoadingSpinner()
        verify<WeatherDetailFragmentScreen>(view).getLocation(lat, lng)
    }

    /* exception faced in kotlin
    @Test
    @Throws(Exception::class)
    fun testGetWeather_Success() {
        val entity = WeatherEntity()


        doAnswer(object : Answer<Any> {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Any? {
                val restRequestListener = invocation.arguments[2] as IRestRequestListener<*>
                entity.name = "blah"
                restRequestListener.onSuccess(entity)
                return null
            }
        }).`when`<IWeatherServiceInteractor>(interactor).fetchWeatherData(eq("bengaluru"), eq("india"), any(IRestRequestListener.java))

        assertThat(entity.name == null, `is`(true))

        weatherDetailFragmentPresenter!!.getWeatherForLocation("bengaluru", "india")
        verify<WeatherDetailFragmentScreen>(view).dismissLoadingSpinner()
        verify<WeatherDetailFragmentScreen>(view).onWeatherDetailReceived(entity)
        assertThat(entity.name == null, `is`(false))
        assertTrue(entity.name.equals("blah", ignoreCase = true))
        assertFalse(entity.name.equals("blahhh", ignoreCase = true))
        verify<IWeatherServiceInteractor>(interactor, times(1)).fetchWeatherData(eq("bengaluru"), eq("india"), any(IRestRequestListener<*>::class.java))
    }

    @Test
    @Throws(Exception::class)
    fun testGetWeather_Failure() {
        doAnswer(object : Answer {
            @Throws(Throwable::class)
            override fun answer(invocation: InvocationOnMock): Any? {
                val restRequestListener = invocation.arguments[2] as IRestRequestListener<*>
                restRequestListener.onFailure(Throwable())
                return null
            }
        }).`when`<IWeatherServiceInteractor>(interactor).fetchWeatherData(eq("bengaluru"), eq("india"), any(IRestRequestListener<*>::class.java))

        weatherDetailFragmentPresenter!!.getWeatherForLocation("bengaluru", "india")
        verify<WeatherDetailFragmentScreen>(view).dismissLoadingSpinner()
        verify<WeatherDetailFragmentScreen>(view).onError()
    }

    @Test
    fun testGetWeatherArgumentCaptor_Success() {
        val entity = WeatherEntity()

        weatherDetailFragmentPresenter!!.getWeatherForLocation("bangalore", "india")
        verify<IWeatherServiceInteractor>(interactor).fetchWeatherData(eq("bangalore"), eq("india"), argumentCaptor!!.capture())
        assertThat(entity.name == null, `is`(true))
        entity.name = "blah"
        argumentCaptor!!.value.onSuccess(entity)
        verify<WeatherDetailFragmentScreen>(view).dismissLoadingSpinner()
        assertThat(entity.name == null, `is`(false))
        assertTrue(entity.name.equals("blah", ignoreCase = true))
        assertFalse(entity.name.equals("blahhh", ignoreCase = true))
    }*/

    @Test
    fun testGetWeatherArgumentCaptor_Failure() {
        weatherDetailFragmentPresenter!!.getWeatherForLocation("bangalore", "india")
        verify<IWeatherServiceInteractor>(interactor).fetchWeatherData(eq("bangalore"), eq("india"), argumentCaptor!!.capture())
        argumentCaptor!!.value.onFailure(Throwable())
        verify<WeatherDetailFragmentScreen>(view).dismissLoadingSpinner()
        verify<WeatherDetailFragmentScreen>(view).onError()
    }
}