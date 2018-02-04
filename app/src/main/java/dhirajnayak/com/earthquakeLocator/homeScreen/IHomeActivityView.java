package dhirajnayak.com.earthquakeLocator.homeScreen;

import dhirajnayak.com.earthquakeLocator.model.GeoPlace;

/**
 * Created by dhirajnayak on 1/28/18.
 * interface for view
 */

public interface IHomeActivityView {
    void onGeoPlacesReceived(GeoPlace geoPlace);
    void showLoading();
    void hideLoading();
    void noInternetConnection();
    void dataErrorOccurred();
}
