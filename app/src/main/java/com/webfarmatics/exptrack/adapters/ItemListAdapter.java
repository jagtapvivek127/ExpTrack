package com.webfarmatics.exptrack.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.webfarmatics.exptrack.R;
import com.webfarmatics.exptrack.bean.BeanItemStates;

import java.util.ArrayList;

public class ItemListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<BeanItemStates> itemsArrayList;

    public ItemListAdapter(Context context, ArrayList<BeanItemStates> itemsArrayList) {
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
            viewOne = inflater.inflate(R.layout.item_list_single_row, parent, false);
        }

        TextView tvItem = (TextView) viewOne.findViewById(R.id.tvItem);
        final ImageView imgLowPriority = (ImageView) viewOne.findViewById(R.id.imgLowPriority);
        final ImageView imgHighPriority = (ImageView) viewOne.findViewById(R.id.imgHighPriority);


        BeanItemStates item = itemsArrayList.get(position);

        String priority = item.getPriority();

        if (priority.equalsIgnoreCase("HIGH")) {
            imgHighPriority.setVisibility(View.VISIBLE);
            imgLowPriority.setImageResource(R.drawable.null_icon);
        } else if (priority.equalsIgnoreCase("LOW")) {
            imgHighPriority.setImageResource(R.drawable.null_icon);
            imgLowPriority.setVisibility(View.VISIBLE);
        }

        tvItem.setText(item.getType());

        return viewOne;
    }
}
