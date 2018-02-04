package dhirajnayak.com.earthquakeLocator.utility;

import java.util.List;

import dhirajnayak.com.earthquakeLocator.model.City;
import dhirajnayak.com.earthquakeLocator.model.GeoPlace;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dhirajnayak on 1/26/18.
 * method for different network calls
 */

public interface IService {
    //network call for searching city.
    @GET("search.json")
    Call<List<City>> getLocationData(@Query("key") String apiKey,
                                     @Query("q") String searchText);
    //network call for getting earthquake zones based on different parameters
    @GET("query")
    Call<GeoPlace> getPlaces(@Query("format") String format,
                             @Query("starttime") String starttime,
                             @Query("endtime") String endtime);
    @GET("query")
    Call<GeoPlace> getPlacesWithMag(@Query("format") String format,
                                    @Query("starttime") String starttime,
                                    @Query("endtime") String endtime,
                                    @Query("minmagnitude") String minmagnitude);

    @GET("query")
    Call<GeoPlace> getCustomizedPlaces(@Query("format") String format,
                                       @Query("starttime") String starttime,
                                       @Query("endtime") String endtime,
                                       @Query("latitude") String latitude,
                                       @Query("longitude")String longitude,
                                       @Query("maxradiuskm")String maxradiuskm);
}
