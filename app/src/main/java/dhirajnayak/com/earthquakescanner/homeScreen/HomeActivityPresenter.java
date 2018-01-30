package dhirajnayak.com.earthquakescanner.homeScreen;

import dhirajnayak.com.earthquakescanner.model.City;
import dhirajnayak.com.earthquakescanner.model.GeoPlace;
import dhirajnayak.com.earthquakescanner.utility.IConnection;

/**
 * Created by dhirajnayak on 1/28/18.
 * presenter for interacting with view and repository
 */

public class HomeActivityPresenter implements IHomeActivityPresenter {
    private IHomeActivityView view;
    private IGeoPlaceRepository repository;
    private IConnection connection;

    public HomeActivityPresenter(IHomeActivityView view,IConnection connection) {
        this.view = view;
        repository=new GeoPlaceRepository(this);
        this.connection=connection;
    }

    //querying with different parameters
    @Override
    public void getGeoPlaces(String startTime,String endTime) {
        view.showLoading();
        if(connection.checkInternetConnection()){
            repository.dataFromNetwork(startTime,endTime);
        }else{
            view.noInternetConnection();
        }

    }

    //querying with different parameters
    @Override
    public void getGeoPlacesWithMag(String startTime, String endTime, String minMagnitude) {
        view.showLoading();
        if(connection.checkInternetConnection()) {
            repository.dataFromNetworkWithMag(startTime, endTime, minMagnitude);
        }else{
            view.noInternetConnection();
        }
    }

    //querying with different parameters
    @Override
    public void customizedGeoPlaces(String startTime, String endTime, String latitude, String longitude, String maxradiuskm) {
        view.showLoading();
        if(connection.checkInternetConnection()) {
            repository.dataFromNetworkCustomized(startTime,endTime,latitude,longitude,maxradiuskm);
        }else{
            view.noInternetConnection();
        }
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

    public void OnDataError(){
        view.dataErrorOccurred();
    }
}
