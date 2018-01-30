package dhirajnayak.com.earthquakescanner.customizeScreen;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.ybq.android.spinkit.SpinKitView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.Calendar;

import dhirajnayak.com.earthquakescanner.R;
import dhirajnayak.com.earthquakescanner.homeScreen.HomeActivity;
import dhirajnayak.com.earthquakescanner.homeScreen.HomeActivityPresenter;
import dhirajnayak.com.earthquakescanner.homeScreen.IHomeActivityPresenter;
import dhirajnayak.com.earthquakescanner.homeScreen.IHomeActivityView;
import dhirajnayak.com.earthquakescanner.mapScreen.MapsActivity;
import dhirajnayak.com.earthquakescanner.model.City;
import dhirajnayak.com.earthquakescanner.model.GeoPlace;
import dhirajnayak.com.earthquakescanner.searchScreen.SearchCityActivity;
import dhirajnayak.com.earthquakescanner.utility.Connection;
import dhirajnayak.com.earthquakescanner.utility.Constants;
import dhirajnayak.com.earthquakescanner.utility.IConnection;

/**
        * Created by dhirajnayak on 1/28/18.
        * pclass for responding to customized search
        */

public class CustomizeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener,View.OnClickListener,IHomeActivityView {

    Button pickCity,pickDate,pickTime;
    EditText textPickCity,textPickDate,textPickTime,textRadius;
    SpinKitView spin_kit_customize;
    DiscreteSeekBar seekbarRadius;
    FloatingActionButton send;
    String startTime,endTime,startDate,endDate;
    int radius=100;
    City city;
    IHomeActivityPresenter presenter;
    IHomeActivityView view;
    IConnection connection;
    Snackbar snackbar;
    View parentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);
        pickCity=findViewById(R.id.pickCity);
        textPickCity=findViewById(R.id.textPickCity);
        pickDate=findViewById(R.id.pickDate);
        textPickDate=findViewById(R.id.textPickDate);
        pickTime=findViewById(R.id.pickTime);
        textPickTime=findViewById(R.id.textPickTime);
        pickCity=findViewById(R.id.pickCity);
        textPickTime=findViewById(R.id.textPickTime);
        seekbarRadius=findViewById(R.id.seekbarRadius);
        textRadius=findViewById(R.id.textRadius);
        spin_kit_customize=findViewById(R.id.spin_kit_customize);
        send=findViewById(R.id.send);

        view=this;
        connection=new Connection(this);
        presenter=new HomeActivityPresenter(view,connection);
        parentView=findViewById(android.R.id.content);

        pickCity.setOnClickListener(this);
        pickDate.setOnClickListener(this);
        pickTime.setOnClickListener(this);
        send.setOnClickListener(this);

        //seebar listner
        seekbarRadius.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                radius=value;
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                textRadius.setText(String.valueOf(radius)+" miles");
            }
        });
    }

    //getting choose dtae fragment
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        int month=monthOfYear+1;
        int monthEnd=monthOfYearEnd+1;
        startDate=String.valueOf(year+"-"+month+"-"+dayOfMonth);
        endDate=String.valueOf(yearEnd+"-"+monthEnd+"-"+dayOfMonthEnd);
        textPickDate.setText(startDate+" to "+endDate);
    }

    //getting choose time fragment
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        startTime=String.valueOf(hourOfDay)+":"+String.valueOf(minute)+":"+"00";
        endTime=String.valueOf(hourOfDayEnd)+":"+String.valueOf(minuteEnd)+":"+"00";
        textPickTime.setText(startTime+" to "+endTime);
    }

    //onclick listener
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pickCity:
                Intent intent=new Intent(CustomizeActivity.this, SearchCityActivity.class);
                startActivityForResult(intent,Constants.CITY_KEY_REQ);

                break;
            case R.id.pickDate:
                Calendar dateNow = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CustomizeActivity.this,
                        dateNow.get(Calendar.YEAR),
                        dateNow.get(Calendar.MONTH),
                        dateNow.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.pickTime:
                Calendar timeNow = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        CustomizeActivity.this,
                        timeNow.get(Calendar.HOUR_OF_DAY),
                        timeNow.get(Calendar.MINUTE),
                        false
                );
                tpd.show(getFragmentManager(), "Timepickerdialog");
                break;
            case R.id.send:
                if(presenter.validateData(startDate,endDate,startTime,endTime,city)){
                    String startDateTime=startDate+"T"+startTime;
                    String endDateTime=endDate+"T"+endTime;
                    double distance=1.6*radius;
                    presenter.customizedGeoPlaces(startDateTime,endDateTime,city.getLat(),city.getLon(),String.valueOf(distance));
                }else{
                    Toast.makeText(CustomizeActivity.this,"Invalid data",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    //when city is selected
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        city= (City) data.getExtras().get(Constants.CITY_KEY);
        textPickCity.setText(city.getName());
    }

    //calling map activity
    @Override
    public void onGeoPlacesReceived(GeoPlace geoPlace) {
        Intent intent=new Intent(CustomizeActivity.this, MapsActivity.class);
        intent.putExtra(Constants.PLACES_KEY,geoPlace);
        startActivity(intent);
    }

    //loading spinkit
    @Override
    public void showLoading() {
        spin_kit_customize.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    //hiding spinkit
    @Override
    public void hideLoading() {
        spin_kit_customize.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void noInternetConnection() {
        snackbar=Snackbar.make(parentView,"No Internet Connection",Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    @Override
    protected void onResume() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onResume();
    }
}
