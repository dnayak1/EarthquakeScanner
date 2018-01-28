package dhirajnayak.com.earthquakescanner.homeScreen;

import dhirajnayak.com.earthquakescanner.model.City;
import dhirajnayak.com.earthquakescanner.model.GeoPlace;

/**
 * Created by dhirajnayak on 1/28/18.
 * presenter for interacting with view and repository
 */

public class HomeActivityPresenter implements IHomeActivityPresenter {
    IHomeActivityView view;
    IGeoPlaceRepository repository;

    public HomeActivityPresenter(IHomeActivityView view) {
        this.view = view;
        repository=new GeoPlaceRepository(this);
    }

    //querying with different parameters
    @Override
    public void getGeoPlaces(String startTime,String endTime) {
        view.showLoading();
        repository.dataFromNetwork(startTime,endTime);
    }

    //querying with different parameters
    @Override
    public void getGeoPlacesWithMag(String startTime, String endTime, String minMagnitude) {
        view.showLoading();
        repository.dataFromNetworkWithMag(startTime,endTime,minMagnitude);
    }

    //querying with different parameters
    @Override
    public void customizedGeoPlaces(String startTime, String endTime, String latitude, String longitude, String maxradiuskm) {
        view.showLoading();
        repository.dataFromNetworkCustomized(startTime,endTime,latitude,longitude,maxradiuskm);
    }

    //service successfully returns the response
    @Override
    public void onGeoPlacesReceived(GeoPlace place) {
        view.hideLoading();
        view.onGeoPlacesReceived(place);
    }

    //validating input data
    @Override
    public boolean validateData(String startDate, String endDate, String startTime, String endTime, City city) {
        if(startDate!=null && !startDate.isEmpty()&&
                endDate!=null && !endDate.isEmpty() &&
                startTime!=null && !startTime.isEmpty() &&
                endTime!=null && !endTime.isEmpty() &&
                city!=null){
            return true;
        }
        return false;
    }
}
