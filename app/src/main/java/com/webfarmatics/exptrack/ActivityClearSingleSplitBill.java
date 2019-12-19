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
import com.webfarmatics.exptrack.adapters.SingleSplitDetailsListAdapter;
import com.webfarmatics.exptrack.bean.BeanSplitFinalHelper;
import com.webfarmatics.exptrack.bean.BeanSplitHelper;
import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.GlobalData;

import java.util.ArrayList;

public class ActivityClearSingleSplitBill extends AppCompatActivity {

    private Context context;
    private AppDatabase database;
    private ListView lvSplitBill;
    private LinearLayout llServerError;
    private FloatingActionButton fabDelete;

    private ArrayList<BeanSplitFinalHelper> listHelper;

    private ArrayList<BeanSplitHelper> listChild;

    private String previousDate = null;

    private String previousTitle = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_single_split_bill);

        initialize();

        initToolbar();

        MobileAds.initialize(context, GlobalData.ADD_ID);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        ArrayList<BeanSplitHelper> statesByDateArrayList = database.getSplitBillList();

        listChild = statesByDateArrayList;

        if (listHelper == null) {
            lvSplitBill.setVisibility(View.GONE);
            llServerError.setVisibility(View.VISIBLE);
            return;
        }

        printListBeanStatesByDate(statesByDateArrayList);

        if (statesByDateArrayList == null) {
            return;
        }

        if (statesByDateArrayList.size() <= 0) {
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

            String currentDate = date;
            String currentTitle = title;
            if (!currentDate.equalsIgnoreCase(previousDate) || !currentTitle.equalsIgnoreCase(previousTitle)) {
                Log.e("joker", " ++++     N o t   E q u a l s    ++++  " + currentDate + " + + + " + previousDate);
                addBeanByDate(title, date);
                previousDate = currentDate;
                previousTitle = currentTitle;
            } else {
                Log.e("joker", " ++++     E q u a l s    ++++  " + currentDate + " + + + " + previousDate);
            }
        }

        SingleSplitDetailsListAdapter adapter = new SingleSplitDetailsListAdapter(context, listHelper);
        lvSplitBill.setAdapter(adapter);

        Log.e("sizeList", "Helper : " + listHelper.size());

        lvSplitBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageView img = view.findViewById(R.id.img_state);

                BeanSplitFinalHelper transaction = listHelper.get(position);
                String state = transaction.getState();
                String date = transaction.getDate();
                Log.e("jokerer", " *-*- *-* -* -* -* -* - *- *- - - - -  " + state+"  D  "+date);
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
                clearSelectedSplitBills();
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

        BeanSplitFinalHelper beanSplitHelperHelper = new BeanSplitFinalHelper(title, date, listBean, "unsel");
        listHelper.add(beanSplitHelperHelper);

    }


    private void initialize() {

        context = ActivityClearSingleSplitBill.this;
        database = AppDatabase.getInstance(this);
        llServerError = findViewById(R.id.llServerError);
        lvSplitBill = findViewById(R.id.lvSplitBill);

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

    private void clearSelectedSplitBills() {

        boolean deleted = database.clearSelectedSplitBills(listHelper);
        if (deleted) {
            toastMsg("Selected Split Bill deleted.");
            finish();
        } else {
            toastMsg("failed to delete Split Bill..!");
        }

    }

    public void toastMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
