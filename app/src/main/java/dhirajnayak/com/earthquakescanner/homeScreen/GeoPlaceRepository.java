package dhirajnayak.com.earthquakescanner.homeScreen;

import dhirajnayak.com.earthquakescanner.homeScreen.IGeoPlaceRepository;
import dhirajnayak.com.earthquakescanner.model.GeoPlace;
import dhirajnayak.com.earthquakescanner.splashScreen.ISplashActivityPresenter;
import dhirajnayak.com.earthquakescanner.utility.Constants;
import dhirajnayak.com.earthquakescanner.utility.IService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dhirajnayak on 1/27/18.
 * class for get earthquake affected places
 */

public class GeoPlaceRepository implements IGeoPlaceRepository {
    final GeoPlace place=new GeoPlace();
    IHomeActivityPresenter presenter;

    public GeoPlaceRepository(IHomeActivityPresenter presenter) {
        this.presenter = presenter;
    }

    //when start time and end time is provided
    @Override
    public void dataFromNetwork(String starttime,String endtime) {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IService service=retrofit.create(IService.class);
        Call<GeoPlace> call=service.getPlaces("geojson",starttime,endtime);
        call.enqueue(new Callback<GeoPlace>() {
            @Override
            public void onResponse(Call<GeoPlace> call, Response<GeoPlace> response) {
                place.setBbox(response.body().getBbox());
                place.setFeatures(response.body().getFeatures());
                place.setMetadata(response.body().getMetadata());
                place.setType(response.body().getType());
                presenter.onGeoPlacesReceived(place);
            }

            @Override
            public void onFailure(Call<GeoPlace> call, Throwable t) {

            }
        });
    }

    //when magnitued is added as extra parameter
    @Override
    public void dataFromNetworkWithMag(String starttime, String endtime, String minmagnitude) {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IService service=retrofit.create(IService.class);
        Call<GeoPlace> call=service.getPlacesWithMag("geojson",starttime,endtime,minmagnitude);
        call.enqueue(new Callback<GeoPlace>() {
            @Override
            public void onResponse(Call<GeoPlace> call, Response<GeoPlace> response) {
                place.setBbox(response.body().getBbox());
                place.setFeatures(response.body().getFeatures());
                place.setMetadata(response.body().getMetadata());
                place.setType(response.body().getType());
                presenter.onGeoPlacesReceived(place);
            }

            @Override
            public void onFailure(Call<GeoPlace> call, Throwable t) {

            }
        });
    }

    //when all parameters are provided
    @Override
    public void dataFromNetworkCustomized(String starttime, String endtime, String latitude, String longitude, String maxradiuskm) {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IService service=retrofit.create(IService.class);
        Call<GeoPlace> call=service.getCustomizedPlaces("geojson",starttime,endtime,latitude,longitude,maxradiuskm);
        call.enqueue(new Callback<GeoPlace>() {
            @Override
            public void onResponse(Call<GeoPlace> call, Response<GeoPlace> response) {
                place.setBbox(response.body().getBbox());
                place.setFeatures(response.body().getFeatures());
                place.setMetadata(response.body().getMetadata());
                place.setType(response.body().getType());
                presenter.onGeoPlacesReceived(place);
            }

            @Override
            public void onFailure(Call<GeoPlace> call, Throwable t) {

            }
        });
    }


}
