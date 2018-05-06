package tw.com.weatherapp.ui.weatherdetail;

import tw.com.weatherapp.interactor.IWeatherServiceInteractor;
import tw.com.weatherapp.model.WeatherEntity;
import tw.com.weatherapp.network.IRestRequestListener;

/*
Presenter has instance of View and Interactor
 */
public class WeatherDetailFragmentPresenter {
    private final IWeatherServiceInteractor mWeatherServiceInteractor;
    private final WeatherDetailFragmentScreen mWeatherDetailFragmentScreen;

    //dependency injection : presenter need interator object to make api calls and presentor needs view objects to make view calls
    // passing the dependency or injecting the dependency
    public WeatherDetailFragmentPresenter(IWeatherServiceInteractor weatherServiceInteractor, WeatherDetailFragmentScreen weatherDetailFragmentScreen){
        mWeatherDetailFragmentScreen = weatherDetailFragmentScreen;
        mWeatherServiceInteractor = weatherServiceInteractor;
    }

    public void getLocation(double lat, double lng){
        mWeatherDetailFragmentScreen.showLoadingSpinner();
        mWeatherDetailFragmentScreen.getLocation(lat, lng);
    }

    public void getWeatherForLocation(String city, String country){
        mWeatherDetailFragmentScreen.showLoadingSpinner();
        mWeatherServiceInteractor.fetchWeatherData(city, country, new IRestRequestListener<WeatherEntity>() {
            @Override
            public void onSuccess(WeatherEntity weatherEntity) {
                mWeatherDetailFragmentScreen.dismissLoadingSpinner();
                mWeatherDetailFragmentScreen.onWeatherDetailReceived(weatherEntity);
            }

            @Override
            public void onFailure(Throwable t) {
                mWeatherDetailFragmentScreen.dismissLoadingSpinner();
                mWeatherDetailFragmentScreen.onError();
            }
        });
    }
}
