package dhirajnayak.com.earthquakescanner.homeScreen;

import dhirajnayak.com.earthquakescanner.model.GeoPlace;

/**
 * Created by dhirajnayak on 1/28/18.
 * interface for view
 */

public interface IHomeActivityView {
    public void onGeoPlacesReceived(GeoPlace geoPlace);
    public void showLoading();
    public void hideLoading();
}
