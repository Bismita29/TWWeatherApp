package tw.com.weatherapp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import tw.com.weatherapp.interactor.IWeatherServiceInteractor;
import tw.com.weatherapp.service.WeatherService;
import tw.com.weatherapp.ui.weatherdetail.WeatherDetailFragment;
import tw.com.weatherapp.ui.weatherdetail.WeatherDetailFragmentPresenter;

import static org.mockito.Mockito.mock;

public class MainActivityMockito {


    WeatherDetailFragmentPresenter presenter;
    @Mock
    WeatherDetailFragment view;
    @Mock
    IWeatherServiceInteractor interactor;

    @Before
    public void setup(){
        view = mock(WeatherDetailFragment.class);
        interactor = mock(IWeatherServiceInteractor.class);

    }

    @Test
    public void testGetLocation() throws Exception {
        //Mockito.when(presenter.getLocation())
    }

}
