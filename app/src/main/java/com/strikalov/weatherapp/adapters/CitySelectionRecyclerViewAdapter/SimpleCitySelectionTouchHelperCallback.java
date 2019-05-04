package com.strikalov.weatherapp.adapters.CitySelectionRecyclerViewAdapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * В этом классе фиксируются свайпы влево или вправо и передаются
 * в адаптер, который реализует интерфейс CitySelectionTouchHelperAdapter
 */
public class SimpleCitySelectionTouchHelperCallback extends ItemTouchHelper.Callback{

    private final CitySelectionTouchHelperAdapter mAdapter;

    public SimpleCitySelectionTouchHelperCallback(CitySelectionTouchHelperAdapter mAdapter){
        this.mAdapter = mAdapter;
    }


    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = 0;
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        mAdapter.onItemDelete(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {

        return true;
    }

}
