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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.webfarmatics.exptrack.adapters.SplitDetailsListAdapter;
import com.webfarmatics.exptrack.animation.ViewAnimation;
import com.webfarmatics.exptrack.bean.BeanSplitHelper;
import com.webfarmatics.exptrack.bean.BeanSplitHelperHelper;
import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.GlobalData;

import java.util.ArrayList;

public class ActivitySplitBillList extends AppCompatActivity {

    private Context context;
    private AppDatabase database;
    private ListView lvSplitBill;
    private LinearLayout llServerError;

    private ArrayList<BeanSplitHelperHelper> listHelper;
    private ArrayList<BeanSplitHelper> listChild;

    private String previousDate = null, previousTitle = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_bill_list);

        initialize();

        initToolbar();

        setListView();

        initializeFab();

        MobileAds.initialize(context, GlobalData.ADD_ID);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        lvSplitBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BeanSplitHelperHelper helper = listHelper.get(position);
                Intent intent = new Intent(ActivitySplitBillList.this, ActivitySettleSplitBill.class);
                intent.putExtra("helper", helper);
                startActivity(intent);
                finish();
            }
        });


    }

    private void addBeanByDate(String title, String date) {

        ArrayList<BeanSplitHelper> listBean = new ArrayList<>();

        for (int j = 0; j < listChild.size(); j++) {

            BeanSplitHelper oneItem = listChild.get(j);
            String title1 = oneItem.getTitle();
            String amount = oneItem.getAmount();
            String date1 = oneItem.getDate();
            String returnDate = oneItem.getReturnDate();
            String comment = oneItem.getComment();
            String owesYou = oneItem.getOwesYou();
            String participant = oneItem.getParticipant();

            if (date1.equalsIgnoreCase(date) && title1.equalsIgnoreCase(title)) {

                BeanSplitHelper beanSplit = new BeanSplitHelper(title1, amount, date1, returnDate, comment, owesYou, participant);
                listBean.add(beanSplit);

            }

        }

        BeanSplitHelperHelper beanSplitHelperHelper = new BeanSplitHelperHelper(title, date, listBean);
        listHelper.add(beanSplitHelperHelper);

    }


    private void initialize() {

        context = ActivitySplitBillList.this;
        database = AppDatabase.getInstance(this);
        llServerError = findViewById(R.id.llServerError);
        lvSplitBill = findViewById(R.id.lvSplitBill);

        listHelper = new ArrayList<>();

    }

    private void setListView() {

        ArrayList<BeanSplitHelper> statesByDateArrayList = database.getSplitBillList();

        listChild = statesByDateArrayList;

        if (listHelper == null) {
            Log.e("sizeList", "listHelper == null");
            lvSplitBill.setVisibility(View.GONE);
            llServerError.setVisibility(View.VISIBLE);
            return;
        }

        printListBeanStatesByDate(statesByDateArrayList);

        if (statesByDateArrayList == null) {
            lvSplitBill.setVisibility(View.GONE);
            llServerError.setVisibility(View.VISIBLE);
            Log.e("sizeList", "statesByDateArrayList == null");
            return;
        }

        if (statesByDateArrayList.size() <= 0) {
            lvSplitBill.setVisibility(View.GONE);
            llServerError.setVisibility(View.VISIBLE);
            Log.e("sizeList", "statesByDateArrayList size() == null");
            return;
        }

        Log.e("sizeList", "" + statesByDateArrayList.size());

        for (int j = 0; j < statesByDateArrayList.size(); j++) {

            BeanSplitHelper oneItem = statesByDateArrayList.get(j);

            String title = oneItem.getTitle();
            String amount = oneItem.getAmount();
            String date = oneItem.getDate();
            String returnDate = oneItem.getReturnDate();
            String comment = oneItem.getComment();
            String owesYou = oneItem.getOwesYou();
            String participant = oneItem.getParticipant();

            if (!date.equalsIgnoreCase(previousDate) || !title.equalsIgnoreCase(previousTitle)) {
                Log.e("joker", " ++++     N o t   E q u a l s    ++++  " + date + " + + + " + previousDate);
                addBeanByDate(title, date);
                previousDate = date;
                previousTitle = title;
            } else {
                Log.e("joker", " ++++     E q u a l s    ++++  " + date + " + + + " + previousDate);
            }
        }

        SplitDetailsListAdapter adapter = new SplitDetailsListAdapter(context, listHelper);
        lvSplitBill.setAdapter(adapter);

        Log.e("sizeList", "Helper : " + listHelper.size());


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

    private void printListBeanStatesByDate(ArrayList<BeanSplitHelper> list123) {

        if (list123 == null) {
            return;
        }

        if (list123.size() <= 0) {
            return;
        }

        for (int j = 0; j < list123.size(); j++) {

            BeanSplitHelper oneItem = list123.get(j);
            String title = oneItem.getTitle();
            String amount = oneItem.getAmount();
            String participant = oneItem.getParticipant();
            String owes = oneItem.getOwesYou();
            String date1 = oneItem.getDate();
            String retDate = oneItem.getReturnDate();
            int value = Integer.parseInt(amount);

            Log.e("splitSingle ", " title " + title + " amount " + amount + " date " + date1 + " participant " + participant + " owes " + owes);

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();
        return true;
    }


    private boolean rotate = false;
    private View lytClearSplitBill;
    private View lytAddSplitBill;


    private void initializeFab() {

        final FloatingActionButton fabClearSplitBill = findViewById(R.id.fabClearSplitBill);
        final FloatingActionButton fabAddBill = findViewById(R.id.fabAddBill);
        final FloatingActionButton fab_add = findViewById(R.id.fab_add);
        lytClearSplitBill = findViewById(R.id.lytClearSplitBill);
        lytAddSplitBill = findViewById(R.id.lytAddSplitBill);
        ViewAnimation.initShowOut(lytClearSplitBill);
        ViewAnimation.initShowOut(lytAddSplitBill);


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFabMode(v);
            }
        });

        fabClearSplitBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySplitBillList.this, ActivityClearSingleSplitBill.class);
                startActivity(intent);
                finish();
            }
        });

        fabAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySplitBillList.this, ActivitySaveSplitBill.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void toggleFabMode(View v) {
        rotate = ViewAnimation.rotateFab(v, !rotate);
        if (rotate) {
            ViewAnimation.showIn(lytClearSplitBill);
            ViewAnimation.showIn(lytAddSplitBill);
        } else {
            ViewAnimation.showOut(lytClearSplitBill);
            ViewAnimation.showOut(lytAddSplitBill);
        }
    }


}
