package com.strikalov.weatherapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.strikalov.weatherapp.R;
import com.strikalov.weatherapp.model.entities.City;

import java.util.List;

/**
 * Адаптер для RecyclerView, который находится на навигационной шторке в MainActivity
 * и содержит список избранных городов
 */
public class CityNavDrawerRecyclerViewAdapter
        extends RecyclerView.Adapter<CityNavDrawerRecyclerViewAdapter.CityViewHolder> {

    public interface OnItemClickListener{
        void onItemClicked(int position);
    }

    private List<City> cityList;

    private OnItemClickListener onItemClickListener;

    private int accentColor;

    public CityNavDrawerRecyclerViewAdapter(List<City> cityList, int accentColor){
        this.cityList = cityList;
        this.accentColor = accentColor;
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public class CityViewHolder extends RecyclerView.ViewHolder{

        private View itemView;
        private TextView cityName;

        public CityViewHolder(View view){
            super(view);

            itemView = view;
            cityName = view.findViewById(R.id.city_name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClicked(getAdapterPosition());
                    }
                }
            });
        }

        public void bind(int position){

            City city = cityList.get(position);

            cityName.setText(city.getCityName());

            if(city.isSelected()){
                itemView.setBackgroundColor(accentColor);
            }else {
                itemView.setBackground(null);
            }

        }

    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_city_nav_item, viewGroup, false);

        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder cityViewHolder, int i) {
        cityViewHolder.bind(i);
    }
}
