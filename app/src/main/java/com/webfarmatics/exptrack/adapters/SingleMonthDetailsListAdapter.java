package com.webfarmatics.exptrack.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.webfarmatics.exptrack.R;
import com.webfarmatics.exptrack.bean.BeanStatesByDate;
import com.webfarmatics.exptrack.bean.BeanStatesByDateFinalHelper;
import com.webfarmatics.exptrack.utils.CommonUtil;

import java.util.ArrayList;


public class SingleMonthDetailsListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<BeanStatesByDateFinalHelper> statesByDateList;

    public SingleMonthDetailsListAdapter(Context context, ArrayList<BeanStatesByDateFinalHelper> statesByDateList) {
        this.context = context;
        this.statesByDateList = statesByDateList;
    }

    @Override
    public int getCount() {
        return statesByDateList.size();
    }

    @Override
    public Object getItem(int position) {
        return statesByDateList.get(position);
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
            viewOne = inflater.inflate(R.layout.single_month_details_single_row, parent, false);
        }

        BeanStatesByDateFinalHelper helper = statesByDateList.get(position);

        View viewRem = viewOne.findViewById(R.id.v1);
        TextView tv1 = viewOne.findViewById(R.id.tv1);
        TextView tvA1 = viewOne.findViewById(R.id.tvA1);
        viewRem.setVisibility(View.VISIBLE);

        String date = helper.getDate();


        Log.e("joker", " *-*- *-* -* -* -* -* - *- *   " + date + "   *-*- *-* -* -* -* -* - *- *");

        ArrayList<BeanStatesByDate> statesByDatesList = helper.getStatesByDatesList();

        int count = 0;

        for (int i = 0; i < statesByDatesList.size(); i++) {

            BeanStatesByDate statesByDate = statesByDatesList.get(i);
            String item = statesByDate.getType();
            String money = statesByDate.getMoneySpend();
            String priority = statesByDate.getPriority();
            Log.e("joker", "   " + item + "   " + " " + priority);
            if (priority.equalsIgnoreCase("HIGH")) {
                setViewDetails(count, viewOne, item, money);
                Log.e("joker123", "j--- " + count + "   item--- " + item + "  priority--- " + priority);
                count++;
            }
        }

        TextView tvHeaderDate = (TextView) viewOne.findViewById(R.id.tvHeaderDate);
        TextView tvHeaderMonth = (TextView) viewOne.findViewById(R.id.tvHeaderMonth);
        TextView tvHeaderYear = (TextView) viewOne.findViewById(R.id.tvHeaderYear);

        ArrayList<String> dmy = CommonUtil.getDMY(date);
        String year = dmy.get(0);
        String month = dmy.get(1);
        String day = dmy.get(2);

        tvHeaderDate.setText(day);
        tvHeaderMonth.setText(month);
        tvHeaderYear.setText(year);

        return viewOne;
    }

    private void setViewDetails(int j, View viewOne, String item, String money) {

        Log.e("joker", "j--- " + j + "   item--- " + item + "  money--- " + money);

        String rupee = context.getResources().getString(R.string.rupee_sign);

        if (j == 0) {
            View viewRem = viewOne.findViewById(R.id.v1);
            TextView tv1 = viewOne.findViewById(R.id.tv1);
            TextView tvA1 = viewOne.findViewById(R.id.tvA1);
            viewRem.setVisibility(View.VISIBLE);
            tv1.setText(item);
            tvA1.setText(rupee + " " + money);

        }
        if (j == 1) {
            View viewRem = viewOne.findViewById(R.id.v2);
            TextView tv2 = viewOne.findViewById(R.id.tv2);
            TextView tvA2 = viewOne.findViewById(R.id.tvA2);
            viewRem.setVisibility(View.VISIBLE);
            tv2.setText(item);
            tvA2.setText(rupee + " " + money);
        }
        if (j == 2) {
            View viewRem = viewOne.findViewById(R.id.v3);
            TextView tv3 = viewOne.findViewById(R.id.tv3);
            TextView tvA3 = viewOne.findViewById(R.id.tvA3);
            viewRem.setVisibility(View.VISIBLE);
            tv3.setText(item);
            tvA3.setText(rupee + " " + money);
        }
        if (j == 3) {
            View viewRem = viewOne.findViewById(R.id.v4);
            TextView tv4 = viewOne.findViewById(R.id.tv4);
            TextView tvA4 = viewOne.findViewById(R.id.tvA4);
            viewRem.setVisibility(View.VISIBLE);
            tv4.setText(item);
            tvA4.setText(rupee + " " + money);
        }
        if (j == 4) {
            View viewRem = viewOne.findViewById(R.id.v5);
            TextView tv5 = viewOne.findViewById(R.id.tv5);
            TextView tvA5 = viewOne.findViewById(R.id.tvA5);
            viewRem.setVisibility(View.VISIBLE);
            tv5.setText(item);
            tvA5.setText(rupee + " " + money);
        }
        if (j == 5) {
            View viewRem = viewOne.findViewById(R.id.v6);
            TextView tv6 = viewOne.findViewById(R.id.tv6);
            TextView tvA6 = viewOne.findViewById(R.id.tvA6);
            viewRem.setVisibility(View.VISIBLE);
            tv6.setText(item);
            tvA6.setText(rupee + " " + money);
        }
    }

}
