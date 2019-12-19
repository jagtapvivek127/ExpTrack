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
import com.webfarmatics.exptrack.adapters.MonthDetailsListAdapter;
import com.webfarmatics.exptrack.animation.ViewAnimation;
import com.webfarmatics.exptrack.bean.BeanStatesByDate;
import com.webfarmatics.exptrack.bean.BeanStatesByDateHelper;
import com.webfarmatics.exptrack.bean.BeanTransactions;
import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.GlobalData;

import java.util.ArrayList;

public class ActivityMonthExpenses extends AppCompatActivity {

    private Context context;
    private AppDatabase database;
    private ListView lvAllMonth;
    private LinearLayout llServerError;

    private ArrayList<BeanStatesByDateHelper> listHelper;

    private ArrayList<BeanStatesByDate> statesByDateArrayList;

    private String previousDate = null;
    private int count = -1;

    private static final String TAG = "ActivityMonthExpenses";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_expences);

        initialize();

        initToolbar();

        Log.e("joker11", " ++++       ++++  ActivityMonthExpenses  + + + ");

        MobileAds.initialize(context, GlobalData.ADD_ID);

        statesByDateArrayList = database.getStatsByDate();

        if (statesByDateArrayList == null) {
            lvAllMonth.setVisibility(View.GONE);
            llServerError.setVisibility(View.VISIBLE);
            return;
        }

        for (int j = 0; j < statesByDateArrayList.size(); j++) {

            BeanStatesByDate oneItem = statesByDateArrayList.get(j);
            String id = oneItem.getId();
            String type = oneItem.getType();
            String moneySpend = oneItem.getMoneySpend();
            String priority = oneItem.getPriority();
            String date1 = oneItem.getDate();
            String currentDate = date1;
            if (!currentDate.equalsIgnoreCase(previousDate)) {
                Log.e("joker", " ++++     N o t   E q u a l s    ++++  " + currentDate + " + + + " + previousDate);
                addBeanByDate(date1);
                previousDate = currentDate;
            } else {
                Log.e("joker", " ++++     E q u a l s    ++++  " + currentDate + " + + + " + previousDate);
            }

        }

        MonthDetailsListAdapter adapter = new MonthDetailsListAdapter(context, listHelper);
        lvAllMonth.setAdapter(adapter);

        lvAllMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemSelected: " + listHelper.get(position));

                BeanStatesByDateHelper states = listHelper.get(position);

                Intent intent = new Intent(context, ActivityMonthExpensesDetails.class);
                intent.putExtra("states", states);
                startActivity(intent);
            }
        });

    }


    private void addBeanByDate(String date1) {

        count++;

        ArrayList<BeanStatesByDate> listBean = new ArrayList<>();

        for (int j = 0; j < statesByDateArrayList.size(); j++) {

            BeanStatesByDate oneItem = statesByDateArrayList.get(j);
            String id = oneItem.getId();
            String type = oneItem.getType();
            String moneySpend = oneItem.getMoneySpend();
            String priority = oneItem.getPriority();
            String date = oneItem.getDate();
            int value = Integer.parseInt(moneySpend);

            if (date.equalsIgnoreCase(date1)) {

                BeanStatesByDate statesByDate = new BeanStatesByDate(id, type, moneySpend, priority, date);
                listBean.add(statesByDate);

            }

        }

        BeanStatesByDateHelper statesByDateHelper = new BeanStatesByDateHelper(date1, listBean);
        listHelper.add(statesByDateHelper);

        ArrayList<BeanStatesByDate> z = listHelper.get(count).getStatesByDatesList();
        printListBeanStatesByDate(z);
    }


    private void initialize() {

        context = ActivityMonthExpenses.this;
        database = AppDatabase.getInstance(this);
        llServerError = findViewById(R.id.llServerError);
        lvAllMonth = findViewById(R.id.lvAllMonth);

        listHelper = new ArrayList<>();

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


    private void printListBeanStatesByDate(ArrayList<BeanStatesByDate> list123) {

        for (int j = 0; j < list123.size(); j++) {

            BeanStatesByDate oneItem = list123.get(j);
            String id = oneItem.getId();
            String type = oneItem.getType();
            String moneySpend = oneItem.getMoneySpend();
            String priority = oneItem.getPriority();
            String date1 = oneItem.getDate();
            int value = Integer.parseInt(moneySpend);
            Log.e("321321", " type " + type + " moneySpend " + moneySpend + " priority " + priority + " date " + date1);

            Log.e("joker", date1 + " " + type + " " + priority + " " + moneySpend);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();
        return true;
    }

}
