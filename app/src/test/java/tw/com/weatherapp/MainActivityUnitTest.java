package tw.com.weatherapp;

import org.junit.Before;
import org.junit.Test;

import tw.com.weatherapp.interactor.IWeatherServiceInteractor;
import tw.com.weatherapp.model.WeatherEntity;
import tw.com.weatherapp.network.IRestRequestListener;
import tw.com.weatherapp.ui.weatherdetail.WeatherDetailFragmentPresenter;
import tw.com.weatherapp.ui.weatherdetail.WeatherDetailFragmentScreen;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

class WeatherFragmentPresenterUnitTest {

    private WeatherDetailFragmentPresenter weatherDetailFragmentPresenter;
    private FakeWeatherDetailFragmentScreen fakeWeatherDetailFragmentScreen;
    private FakeWeatherServiceInteractor fakeWeatherServiceInteractor;

    @Before
    public void setUp(){
        fakeWeatherDetailFragmentScreen = new FakeWeatherDetailFragmentScreen();
        fakeWeatherServiceInteractor = new FakeWeatherServiceInteractor();
        weatherDetailFragmentPresenter = new WeatherDetailFragmentPresenter(fakeWeatherServiceInteractor, fakeWeatherDetailFragmentScreen);
    }

    @Test
    public void testGetLocation() throws Exception {
        assertFalse(fakeWeatherDetailFragmentScreen.isGetLocationCalled);
        weatherDetailFragmentPresenter.getLocation(111, 222);
        assertTrue(fakeWeatherDetailFragmentScreen.isGetLocationCalled);
    }
    @Test
    public void testGetWeather_Success() throws Exception {
        fakeWeatherServiceInteractor.reset();
        fakeWeatherDetailFragmentScreen.reset();

        assertFalse(fakeWeatherDetailFragmentScreen.isWeatherDetailReceived);
        fakeWeatherServiceInteractor.isSuccess =true;
        weatherDetailFragmentPresenter.getWeatherForLocation("bengaluru", "karnataka");
        assertTrue(fakeWeatherDetailFragmentScreen.isSuccess);
        assertTrue(fakeWeatherDetailFragmentScreen.isWeatherDetailReceived);

    }

    @Test
    public void testGetWeather_Failure() throws Exception {
        fakeWeatherServiceInteractor.reset();
        fakeWeatherDetailFragmentScreen.reset();

        fakeWeatherServiceInteractor.isSuccess = false;
        weatherDetailFragmentPresenter.getWeatherForLocation("bengaluru", "karnataka");
        assertFalse(fakeWeatherDetailFragmentScreen.isWeatherDetailReceived);
        assertTrue(fakeWeatherDetailFragmentScreen.isFailed);

    }


    private class FakeWeatherDetailFragmentScreen implements WeatherDetailFragmentScreen {

        public boolean isGetLocationCalled = false;
        public boolean isWeatherDetailReceived = false;
        public boolean isFailed = false;
        public boolean isSuccess = false;

        @Override
        public void showLoadingSpinner() {

        }

        @Override
        public void dismissLoadingSpinner() {

        }

        @Override
        public void getLocation(double lat, double lng) {
            isGetLocationCalled = true;
        }

        @Override
        public void onWeatherDetailReceived(WeatherEntity weatherEntity) {
            isSuccess = true;
            if(weatherEntity != null)
                isWeatherDetailReceived = true;
        }

        @Override
        public void onError() {
            isFailed = true;
        }

        public void reset(){
            isGetLocationCalled = false;
            isWeatherDetailReceived = false;
            isFailed = false;
            isSuccess = false;
        }
    }

    private class FakeWeatherServiceInteractor implements IWeatherServiceInteractor{

        public boolean isSuccess;

        @Override
        public void fetchWeatherData(String city, String country, IRestRequestListener restRequestListener) {
            if(isSuccess)
                restRequestListener.onSuccess(new WeatherEntity());
            else
                restRequestListener.onFailure(new Throwable());
        }

        public void reset(){
            isSuccess = false;
        }
    }
}