package tw.com.weatherapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import tw.com.weatherapp.interactor.IWeatherServiceInteractor;
import tw.com.weatherapp.model.WeatherEntity;
import tw.com.weatherapp.network.IRestRequestListener;
import tw.com.weatherapp.ui.weatherdetail.WeatherDetailFragmentPresenter;
import tw.com.weatherapp.ui.weatherdetail.WeatherDetailFragmentScreen;

import static junit.framework.Assert.assertFalse;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherFragmentPresenterUnitTestMockito {

   private WeatherDetailFragmentPresenter weatherDetailFragmentPresenter;

    @Mock
    private WeatherDetailFragmentScreen view;

    @Mock
    private IWeatherServiceInteractor interactor;

    @Captor
    ArgumentCaptor<IRestRequestListener> argumentCaptor;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        weatherDetailFragmentPresenter = new WeatherDetailFragmentPresenter(interactor, view);
    }

    @Test
    public void testGetLocation() throws Exception {
        double lat = 111;
        double lng = 222;
        weatherDetailFragmentPresenter.getLocation(lat, lng);
        verify(view).showLoadingSpinner();
        verify(view).getLocation(lat, lng);
    }

    @Test
    public void testGetWeather_Success() throws Exception {
        final WeatherEntity entity = new WeatherEntity();


        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                IRestRequestListener restRequestListener = (IRestRequestListener) invocation.getArguments()[2];
                entity.setName("blah");
                restRequestListener.onSuccess(entity);
                return null;
            }
        }).when(interactor).fetchWeatherData(eq("bengaluru"), eq("india"), any(IRestRequestListener.class));

        assertThat(entity.getName() == null, is(true));

        weatherDetailFragmentPresenter.getWeatherForLocation("bengaluru", "india");
        verify(view).dismissLoadingSpinner();
        verify(view).onWeatherDetailReceived(entity);
        assertThat(entity.getName() == null, is(false));
        assertTrue(entity.getName().equalsIgnoreCase("blah"));
        assertFalse(entity.getName().equalsIgnoreCase("blahhh"));
        verify(interactor, times(1)).fetchWeatherData(eq("bengaluru"), eq("india"), any(IRestRequestListener.class));
    }

    @Test
    public void testGetWeather_Failure() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                IRestRequestListener restRequestListener = (IRestRequestListener) invocation.getArguments()[2];
                restRequestListener.onFailure(new Throwable());
                return null;
            }
        }).when(interactor).fetchWeatherData(eq("bengaluru"), eq("india"), any(IRestRequestListener.class));

        weatherDetailFragmentPresenter.getWeatherForLocation("bengaluru", "india");
        verify(view).dismissLoadingSpinner();
        verify(view).onError();
    }

    @Test
    public void testGetWeatherArgumentCaptor_Success(){
        WeatherEntity entity = new WeatherEntity();

        weatherDetailFragmentPresenter.getWeatherForLocation("bangalore","india");
        verify(interactor).fetchWeatherData(eq("bangalore"),eq("india"), argumentCaptor.capture());
        assertThat(entity.getName() == null, is(true));
        entity.setName("blah");
        argumentCaptor.getValue().onSuccess(entity);
        verify(view).dismissLoadingSpinner();
        assertThat(entity.getName() == null, is(false));
        assertTrue(entity.getName().equalsIgnoreCase("blah"));
        assertFalse(entity.getName().equalsIgnoreCase("blahhh"));
    }

    @Test
    public void testGetWeatherArgumentCaptor_Failure() {
        weatherDetailFragmentPresenter.getWeatherForLocation("bangalore", "india");
        verify(interactor).fetchWeatherData(eq("bangalore"), eq("india"), argumentCaptor.capture());
        argumentCaptor.getValue().onFailure(new Throwable());
        verify(view).dismissLoadingSpinner();
        verify(view).onError();
    }
}
