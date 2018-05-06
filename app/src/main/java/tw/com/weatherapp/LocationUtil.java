package tw.com.weatherapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import tw.com.weatherapp.model.Location;

//synchronous call : we cannot make it on UI thread
public class LocationUtil {
    public static Location getLocation(Context context, double latitude, double longitude){
        final Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        final Location location = new Location();

        try {
            final List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            location.setCity(addresses.get(0).getLocality());
            location.setCountry(addresses.get(0).getCountryName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }


}
