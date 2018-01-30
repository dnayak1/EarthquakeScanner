package dhirajnayak.com.earthquakescanner.homeScreen;

import dhirajnayak.com.earthquakescanner.model.GeoPlace;

/**
 * Created by dhirajnayak on 1/28/18.
 * interface for view
 */

public interface IHomeActivityView {
    void onGeoPlacesReceived(GeoPlace geoPlace);
    void showLoading();
    void hideLoading();
    void noInternetConnection();
}
