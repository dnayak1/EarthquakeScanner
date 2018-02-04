package dhirajnayak.com.earthquakeLocator.homeScreen;

import dhirajnayak.com.earthquakeLocator.model.City;
import dhirajnayak.com.earthquakeLocator.model.GeoPlace;

/**
 * Created by dhirajnayak on 1/28/18.
 * interface for presenter
 */

public interface IHomeActivityPresenter {
    void getGeoPlaces(String startTime,String endTime);
    void getGeoPlacesWithMag(String startTime,String endTime, String minMagnitude);
    void customizedGeoPlaces(String startTime,String endTime, String latitude,String longitude,String maxradiuskm);
    void onGeoPlacesReceived(GeoPlace place);
    boolean validateData(String startDate, String endDate ,String startTime,String endTime, City city);
}
