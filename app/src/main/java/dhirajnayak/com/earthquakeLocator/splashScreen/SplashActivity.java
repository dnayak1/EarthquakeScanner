package dhirajnayak.com.earthquakeLocator.splashScreen;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.ybq.android.spinkit.SpinKitView;

import dhirajnayak.com.earthquakeLocator.R;
import dhirajnayak.com.earthquakeLocator.homeScreen.HomeActivity;
import dhirajnayak.com.earthquakeLocator.utility.Connection;
import dhirajnayak.com.earthquakeLocator.utility.IConnection;

public class SplashActivity extends AppCompatActivity implements ISplashActivityView, View.OnClickListener {

    private SplashActivityPresenter presenter;
    private SpinKitView spinKitView;
    private Snackbar snackbar;
    private View parentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        spinKitView=findViewById(R.id.spin_kit);
        parentView=findViewById(android.R.id.content);

        ISplashActivityView view=this;
        IConnection connection=new Connection(this);
        presenter=new SplashActivityPresenter(view,connection);
        performInit();
    }

    public void performInit(){
        spinKitView.setVisibility(View.VISIBLE);
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
        snackbar=Snackbar.make(parentView,"No Internet Connection!",Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("RETRY", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performInit();
            }
        });
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
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

    @Override
    public void onClick(View view) {

    }
}
