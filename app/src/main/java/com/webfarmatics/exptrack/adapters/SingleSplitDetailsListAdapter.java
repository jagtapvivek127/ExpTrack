package com.webfarmatics.exptrack.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.webfarmatics.exptrack.R;
import com.webfarmatics.exptrack.bean.BeanSplitFinalHelper;
import com.webfarmatics.exptrack.bean.BeanSplitHelper;
import com.webfarmatics.exptrack.utils.CommonUtil;

import java.util.ArrayList;


public class SingleSplitDetailsListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<BeanSplitFinalHelper> listHelper;


    public SingleSplitDetailsListAdapter(Context context, ArrayList<BeanSplitFinalHelper> listHelper) {
        this.context = context;
        this.listHelper = listHelper;
    }

    @Override
    public int getCount() {
        return listHelper.size();
    }

    @Override
    public Object getItem(int position) {
        return listHelper.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewOne = convertView;

        if (convertView == null) { // if it's not recycled, initialize some
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewOne = inflater.inflate(R.layout.single_split_details_single_row, parent, false);
        }

        BeanSplitFinalHelper helper = listHelper.get(position);

        String title = helper.getTitle();
        String date = helper.getDate();

        TextView tvSplitTitle = viewOne.findViewById(R.id.tvSplitTitle);
        TextView tvHeaderDate = (TextView) viewOne.findViewById(R.id.tvHeaderDate);
        TextView tvHeaderMonth = (TextView) viewOne.findViewById(R.id.tvHeaderMonth);
        TextView tvHeaderYear = (TextView) viewOne.findViewById(R.id.tvHeaderYear);

        tvSplitTitle.setText(title);

        ArrayList<String> dmy = CommonUtil.getDMY(date);
        String year = dmy.get(0);
        String month = dmy.get(1);
        String day = dmy.get(2);

        tvHeaderDate.setText(day);
        tvHeaderMonth.setText(month);
        tvHeaderYear.setText(year);

        ArrayList<BeanSplitHelper> splitHelpersList = helper.getSplitHelpersList();

        int size = splitHelpersList.size();

        int count = 0;

        for (int i = 0; i < splitHelpersList.size(); i++) {

            BeanSplitHelper statesByDate = splitHelpersList.get(i);
            String name = statesByDate.getParticipant();
            String money = statesByDate.getOwesYou();


            Log.e("adapterJoker", "name : " + name + " money : " + money);

            setViewDetails(count, viewOne, name, money);

            count++;
        }

        hideOtherViews(splitHelpersList.size(), viewOne);

        return viewOne;
    }

    private void hideOtherViews(int size, View viewOne) {

        for (int j = size; j < 10; j++) {

            if (j == 0) {

                View v1 = viewOne.findViewById(R.id.v1);
                View v2 = viewOne.findViewById(R.id.v2);
                View v3 = viewOne.findViewById(R.id.v3);
                View v4 = viewOne.findViewById(R.id.v4);
                View v5 = viewOne.findViewById(R.id.v5);
                View v6 = viewOne.findViewById(R.id.v6);
                View v7 = viewOne.findViewById(R.id.v7);
                View v8 = viewOne.findViewById(R.id.v8);
                View v9 = viewOne.findViewById(R.id.v9);
                View v11 = viewOne.findViewById(R.id.v11);

                v1.setVisibility(View.GONE);
                v2.setVisibility(View.GONE);
                v3.setVisibility(View.GONE);
                v4.setVisibility(View.GONE);
                v5.setVisibility(View.GONE);
                v6.setVisibility(View.GONE);
                v7.setVisibility(View.GONE);
                v8.setVisibility(View.GONE);
                v9.setVisibility(View.GONE);
                v11.setVisibility(View.GONE);

            }
            if (j == 1) {

                View v2 = viewOne.findViewById(R.id.v2);
                View v3 = viewOne.findViewById(R.id.v3);
                View v4 = viewOne.findViewById(R.id.v4);
                View v5 = viewOne.findViewById(R.id.v5);
                View v6 = viewOne.findViewById(R.id.v6);
                View v7 = viewOne.findViewById(R.id.v7);
                View v8 = viewOne.findViewById(R.id.v8);
                View v9 = viewOne.findViewById(R.id.v9);
                View v11 = viewOne.findViewById(R.id.v11);


                v2.setVisibility(View.GONE);
                v3.setVisibility(View.GONE);
                v4.setVisibility(View.GONE);
                v5.setVisibility(View.GONE);
                v6.setVisibility(View.GONE);
                v7.setVisibility(View.GONE);
                v8.setVisibility(View.GONE);
                v9.setVisibility(View.GONE);
                v11.setVisibility(View.GONE);
            }
            if (j == 2) {

                View v3 = viewOne.findViewById(R.id.v3);
                View v4 = viewOne.findViewById(R.id.v4);
                View v5 = viewOne.findViewById(R.id.v5);
                View v6 = viewOne.findViewById(R.id.v6);
                View v7 = viewOne.findViewById(R.id.v7);
                View v8 = viewOne.findViewById(R.id.v8);
                View v9 = viewOne.findViewById(R.id.v9);
                View v11 = viewOne.findViewById(R.id.v11);


                v3.setVisibility(View.GONE);
                v4.setVisibility(View.GONE);
                v5.setVisibility(View.GONE);
                v6.setVisibility(View.GONE);
                v7.setVisibility(View.GONE);
                v8.setVisibility(View.GONE);
                v9.setVisibility(View.GONE);
                v11.setVisibility(View.GONE);
            }
            if (j == 3) {

                View v4 = viewOne.findViewById(R.id.v4);
                View v5 = viewOne.findViewById(R.id.v5);
                View v6 = viewOne.findViewById(R.id.v6);
                View v7 = viewOne.findViewById(R.id.v7);
                View v8 = viewOne.findViewById(R.id.v8);
                View v9 = viewOne.findViewById(R.id.v9);
                View v11 = viewOne.findViewById(R.id.v11);

                v4.setVisibility(View.GONE);
                v5.setVisibility(View.GONE);
                v6.setVisibility(View.GONE);
                v7.setVisibility(View.GONE);
                v8.setVisibility(View.GONE);
                v9.setVisibility(View.GONE);
                v11.setVisibility(View.GONE);
            }
            if (j == 4) {
                View v5 = viewOne.findViewById(R.id.v5);
                View v6 = viewOne.findViewById(R.id.v6);
                View v7 = viewOne.findViewById(R.id.v7);
                View v8 = viewOne.findViewById(R.id.v8);
                View v9 = viewOne.findViewById(R.id.v9);
                View v11 = viewOne.findViewById(R.id.v11);

                v5.setVisibility(View.GONE);
                v6.setVisibility(View.GONE);
                v7.setVisibility(View.GONE);
                v8.setVisibility(View.GONE);
                v9.setVisibility(View.GONE);
                v11.setVisibility(View.GONE);
            }
            if (j == 5) {
                View v6 = viewOne.findViewById(R.id.v6);
                View v7 = viewOne.findViewById(R.id.v7);
                View v8 = viewOne.findViewById(R.id.v8);
                View v9 = viewOne.findViewById(R.id.v9);
                View v11 = viewOne.findViewById(R.id.v11);

                v6.setVisibility(View.GONE);
                v7.setVisibility(View.GONE);
                v8.setVisibility(View.GONE);
                v9.setVisibility(View.GONE);
                v11.setVisibility(View.GONE);
            }
            if (j == 6) {
                View v7 = viewOne.findViewById(R.id.v7);
                View v8 = viewOne.findViewById(R.id.v8);
                View v9 = viewOne.findViewById(R.id.v9);
                View v11 = viewOne.findViewById(R.id.v11);

                v7.setVisibility(View.GONE);
                v8.setVisibility(View.GONE);
                v9.setVisibility(View.GONE);
                v11.setVisibility(View.GONE);
            }
            if (j == 7) {
                View v8 = viewOne.findViewById(R.id.v8);
                View v9 = viewOne.findViewById(R.id.v9);
                View v11 = viewOne.findViewById(R.id.v11);

                v8.setVisibility(View.GONE);
                v9.setVisibility(View.GONE);
                v11.setVisibility(View.GONE);
            }
            if (j == 8) {
                View v9 = viewOne.findViewById(R.id.v9);
                View v11 = viewOne.findViewById(R.id.v11);

                v9.setVisibility(View.GONE);
                v11.setVisibility(View.GONE);
            }
            if (j == 9) {
                View v11 = viewOne.findViewById(R.id.v11);

                v11.setVisibility(View.GONE);
            }

        }

    }


    class ViewHolder {

        TextView tvSplitTitle;
        TextView tvHeaderDate;
        TextView tvHeaderMonth;
        TextView tvHeaderYear;

        TextView tvCabType;
        TextView tvSeat;
        TextView tvCabModel;
        ImageView imageCab;
        TextView tvFare;
        TextView tvRatePKm;
        TextView tvFareDetails;
        Button btnBookCab;

    }


    private void setViewDetails(int j, View viewOne, String name, String money) {

        String rupee = context.getResources().getString(R.string.rupee_sign);

        Log.e("adapterJoker123", "  j : " + j + "name : " + name + " money : " + money);

        if (j == 0) {
            View viewRem = viewOne.findViewById(R.id.v1);
            TextView tv1 = viewOne.findViewById(R.id.tv1);
            TextView tvA1 = viewOne.findViewById(R.id.tvA1);
            viewRem.setVisibility(View.VISIBLE);
            tv1.setText(name);
            tvA1.setText(rupee + " " + money);
        }
        if (j == 1) {
            View viewRem = viewOne.findViewById(R.id.v2);
            TextView tv2 = viewOne.findViewById(R.id.tv2);
            TextView tvA2 = viewOne.findViewById(R.id.tvA2);
            viewRem.setVisibility(View.VISIBLE);
            tv2.setText(name);
            tvA2.setText(rupee + " " + money);
        }
        if (j == 2) {
            View viewRem = viewOne.findViewById(R.id.v3);
            TextView tv3 = viewOne.findViewById(R.id.tv3);
            TextView tvA3 = viewOne.findViewById(R.id.tvA3);
            viewRem.setVisibility(View.VISIBLE);
            tv3.setText(name);
            tvA3.setText(rupee + " " + money);
        }
        if (j == 3) {
            View viewRem = viewOne.findViewById(R.id.v4);
            TextView tv4 = viewOne.findViewById(R.id.tv4);
            TextView tvA4 = viewOne.findViewById(R.id.tvA4);
            viewRem.setVisibility(View.VISIBLE);
            tv4.setText(name);
            tvA4.setText(rupee + " " + money);
        }
        if (j == 4) {
            View viewRem = viewOne.findViewById(R.id.v5);
            TextView tv5 = viewOne.findViewById(R.id.tv5);
            TextView tvA5 = viewOne.findViewById(R.id.tvA5);
            viewRem.setVisibility(View.VISIBLE);
            tv5.setText(name);
            tvA5.setText(rupee + " " + money);
        }
        if (j == 5) {
            View viewRem = viewOne.findViewById(R.id.v6);
            TextView tv6 = viewOne.findViewById(R.id.tv6);
            TextView tvA6 = viewOne.findViewById(R.id.tvA6);
            viewRem.setVisibility(View.VISIBLE);
            tv6.setText(name);
            tvA6.setText(rupee + " " + money);
        }
        if (j == 6) {
            View viewRem = viewOne.findViewById(R.id.v7);
            TextView tv7 = viewOne.findViewById(R.id.tv7);
            TextView tvA7 = viewOne.findViewById(R.id.tvA7);
            viewRem.setVisibility(View.VISIBLE);
            tv7.setText(name);
            tvA7.setText(rupee + " " + money);
        }
        if (j == 7) {
            View viewRem = viewOne.findViewById(R.id.v8);
            TextView tv8 = viewOne.findViewById(R.id.tv8);
            TextView tvA8 = viewOne.findViewById(R.id.tvA8);
            viewRem.setVisibility(View.VISIBLE);
            tv8.setText(name);
            tvA8.setText(rupee + " " + money);
        }
        if (j == 8) {
            View viewRem = viewOne.findViewById(R.id.v9);
            TextView tv9 = viewOne.findViewById(R.id.tv9);
            TextView tvA9 = viewOne.findViewById(R.id.tvA9);
            viewRem.setVisibility(View.VISIBLE);
            tv9.setText(name);
            tvA9.setText(rupee + " " + money);
        }
        if (j == 9) {
            View viewRem = viewOne.findViewById(R.id.v11);
            TextView tv11 = viewOne.findViewById(R.id.tv11);
            TextView tvA11 = viewOne.findViewById(R.id.tvA11);
            viewRem.setVisibility(View.VISIBLE);
            tv11.setText(name);
            tvA11.setText(rupee + " " + money);
        }

    }

}

