package dhirajnayak.com.earthquakescanner.searchScreen;

import java.util.ArrayList;

import dhirajnayak.com.earthquakescanner.model.City;


/**
 * Created by dhirajnayak on 1/26/18.
 * interface for setting recycler view in SearchCityActivity
 */

public interface ISearchCityView {
    public void setCityRecyclerView(ArrayList<City> cities);
}
