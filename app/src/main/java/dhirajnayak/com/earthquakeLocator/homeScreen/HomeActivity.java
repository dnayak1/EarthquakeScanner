package dhirajnayak.com.earthquakeLocator.homeScreen;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;

import com.github.ybq.android.spinkit.SpinKitView;

import java.text.SimpleDateFormat;
import java.util.Date;

import dhirajnayak.com.earthquakeLocator.R;
import dhirajnayak.com.earthquakeLocator.customizeScreen.CustomizeActivity;
import dhirajnayak.com.earthquakeLocator.mapScreen.MapsActivity;
import dhirajnayak.com.earthquakeLocator.model.GeoPlace;
import dhirajnayak.com.earthquakeLocator.utility.Connection;
import dhirajnayak.com.earthquakeLocator.utility.Constants;
import dhirajnayak.com.earthquakeLocator.utility.IConnection;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,IHomeActivityView{
    //variables
    CardView oneHrCard,twelveHrCard,twentyFourHrCard,twentyFourHrMagCard,customizeCard,usgsCard;
    IHomeActivityPresenter presenter;
    IHomeActivityView view;
    IConnection connection;
    SpinKitView spinKitHome;
    View parentView;
    Snackbar snackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        oneHrCard=findViewById(R.id.oneHrCard);
        twelveHrCard=findViewById(R.id.twelveHrCard);
        twentyFourHrCard=findViewById(R.id.twentyFourHrCard);
        twentyFourHrMagCard=findViewById(R.id.twentyFourHrMagCard);
        customizeCard=findViewById(R.id.customizeCard);
        usgsCard=findViewById(R.id.usgsCard);
        spinKitHome=findViewById(R.id.spin_kit_home);
        parentView=findViewById(android.R.id.content);

        view=this;
        connection=new Connection(this);
        presenter=new HomeActivityPresenter(view,connection);


        oneHrCard.setOnClickListener(this);
        twelveHrCard.setOnClickListener(this);
        twentyFourHrCard.setOnClickListener(this);
        twentyFourHrMagCard.setOnClickListener(this);
        customizeCard.setOnClickListener(this);
        usgsCard.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String startTime,endTime;
        switch (view.getId()){
            case R.id.oneHrCard:
                startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date(System.currentTimeMillis() - 3600 * 1000));
                endTime=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
                presenter.getGeoPlaces(startTime,endTime);
                break;
            case R.id.twelveHrCard:
                startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date(System.currentTimeMillis() - 12 * 3600 * 1000));
                endTime=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
                presenter.getGeoPlaces(startTime,endTime);
                break;
            case R.id.twentyFourHrCard:
                startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date(System.currentTimeMillis() - 24 * 3600 * 1000));
                endTime=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
                presenter.getGeoPlaces(startTime,endTime);
                break;
            case R.id.twentyFourHrMagCard:
                startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date(System.currentTimeMillis() - 24 * 3600 * 1000));
                endTime=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
                presenter.getGeoPlacesWithMag(startTime,endTime,"5");
                break;
            case R.id.customizeCard:
                Intent customizeIntent=new Intent(HomeActivity.this, CustomizeActivity.class);
                startActivity(customizeIntent);
                break;
            case R.id.usgsCard:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(Constants.USGS_URL));
                startActivity(intent);
        }
    }

    //when data is returned
    @Override
    public void onGeoPlacesReceived(GeoPlace geoPlace) {
        Intent intent=new Intent(HomeActivity.this, MapsActivity.class);
        intent.putExtra(Constants.PLACES_KEY,geoPlace);
        startActivity(intent);
    }

    //showing spinkit
    @Override
    public void showLoading() {
        spinKitHome.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    //hiding spinkit
    @Override
    public void hideLoading() {
        spinKitHome.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    //no Connection
    @Override
    public void noInternetConnection() {
        snackbar=Snackbar.make(parentView,"No Internet Connection!",Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
        spinKitHome.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void dataErrorOccurred() {
        snackbar=Snackbar.make(parentView,"Error Occurred. Try Again!",Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
        spinKitHome.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    //exit if back is pressed
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        connection.checkInternetConnection();
        super.onResume();
    }
}
