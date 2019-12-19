package com.webfarmatics.exptrack.adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.webfarmatics.exptrack.R;
import com.webfarmatics.exptrack.bean.BeanLoan;

import java.util.ArrayList;

public class LoanListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<BeanLoan> loanArrayList;
    private String todaysDate;

    public LoanListAdapter(Context context, ArrayList<BeanLoan> loanArrayList, String todaysDate) {
        this.context = context;
        this.loanArrayList = loanArrayList;
        this.todaysDate = todaysDate;
    }

    @Override
    public int getCount() {
        return loanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return loanArrayList.get(position);
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
            viewOne = inflater.inflate(R.layout.loan_list_single_row, parent, false);
        }

        TextView tv_list_trans_type = (TextView) viewOne.findViewById(R.id._tv_list_trans_type);
        TextView tv_list_trans_date = (TextView) viewOne.findViewById(R.id._tv_list_trans_date);
        TextView tv_list_trans_amt = (TextView) viewOne.findViewById(R.id._tv_list_trans_amt);
        TextView tv_list_trans_desc = (TextView) viewOne.findViewById(R.id._tv_list_trans_desc);

        ImageView imgTransType = (ImageView) viewOne.findViewById(R.id.imgTransType);

        BeanLoan loan = loanArrayList.get(position);
        String r = context.getResources().getString(R.string.rupee_sign);
        tv_list_trans_type.setText("Name : " + loan.getFrom());
        tv_list_trans_date.setText("R.Date : " + loan.getReturnDate());
        tv_list_trans_amt.setText(r + " : " + loan.getAmount());

        String s = loan.getDesc();
        if (s.length() > 15) {
            String comment = s.substring(0, Math.min(s.length(), 30));
            tv_list_trans_desc.setText("Comment : " + comment + "...");
        } else {
            tv_list_trans_desc.setText("Comment : " + loan.getDesc());
        }

        String returnDate = loan.getReturnDate();

        if (returnDate.equalsIgnoreCase(todaysDate)) {
            tv_list_trans_type.setTextColor(Color.BLUE);
            tv_list_trans_amt.setTextColor(Color.BLUE);
            imgTransType.setImageResource(R.drawable.loan_inmoney);
        }else{
            imgTransType.setImageResource(R.drawable.coins_icon);
        }

        return viewOne;
    }
}
