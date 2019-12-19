package com.webfarmatics.exptrack.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.webfarmatics.exptrack.R;
import com.webfarmatics.exptrack.bean.BeanTransactions;

import java.util.ArrayList;


public class TransactionListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<BeanTransactions> transactionsList;

    public TransactionListAdapter(Context context, ArrayList<BeanTransactions> transactionsList) {
        this.context = context;
        this.transactionsList = transactionsList;
    }

    @Override
    public int getCount() {
        return transactionsList.size();
    }

    @Override
    public Object getItem(int position) {
        return transactionsList.get(position);
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
            viewOne = inflater.inflate(R.layout.trans_list_single_row, parent, false);
        }

        TextView tv_list_trans_type = (TextView) viewOne.findViewById(R.id._tv_list_trans_type);
        TextView tv_list_trans_date = (TextView) viewOne.findViewById(R.id._tv_list_trans_date);
        TextView tv_list_trans_amt = (TextView) viewOne.findViewById(R.id._tv_list_trans_amt);
        TextView tv_list_trans_desc = (TextView) viewOne.findViewById(R.id._tv_list_trans_desc);
        ImageView imgTransType = (ImageView) viewOne.findViewById(R.id.imgTransType);

        BeanTransactions transaction = transactionsList.get(position);
        String r = context.getResources().getString(R.string.rupee_sign);
        tv_list_trans_type.setText(transaction.getItem());
        tv_list_trans_date.setText("Date : " + transaction.getDate());
        tv_list_trans_amt.setText(r+" : " + transaction.getAmount());

        String s = transaction.getDesc();
        if (s.length() > 15) {
            String comment = s.substring(0, Math.min(s.length(), 30));
            tv_list_trans_desc.setText("Comment : " + comment + "...");
        } else {
            tv_list_trans_desc.setText("Comment : " + transaction.getDesc());
        }

        String item = transaction.getPaymentBy();

        Log.e("TAG", " item " + item);

        if (item.equalsIgnoreCase("Card")) {
            imgTransType.setImageResource(R.drawable.card_icon);
        } else if (item.equalsIgnoreCase("Wallet")) {
            imgTransType.setImageResource(R.drawable.wallet_icon);
        } else if (item.equalsIgnoreCase("N/A")) {
            imgTransType.setImageResource(R.drawable.coins_icon);
        }

        return viewOne;
    }
}
