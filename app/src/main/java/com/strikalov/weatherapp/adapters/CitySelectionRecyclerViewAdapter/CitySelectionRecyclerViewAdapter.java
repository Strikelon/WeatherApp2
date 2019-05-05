package com.strikalov.weatherapp.adapters.CitySelectionRecyclerViewAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.strikalov.weatherapp.R;
import com.strikalov.weatherapp.model.entities.City;

import java.util.List;

/**
 *  Адаптер для RecyclerView в CitySelectionActivity, поддерживает клики по элементам, а так же
 *  свайпы влево или вправо для удаления элементов, благодаря интерфейсу CitySelectionTouchHelperAdapter
 */

public class CitySelectionRecyclerViewAdapter
        extends RecyclerView.Adapter<CitySelectionRecyclerViewAdapter.CityViewHolder>
        implements  CitySelectionTouchHelperAdapter{

    public interface OnItemListener{
        void onItemClicked(int position);
        void onSwipeItem(int position);
    }

    private List<City> cityList;

    private OnItemListener onItemListener;

    public CitySelectionRecyclerViewAdapter(List<City> cityList){
        this.cityList = cityList;
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public void setOnItemClickListener(OnItemListener onItemClickListener) {
        this.onItemListener = onItemClickListener;
    }

    public void setCityList(List<City> cityList){
        this.cityList = cityList;
    }

    @Override
    public void onItemDelete(int position) {
        if(onItemListener != null){
            onItemListener.onSwipeItem(position);
        }
    }

    public class CityViewHolder extends RecyclerView.ViewHolder{

        private TextView cityName;
        private ImageView cityImage;

        public CityViewHolder(View view){
            super(view);

            cityName = view.findViewById(R.id.city_name);
            cityImage = view.findViewById(R.id.city_image);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemListener != null){
                        onItemListener.onItemClicked(getAdapterPosition());
                    }
                }
            });
        }

        public void bind(int position){

            City city = cityList.get(position);

            cityName.setText(city.getCityName());

            if(city.isSelected()){
                cityImage.setImageResource(R.mipmap.ic_city_selected);
            }else {
                cityImage.setImageResource(R.mipmap.ic_city_not_selected);
            }
        }

    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_city_selection_item, viewGroup, false);

        return new CitySelectionRecyclerViewAdapter.CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder cityViewHolder, int i) {
        cityViewHolder.bind(i);
    }
}
