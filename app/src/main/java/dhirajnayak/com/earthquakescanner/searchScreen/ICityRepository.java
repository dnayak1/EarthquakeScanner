package dhirajnayak.com.earthquakescanner.searchScreen;



import java.util.ArrayList;


import dhirajnayak.com.earthquakescanner.model.City;
import io.reactivex.Observable;

/**
 * Created by dhirajnayak on 1/26/18.
 * contains method for getting data from network
 */

public interface ICityRepository {
    Observable<ArrayList<City>> dataFromNetwork(String searchText);
}
