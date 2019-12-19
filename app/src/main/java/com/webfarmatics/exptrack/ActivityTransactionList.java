package com.webfarmatics.exptrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.webfarmatics.exptrack.adapters.TransactionListAdapter;
import com.webfarmatics.exptrack.animation.ViewAnimation;
import com.webfarmatics.exptrack.bean.BeanTransactions;
import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.GlobalData;

import java.util.ArrayList;

public class ActivityTransactionList extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private EditText edtTrans;
    private ImageView imgSearch, imgCancelSearch;
    private ListView lvTransactions;
    private LinearLayout llServerError;

    private AppDatabase database;
    private ArrayList<BeanTransactions> transactionsList;

    private ArrayList<BeanTransactions> tempTransactionsList;
    private boolean loading = true;
    private TransactionListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_list);

        initialize();

        setListView();

        initToolbar();

        initializeFab();

        MobileAds.initialize(context, GlobalData.ADD_ID);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        lvTransactions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BeanTransactions transaction = transactionsList.get(position);
                Intent intent = new Intent(ActivityTransactionList.this, ActivityTransactionDetails.class);
                intent.putExtra("transaction", transaction);
                startActivity(intent);
            }
        });


        lvTransactions.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (loading) {
                    if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                        loading = false;
                        Log.v("...", "Last Item Wow !");
                        //Do pagination.. i.e. fetch new data
                        BeanTransactions t = new BeanTransactions("Book", "100", "Book purchased", "10/11/2019", "Cash");
                        transactionsList.add(t);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }

    private void searchTransaction(String trans) {

        for (int i = 0; i < transactionsList.size(); i++) {
            BeanTransactions transactions = transactionsList.get(i);
            String item = transactions.getItem();
            if (item.equalsIgnoreCase(trans)) {
                tempTransactionsList.add(transactions);
            }
        }

        if (tempTransactionsList != null) {

            TransactionListAdapter adapter = new TransactionListAdapter(context, tempTransactionsList);
            lvTransactions.setAdapter(adapter);
            imgSearch.setVisibility(View.GONE);
            imgCancelSearch.setVisibility(View.VISIBLE);


        } else {
            lvTransactions.setVisibility(View.GONE);
            llServerError.setVisibility(View.VISIBLE);
        }

    }

    private void setListView() {

        transactionsList = database.getTransactionsList();

        if (transactionsList != null) {

            adapter = new TransactionListAdapter(context, transactionsList);
            lvTransactions.setAdapter(adapter);

        } else {
            lvTransactions.setVisibility(View.GONE);
            llServerError.setVisibility(View.VISIBLE);
        }
    }

    private void initialize() {

        context = ActivityTransactionList.this;
        database = AppDatabase.getInstance(this);
        edtTrans = findViewById(R.id.edtTrans);
        imgSearch = findViewById(R.id.imgSearchTrans);
        imgCancelSearch = findViewById(R.id.imgCancelSearch);
        lvTransactions = findViewById(R.id.lvTransactions);
        llServerError = findViewById(R.id.llServerError);

        imgSearch.setOnClickListener(this);
        imgCancelSearch.setOnClickListener(this);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return true;
    }

    public void toastMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setListView();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgCancelSearch:
                imgCancelSearch.setVisibility(View.GONE);
                imgSearch.setVisibility(View.VISIBLE);
                setListView();
                break;

            case R.id.imgSearchTrans:
                if (edtTrans.getText().toString().isEmpty()) {
                    edtTrans.setError("Require");
                    return;
                }
                tempTransactionsList = new ArrayList<>();
                String trans = edtTrans.getText().toString();
                searchTransaction(trans);
                break;
        }
    }


    private boolean rotate = false;
    private View lytClearBatch;
    private View lytClearSingle;


    private void initializeFab() {

        View back_drop = findViewById(R.id.back_drop);
        final FloatingActionButton fabClearBatch = findViewById(R.id.fabClearBatch);
        final FloatingActionButton fabClearSingle = findViewById(R.id.fabClearSingle);
        final FloatingActionButton fab_add = findViewById(R.id.fab_add);
        lytClearBatch = findViewById(R.id.lytClearBatch);
        lytClearSingle = findViewById(R.id.lytClearSingle);
        ViewAnimation.initShowOut(lytClearBatch);
        ViewAnimation.initShowOut(lytClearSingle);

        back_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFabMode(fab_add);
            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFabMode(v);
            }
        });

        fabClearBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentB = new Intent(ActivityTransactionList.this, ActivityClearBatchTransaction.class);
                startActivity(intentB);
            }
        });

        fabClearSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentS = new Intent(ActivityTransactionList.this, ActivityClearSingleTransaction.class);
                startActivity(intentS);
            }
        });

    }


    private void toggleFabMode(View v) {
        rotate = ViewAnimation.rotateFab(v, !rotate);
        if (rotate) {
            ViewAnimation.showIn(lytClearBatch);
            ViewAnimation.showIn(lytClearSingle);
        } else {
            ViewAnimation.showOut(lytClearBatch);
            ViewAnimation.showOut(lytClearSingle);
        }
    }


}
