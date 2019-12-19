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
import com.webfarmatics.exptrack.bean.BeanLoanHelper;

import java.util.ArrayList;


public class SingleLoanListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<BeanLoanHelper> loanArrayList;

    public SingleLoanListAdapter(Context context, ArrayList<BeanLoanHelper> loanArrayList) {
        this.context = context;
        this.loanArrayList = loanArrayList;
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
            viewOne = inflater.inflate(R.layout.single_loan_list_single_row, parent, false);
        }

        TextView tv_list_trans_type = (TextView) viewOne.findViewById(R.id._tv_list_trans_type);
        TextView tv_list_trans_date = (TextView) viewOne.findViewById(R.id._tv_list_trans_date);
        TextView tv_list_trans_amt = (TextView) viewOne.findViewById(R.id._tv_list_trans_amt);
        TextView tv_list_trans_desc = (TextView) viewOne.findViewById(R.id._tv_list_trans_desc);
        ImageView img_state = (ImageView) viewOne.findViewById(R.id.img_state);

        BeanLoanHelper loan = loanArrayList.get(position);
        String r = context.getResources().getString(R.string.rupee_sign);
        tv_list_trans_type.setText("Name : "+loan.getFrom());
        tv_list_trans_date.setText(""+loan.getDate());
        tv_list_trans_amt.setText(r+" : "+loan.getAmount());

        String s = loan.getDesc();
        if(s.length()>15){
            String comment = s.substring(0, Math.min(s.length(), 30));
            tv_list_trans_desc.setText("Comment : "+comment+"...");
        }else{
            tv_list_trans_desc.setText("Comment : "+loan.getDesc());
        }

        String state = loan.getState();
        if(state.equalsIgnoreCase("unselected")){
            img_state.setImageResource(R.drawable.unsel_icon);
        }else {
            img_state.setImageResource(R.drawable.selected_icon);
        }

        if (position % 2 == 0) {
            Log.e("color", "position % 2  " + position);
//            viewOne.setBackgroundColor(context.getResources().getColor(R.color.colorEven));
//            viewOne.setBackgroundColor(Color.MAGENTA);
//            viewOne.setBackgroundColor(Color.parseColor("#BEBEBE"));
        } else {
            Log.e("color", "position " + position);
//            viewOne.setBackgroundColor(context.getResources().getColor(R.color.colorOdd));
//            viewOne.setBackgroundColor(Color.YELLOW);
//            viewOne.setBackgroundColor(Color.parseColor("#D0D0D0"));
        }

        return viewOne;
    }
}
