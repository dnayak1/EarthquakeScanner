package dhirajnayak.com.earthquakescanner.splashScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import dhirajnayak.com.earthquakescanner.R;
import dhirajnayak.com.earthquakescanner.homeScreen.HomeActivity;
import dhirajnayak.com.earthquakescanner.mapScreen.MapsActivity;
import dhirajnayak.com.earthquakescanner.model.GeoPlace;
import dhirajnayak.com.earthquakescanner.utility.Connection;
import dhirajnayak.com.earthquakescanner.utility.Constants;
import dhirajnayak.com.earthquakescanner.utility.IConnection;

public class SplashActivity extends AppCompatActivity implements ISplashActivityView {

    SplashActivityPresenter presenter;
    SpinKitView spinKitView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        spinKitView=findViewById(R.id.spin_kit);
        ISplashActivityView view=this;
        IConnection connection=new Connection(this);
        presenter=new SplashActivityPresenter(view,connection);
        performInit();
    }

    public void performInit(){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                presenter.isActiveConnection();
            }
        }, 3000);
    }

    //no internet connection
    @Override
    public void internetConnectionFailed() {
        Toast.makeText(this,"Please make sure you are connected to Internet",Toast.LENGTH_LONG).show();
        spinKitView.setVisibility(View.INVISIBLE);
    }

    //valid internet connection
    @Override
    public void internetConnectionSuccess() {
        Intent intent=new Intent(SplashActivity.this,HomeActivity.class);
        startActivity(intent);
    }


    //perform action everytime app restarts
    @Override
    protected void onRestart() {
        performInit();
        super.onRestart();
    }
}
