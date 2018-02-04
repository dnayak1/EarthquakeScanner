package dhirajnayak.com.earthquakeLocator.searchScreen;

import java.util.ArrayList;
import java.util.List;

import dhirajnayak.com.earthquakeLocator.model.City;
import dhirajnayak.com.earthquakeLocator.utility.IService;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static dhirajnayak.com.earthquakeLocator.utility.Constants.API_KEY;
import static dhirajnayak.com.earthquakeLocator.utility.Constants.BASE_URL_CITY;


/**
 * Created by dhirajnayak on 1/26/18.
 * method implementation for getting data from network
 */

public class CityRepository implements ICityRepository {

    //passing adapter for updating whenever result changes
    INotifyAdapter notifyAdapter;

    public CityRepository(INotifyAdapter notifyAdapter) {
        this.notifyAdapter = notifyAdapter;
    }

    //getting results from network
    @Override
    public Observable<ArrayList<City>> dataFromNetwork(String searchText) {
        final ArrayList<City> cities = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_CITY)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IService service = retrofit.create(IService.class);
        Call<List<City>> call = service.getLocationData(API_KEY,searchText);
        call.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                if(response.body() != null){
                    cities.addAll(response.body());
                }
                notifyAdapter.notifyAdapter();
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {

            }
        });
        return Observable.just(cities);
    }

    public interface INotifyAdapter{
        public void notifyAdapter();
    }
}
