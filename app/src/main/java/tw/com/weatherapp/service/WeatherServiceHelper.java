package tw.com.weatherapp.service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tw.com.weatherapp.error.BaseURLEmptyException;
import tw.com.weatherapp.model.WeatherEntity;
import tw.com.weatherapp.network.ApiClientProvider;
import tw.com.weatherapp.network.IRestRequestListener;

public class WeatherServiceHelper {

    static String BASE_URL ="http://api.openweathermap.org";
    public static void fetchWeather(String city, String country, final IRestRequestListener restRequestListener){
        final String location = city+","+country;
        final String string ="";
        final WeatherService weatherService;
        try {
            weatherService = new ApiClientProvider(BASE_URL, null).provideApiClient(false).create(WeatherService.class);
            final Call<WeatherEntity> call = weatherService.fetchWeatherData(location,string);
            call.enqueue(new Callback<WeatherEntity>() {
                @Override
                public void onResponse(Call<WeatherEntity> call, Response<WeatherEntity> response) {
                    if(restRequestListener != null)
                        restRequestListener.onSuccess(response.body());
                }

                @Override
                public void onFailure(Call<WeatherEntity> call, Throwable t) {
                    if (restRequestListener != null)
                        restRequestListener.onFailure(t);
                }
            });

        } catch (BaseURLEmptyException e) {
            e.printStackTrace();
        }
    }
}
