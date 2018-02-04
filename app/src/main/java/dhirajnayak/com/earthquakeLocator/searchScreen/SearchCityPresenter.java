package dhirajnayak.com.earthquakeLocator.searchScreen;

import java.util.ArrayList;

import dhirajnayak.com.earthquakeLocator.model.City;
import io.reactivex.Observable;

/**
 * Created by dhirajnayak on 1/26/18.
 * Search city presenter to load city search results into the view
 */

public class SearchCityPresenter {
    private ICityRepository repository;
    private ISearchCityView view;

    public SearchCityPresenter(ICityRepository repository, ISearchCityView view) {
        this.repository = repository;
        this.view = view;
    }

    //return list of cities based on search query
    public Observable<ArrayList<City>> loadCities(String searchText){
        return repository.dataFromNetwork(searchText);

    }


}
