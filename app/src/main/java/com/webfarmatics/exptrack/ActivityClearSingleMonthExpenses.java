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
import com.webfarmatics.exptrack.adapters.SingleMonthDetailsListAdapter;
import com.webfarmatics.exptrack.bean.BeanStatesByDate;
import com.webfarmatics.exptrack.bean.BeanStatesByDateFinalHelper;
import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.GlobalData;

import java.util.ArrayList;

public class ActivityClearSingleMonthExpenses extends AppCompatActivity {

    private Context context;
    private AppDatabase database;
    private ListView lvAllMonth;
    private LinearLayout llServerError;
    private FloatingActionButton fabDelete;

    private ArrayList<BeanStatesByDateFinalHelper> listHelper;

    private ArrayList<BeanStatesByDate> statesByDateArrayList;

    private String previousDate = null;
    private int count = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_single_month_expenses);

        initialize();

        initToolbar();

        MobileAds.initialize(context, GlobalData.ADD_ID);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        statesByDateArrayList = database.getStatsByDate();

        if (statesByDateArrayList == null) {
            lvAllMonth.setVisibility(View.GONE);
            llServerError.setVisibility(View.VISIBLE);
            return;
        }


//        printListBeanStatesByDate(statesByDateArrayList);


        Log.e("joker11", "***********************************************************************");

        Log.e("joker11", " ++++       ++++  ActivityClearSingleMonthExpenses  + + + ");

//        printListBeanStatesByDate(listChild);


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

        SingleMonthDetailsListAdapter adapter = new SingleMonthDetailsListAdapter(context, listHelper);
        lvAllMonth.setAdapter(adapter);


        lvAllMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView img = view.findViewById(R.id.img_state);

                BeanStatesByDateFinalHelper transaction = listHelper.get(position);
                String state = transaction.getState();
                if (state.equalsIgnoreCase("unsel")) {
                    transaction.setState("selected");
                    img.setImageResource(R.drawable.selected_icon);
                } else {
                    transaction.setState("unsel");
                    img.setImageResource(R.drawable.blue_unsel_icon);
                }
            }
        });

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelectedTransations();
            }
        });

    }


    private void addBeanByDate(String date1) {

        count++;
        Log.e("joker", " *-*- *-* -* -* -* -* - *- *- - - - -  " + date1);

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

        BeanStatesByDateFinalHelper statesByDateHelper = new BeanStatesByDateFinalHelper(date1, listBean, "unsel");
        listHelper.add(statesByDateHelper);

        ArrayList<BeanStatesByDate> z = listHelper.get(count).getStatesByDatesList();
        printListBeanStatesByDate(z);
    }


    private void initialize() {

        context = ActivityClearSingleMonthExpenses.this;
        database = AppDatabase.getInstance(this);
        llServerError = findViewById(R.id.llServerError);
        lvAllMonth = findViewById(R.id.lvAllMonth);
        fabDelete = findViewById(R.id.fabDelete);

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

        Log.e("321321", " size " + list123.size());


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


    private void clearSelectedTransations() {
        Log.e("joker", "Month clearSelectedTransations");
        boolean deleted = database.clearMonthDetailTransactions(listHelper);
        if (deleted) {
            toastMsg("Selected Month details deleted.");
            finish();
        } else {
            toastMsg("failed to delete Month details..!");
        }
    }

    public void toastMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
