package com.webfarmatics.exptrack.adapters;


import android.content.Context;
import android.preference.PreferenceGroup;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.webfarmatics.exptrack.R;
import com.webfarmatics.exptrack.bean.BeanTransactions;

import java.util.ArrayList;
import java.util.List;


public class TransactionListAdapterTwo extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BeanTransactions> items = new ArrayList<>();
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, BeanTransactions obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public TransactionListAdapterTwo(Context context, List<BeanTransactions> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_list_trans_type;
        public TextView tv_list_trans_date;
        public TextView tv_list_trans_amt;
        public TextView tv_list_trans_desc;
        public ImageView imgTransType;
        public View lyt_parent;

        public OriginalViewHolder(View viewOne) {
            super(viewOne);
            tv_list_trans_type = (TextView) viewOne.findViewById(R.id._tv_list_trans_type);
            tv_list_trans_date = (TextView) viewOne.findViewById(R.id._tv_list_trans_date);
            tv_list_trans_amt = (TextView) viewOne.findViewById(R.id._tv_list_trans_amt);
            tv_list_trans_desc = (TextView) viewOne.findViewById(R.id._tv_list_trans_desc);
            imgTransType = (ImageView) viewOne.findViewById(R.id.imgTransType);
            lyt_parent = (View) viewOne.findViewById(R.id.lyt_parent);
        }
    }


    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressbar;

        public LoadingViewHolder(View viewOne) {
            super(viewOne);
            progressbar = (ProgressBar) viewOne.findViewById(R.id.progressbar);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.trans_list_single_row, parent, false);
            return new OriginalViewHolder(root);
        } else {
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.trans_list_loading_single_row, parent, false);
            return new LoadingViewHolder(root);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof OriginalViewHolder) {

            OriginalViewHolder view = (OriginalViewHolder) holder;
            BeanTransactions transaction = items.get(position);
            String r = ctx.getResources().getString(R.string.rupee_sign);
            view.tv_list_trans_type.setText(transaction.getItem());
            view.tv_list_trans_date.setText("Date : " + transaction.getDate());
            view.tv_list_trans_amt.setText(r + " : " + transaction.getAmount());

            String s = transaction.getDesc();
            if (s.length() > 15) {
                String comment = s.substring(0, Math.min(s.length(), 30));
                view.tv_list_trans_desc.setText("Comment : " + comment + "...");
            } else {
                view.tv_list_trans_desc.setText("Comment : " + transaction.getDesc());
            }

            String item = transaction.getPaymentBy();

            Log.e("TAG", " item " + item);

            if (item.equalsIgnoreCase("Card")) {
                view.imgTransType.setImageResource(R.drawable.card_icon);
            } else if (item.equalsIgnoreCase("Wallet")) {
                view.imgTransType.setImageResource(R.drawable.wallet_icon);
            } else if (item.equalsIgnoreCase("N/A")) {
                view.imgTransType.setImageResource(R.drawable.coins_icon);
            }

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder view = (LoadingViewHolder) holder;
            view.progressbar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 2;


    @Override
    public int getItemViewType(int position) {
        if (items.get(position) != null)
            return VIEW_TYPE_ITEM;
        else
            return VIEW_TYPE_LOADING;
    }


    public void addNullData() {
        items.add(null);
        notifyItemInserted(items.size() - 1);
    }


    public void removeNull() {
        items.remove(items.size() - 1);
        notifyItemRemoved(items.size());
    }


    public void addData(ArrayList<BeanTransactions> transList) {
        items.addAll(transList);
        notifyDataSetChanged();
    }

}