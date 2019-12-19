package com.webfarmatics.exptrack;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.webfarmatics.exptrack.adapters.SingleTransactionListAdapter;
import com.webfarmatics.exptrack.bean.BeanTransactionsHelper;
import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.GlobalData;

import java.util.ArrayList;


public class ActivityClearSingleTransaction extends AppCompatActivity {

    private Context context;
    private ListView lvSingelTrans;
    private LinearLayout llServerError;
    private AppDatabase database;
    private ArrayList<BeanTransactionsHelper> transactionHelperList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_single_trans);

        initialize();

        initToolbar();

        MobileAds.initialize(context, GlobalData.ADD_ID);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        transactionHelperList = database.getSingleTransactionsList();

        if (transactionHelperList != null) {

            SingleTransactionListAdapter adapter = new SingleTransactionListAdapter(context, transactionHelperList);
            lvSingelTrans.setAdapter(adapter);

        } else {
            lvSingelTrans.setVisibility(View.GONE);
            llServerError.setVisibility(View.VISIBLE);
        }


        lvSingelTrans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageView img = view.findViewById(R.id.img_state);

                BeanTransactionsHelper transaction = transactionHelperList.get(position);
                String state = transaction.getState();
                if (state.equalsIgnoreCase("unsel")) {
                    transaction.setState("selected");
                    img.setImageResource(R.drawable.selected_icon);
                } else {
                    transaction.setState("unsel");
                    img.setImageResource(R.drawable.unsel_icon);
                }


            }
        });


    }

    private void initialize() {
        context = ActivityClearSingleTransaction.this;
        lvSingelTrans = findViewById(R.id.lvSingleTrans);
        llServerError = findViewById(R.id.llServerError);
        database = AppDatabase.getInstance(this);


        final FloatingActionButton fab_clear = findViewById(R.id.fab_clear);


        fab_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedTransations();
            }
        });


    }

    public void initToolbar() {
        //this set back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //this is set custom image to back button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
    }

    //this method call when you press back button
    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return true;
    }


    private void clearSelectedTransations() {



        for (int i = 0; i < transactionHelperList.size(); i++) {
            BeanTransactionsHelper transaction = transactionHelperList.get(i);
            String state = transaction.getState();
            Log.e("clearSingle", " " + state + " " + transaction.getAmount());
        }

        boolean deleted = database.clearSelectedTransactions(transactionHelperList);
        if (deleted) {
            toastMsg("Selected Transaction's deleted.");
            finish();
        } else {
            toastMsg("failed to delete Transaction's..!");
        }
    }


    public void toastMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
