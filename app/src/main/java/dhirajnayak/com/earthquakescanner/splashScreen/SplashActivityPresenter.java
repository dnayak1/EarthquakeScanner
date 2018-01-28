package dhirajnayak.com.earthquakescanner.splashScreen;

import java.text.SimpleDateFormat;
import java.util.Date;

import dhirajnayak.com.earthquakescanner.homeScreen.GeoPlaceRepository;
import dhirajnayak.com.earthquakescanner.homeScreen.IGeoPlaceRepository;
import dhirajnayak.com.earthquakescanner.model.GeoPlace;
import dhirajnayak.com.earthquakescanner.utility.IConnection;


/**
 * Created by dhirajnayak on 1/9/18.
 * //class for interaction with view and repository
 */

public class SplashActivityPresenter implements ISplashActivityPresenter{
    ISplashActivityView view;
    IConnection connection;

    public SplashActivityPresenter(ISplashActivityView view, IConnection connection) {
        this.view = view;
        this.connection=connection;
    }

    @Override
    public void isActiveConnection(){
        if(connection.checkInternetConnection()){
            view.internetConnectionSuccess();
        }else{
            view.internetConnectionFailed();
        }
    }
}

