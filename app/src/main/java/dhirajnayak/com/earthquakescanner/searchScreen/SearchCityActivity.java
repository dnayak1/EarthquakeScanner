package dhirajnayak.com.earthquakescanner.searchScreen;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import dhirajnayak.com.earthquakescanner.mapScreen.MapsActivity;
import dhirajnayak.com.earthquakescanner.R;
import dhirajnayak.com.earthquakescanner.model.City;
import dhirajnayak.com.earthquakescanner.utility.Connection;
import dhirajnayak.com.earthquakescanner.utility.Constants;
import dhirajnayak.com.earthquakescanner.utility.IConnection;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class SearchCityActivity extends AppCompatActivity implements ISearchCityView, CityAdapter.ICityListener,CityRepository.INotifyAdapter {

    //Global Variables
    SearchCityPresenter presenter;
    RecyclerView recyclerView;
    CityAdapter adapter;
    LinearLayoutManager layoutManager;
    DividerItemDecoration dividerItemDecoration;
    IConnection connection;
    Snackbar snackbar;
    View parentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        recyclerView=findViewById(R.id.cityRecyclerView);

        //Initializing repository,view,presenter
        ICityRepository repository=new CityRepository(this);
        ISearchCityView view=this;
        presenter=new SearchCityPresenter(repository,view);
        connection=new Connection(this);
        parentView=findViewById(android.R.id.content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.searchMenu);

        //setting the focus on the search view
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setQueryHint("start searching...");

        //Making search view reactive
        SearchViewObservable.fireSuggestions(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        if(s.isEmpty())
                            return false;
                        return true;
                    }
                })
                .distinctUntilChanged()
                .switchMap(new Function<String, ObservableSource<ArrayList<City>>>() {
                    @Override
                    public ObservableSource<ArrayList<City>> apply(String s) throws Exception {
                        return presenter.loadCities(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<City>>() {
                    @Override
                    public void accept(ArrayList<City> cities) throws Exception {
                        setCityRecyclerView(cities);
                    }
                });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setCityRecyclerView(ArrayList<City> cities) {
        adapter=new CityAdapter(SearchCityActivity.this,cities,SearchCityActivity.this);
        recyclerView.setAdapter(adapter);
        layoutManager=new LinearLayoutManager(SearchCityActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setSelectedCity(City selectedCity) {
        Intent intent=new Intent();
        intent.putExtra(Constants.CITY_KEY,selectedCity);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        if(!connection.checkInternetConnection()){
            snackbar= Snackbar.make(parentView,"No Internet Connection!",Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }
        super.onResume();
    }
}
