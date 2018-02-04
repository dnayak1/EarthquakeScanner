package dhirajnayak.com.earthquakeLocator.searchScreen;

import java.util.ArrayList;

import dhirajnayak.com.earthquakeLocator.model.City;


/**
 * Created by dhirajnayak on 1/26/18.
 * interface for setting recycler view in SearchCityActivity
 */

public interface ISearchCityView {
    void setCityRecyclerView(ArrayList<City> cities);
}
