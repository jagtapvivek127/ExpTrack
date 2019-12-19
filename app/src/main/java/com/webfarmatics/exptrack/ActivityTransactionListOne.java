package com.webfarmatics.exptrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.webfarmatics.exptrack.adapters.TransactionListAdapter;
import com.webfarmatics.exptrack.adapters.TransactionListAdapterOne;
import com.webfarmatics.exptrack.animation.ViewAnimation;
import com.webfarmatics.exptrack.bean.BeanTransactions;
import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.GlobalData;

import java.util.ArrayList;

public class ActivityTransactionListOne extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ActivityTransactionList";

    private Context context;
    private EditText edtTrans;
    private ImageView imgSearch, imgCancelSearch;
    private RecyclerView lvTransactions;
    private LinearLayout llServerError;
    private ProgressBar progressCircular;

    private AppDatabase database;
    private ArrayList<BeanTransactions> transactionsList;

    private ArrayList<BeanTransactions> tempTransactionsList;
    private boolean loading = true;


    private int previousTotal = 0;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private TransactionListAdapterOne adapter;
    private LinearLayoutManager mLayoutManager;
    int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_list_one);

        initialize();

        setListView();

        initToolbar();

        initializeFab();

        MobileAds.initialize(context, GlobalData.ADD_ID);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);


        lvTransactions.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount)
                            <= (firstVisibleItem + visibleThreshold)) {
                        // End has been reached

                        Log.i("Yaeye!", "end called");

                        // Do something

                        count++;
                        loadMoreItems(count);

                        loading = true;
                    }
                }
            }
        });



    }

    private void loadMoreItems(int count) {

        Log.e(TAG, "loadMoreItems: **************** " + count + " ********************* ");

        progressCircular.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    BeanTransactions t = new BeanTransactions("book", "100", "Book Purchased", "10/11/2019", "Cash");
                    transactionsList.add(t);
                }
                adapter.notifyDataSetChanged();
                progressCircular.setVisibility(View.GONE);
            }
        }, 3500);


        Log.e(TAG, "loadMoreItems: **************** " + transactionsList.size() + " ********************* ");

        Log.e(TAG, "\nloadMoreItems: --------------------------------------------------------------------");

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

            TransactionListAdapterOne adapter = new TransactionListAdapterOne(context, tempTransactionsList);
            lvTransactions.setAdapter(adapter);
            imgSearch.setVisibility(View.GONE);
            imgCancelSearch.setVisibility(View.VISIBLE);

            adapter.setOnItemClickListener(new TransactionListAdapterOne.OnItemClickListener() {
                @Override
                public void onItemClick(View view, BeanTransactions obj, int position) {
                    Intent intent = new Intent(ActivityTransactionListOne.this, ActivityTransactionDetails.class);
                    intent.putExtra("transaction", obj);
                    startActivity(intent);
                }
            });


        } else {
            lvTransactions.setVisibility(View.GONE);
            llServerError.setVisibility(View.VISIBLE);
        }

    }

    private void setListView() {

        transactionsList = database.getTransactionsList();

        if (transactionsList != null) {

            adapter = new TransactionListAdapterOne(context, transactionsList);
            lvTransactions.setAdapter(adapter);

            Log.e(TAG, "setListView: " + transactionsList.size());
            Log.e(TAG, "setListView: " + visibleItemCount);
            Log.e(TAG, "setListView: " + firstVisibleItem);
            Log.e(TAG, "setListView: " + totalItemCount);

        } else {
            lvTransactions.setVisibility(View.GONE);
            llServerError.setVisibility(View.VISIBLE);
        }
    }

    private void initialize() {

        context = ActivityTransactionListOne.this;
        database = AppDatabase.getInstance(this);
        edtTrans = findViewById(R.id.edtTrans);
        imgSearch = findViewById(R.id.imgSearchTrans);
        imgCancelSearch = findViewById(R.id.imgCancelSearch);
        lvTransactions = findViewById(R.id.lvTransactions);
        mLayoutManager = new LinearLayoutManager(this);
        lvTransactions.setLayoutManager(mLayoutManager);
        lvTransactions.setHasFixedSize(true);
        llServerError = findViewById(R.id.llServerError);
        progressCircular = findViewById(R.id.progress_circular);

        imgSearch.setOnClickListener(this);
        imgCancelSearch.setOnClickListener(this);

        progressCircular.setVisibility(View.GONE);


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
                Intent intentB = new Intent(ActivityTransactionListOne.this, ActivityClearBatchTransaction.class);
                startActivity(intentB);
            }
        });

        fabClearSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentS = new Intent(ActivityTransactionListOne.this, ActivityClearSingleTransaction.class);
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
