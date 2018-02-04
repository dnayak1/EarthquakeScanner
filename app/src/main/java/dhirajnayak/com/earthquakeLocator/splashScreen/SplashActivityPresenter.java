package dhirajnayak.com.earthquakeLocator.splashScreen;

import dhirajnayak.com.earthquakeLocator.utility.IConnection;


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

