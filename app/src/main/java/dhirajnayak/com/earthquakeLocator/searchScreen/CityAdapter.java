package dhirajnayak.com.earthquakeLocator.searchScreen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dhirajnayak.com.earthquakeLocator.R;
import dhirajnayak.com.earthquakeLocator.model.City;

/**
 * Created by dhirajnayak on 1/26/17.
 * adapter for setting city search results
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {
    ArrayList<City> cityArrayList=new ArrayList<>();
    Context mContext;
    private ICityListener cityListener;

    public CityAdapter(Context mContext, ArrayList<City> cityArrayList, ICityListener cityListener) {
        this.cityArrayList = cityArrayList;
        this.mContext = mContext;
        this.cityListener = cityListener;

    }

    //inflating the layout
    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.city_layout,parent,false);
        CityAdapter.CityViewHolder idRecyclerViewHolder=new CityAdapter.CityViewHolder(view);
        return idRecyclerViewHolder;
    }

    //binding data to fields
    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        final City city=cityArrayList.get(position);

        holder.textViewCity.setText(city.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityListener.setSelectedCity(city);
            }
        });
    }

    //return size of list
    @Override
    public int getItemCount() {
        return cityArrayList.size();
    }

    //creating fields per row
    public static class CityViewHolder extends RecyclerView.ViewHolder{

        TextView textViewCity;

        public CityViewHolder(View itemView) {
            super(itemView);
            textViewCity= (TextView) itemView.findViewById(R.id.textViewCity);
        }
    }

    //method called for user action
    interface ICityListener
    {
        void setSelectedCity(City selectedCity);
    }

}
