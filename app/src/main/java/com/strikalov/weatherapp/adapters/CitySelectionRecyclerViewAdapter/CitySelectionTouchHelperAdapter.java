package com.strikalov.weatherapp.adapters.CitySelectionRecyclerViewAdapter;

/**
 * Интерфейс, который используется для recyclerview адаптера, чтобы
 * по свайпу влево или вправо удалять элементы из списка
 */
public interface CitySelectionTouchHelperAdapter {
    void onItemDelete(int position);
}
