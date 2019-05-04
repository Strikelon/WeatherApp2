package com.strikalov.weatherapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.strikalov.weatherapp.model.entities.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Адаптер, который используется в CitySelectionActivity для AutoCompleteTextView.
 * Содержит список объектов City и при получении новой последовательности символов от AutoCompleteTextView
 * фильтрует список
 */
public class CityAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private final Context context;
    private List<City> cityList;
    private List<City> cityResult;


    public CityAutoCompleteAdapter(Context context, List<City> cityList){
        this.context = context;
        this.cityList = cityList;
    }

    @Override
    public int getCount() {
        return cityResult.size();
    }

    @Override
    public City getItem(int index) {
        return cityResult.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }

        City city = getItem(position);

        TextView text = (TextView) convertView;

        text.setText(city.getCityName());

        return convertView;
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint != null && constraint.toString().length() > 0){
                    List<City> cities = findCities(constraint.toString());
                    filterResults.values = cities;
                    filterResults.count = cities.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    cityResult = (List<City>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }

    private List<City> findCities(String cityName) {

        String prefixString = cityName.toLowerCase();

        int count = cityList.size();
        List<City> newValues = new ArrayList<>();

        for (int i = 0; i < count; i++){
            City value = cityList.get(i);
            String valueText = value.getCityName().toLowerCase();

            if (valueText.startsWith(prefixString)) {
                newValues.add(value);
            }else {
                String[] words = valueText.split(" ");
                for (String word : words) {
                    if (word.startsWith(prefixString)) {
                        newValues.add(value);
                        break;
                    }
                }
            }

        }

        return newValues;

    }
}
