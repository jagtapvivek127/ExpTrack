package com.webfarmatics.exptrack.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.webfarmatics.exptrack.R;
import com.webfarmatics.exptrack.bean.BeanItemStates;

import java.util.ArrayList;

public class GraphListAdapter extends BaseAdapter {

    private static final String TAG = "GraphListAdapter";

    private Context context;
    private ArrayList<BeanItemStates> itemsArrayList;

    public GraphListAdapter(Context context, ArrayList<BeanItemStates> itemsArrayList) {
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public int getCount() {
        return itemsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View viewOne = convertView;

        if (convertView == null) { // if it's not recycled, initialize some
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewOne = inflater.inflate(R.layout.item_graph_single_row, parent, false);
        }

        TextView tvType = (TextView) viewOne.findViewById(R.id.tvType);
        TextView tvNoOfTrans = (TextView) viewOne.findViewById(R.id.tvNoOfTrans);
        TextView tvNoMoneySpend = (TextView) viewOne.findViewById(R.id.tvNoMoneySpend);

        BeanItemStates item = itemsArrayList.get(position);

        tvType.setText(item.getType());
        tvNoOfTrans.setText("Count : "+item.getNoOfTrans());
        tvNoMoneySpend.setText("Rs."+item.getMoneySpend());

        return viewOne;
    }
}
