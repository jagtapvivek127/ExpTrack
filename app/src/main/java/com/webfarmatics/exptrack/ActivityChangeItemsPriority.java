package com.webfarmatics.exptrack;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.internal.overlay.zzo;
import com.webfarmatics.exptrack.adapters.ItemListAdapter;
import com.webfarmatics.exptrack.bean.BeanItemStates;
import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.GlobalData;

import java.util.ArrayList;

public class ActivityChangeItemsPriority extends AppCompatActivity {

    private Context context;
    private AppDatabase database;
    private ListView lvItemPriority;
    private FloatingActionButton fabItemSaveP;
    private ArrayList<BeanItemStates> itemStatesList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_items_priority);

        initialize();

        initToolbar();

        MobileAds.initialize(context, GlobalData.ADD_ID);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        itemStatesList = database.getItemStatesList();

        if (itemStatesList != null) {
            ItemListAdapter adapter = new ItemListAdapter(context, itemStatesList);
            lvItemPriority.setAdapter(adapter);
        }


        lvItemPriority.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final int pos = position;

                final ImageView imgHighPriority = view.findViewById(R.id.imgHighPriority);

                final ImageView imgLowPriority = view.findViewById(R.id.imgLowPriority);

                BeanItemStates item = itemStatesList.get(position);

//                String priority = item.getPriority();
//
//                if (priority.equalsIgnoreCase("HIGH")) {
//                    imgHighPriority.setVisibility(View.VISIBLE);
//                    imgLowPriority.setVisibility(View.INVISIBLE);
//                } else if (priority.equalsIgnoreCase("LOW")) {
//                    imgHighPriority.setVisibility(View.INVISIBLE);
//                    imgLowPriority.setVisibility(View.VISIBLE);
//                }


                imgHighPriority.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgHighPriority.setImageResource(R.drawable.high_icon);
                        imgLowPriority.setImageResource(R.drawable.null_icon);
                        BeanItemStates itemStates = itemStatesList.get(pos);
                        itemStates.setPriority("HIGH");
                    }
                });


                imgLowPriority.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgHighPriority.setImageResource(R.drawable.null_icon);
                        imgLowPriority.setImageResource(R.drawable.low_icon);
                        BeanItemStates itemStates = itemStatesList.get(pos);
                        itemStates.setPriority("LOW");
                    }
                });
            }
        });


        printItemsList("before");


        fabItemSaveP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChangedPriorities();
            }
        });


    }

    private void initialize() {

        context = ActivityChangeItemsPriority.this;
        database = AppDatabase.getInstance(this);
        lvItemPriority = findViewById(R.id.lvItemPriority);
        fabItemSaveP = findViewById(R.id.fabItemSaveP);

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

    private void saveChangedPriorities() {

        int highCount = 0;

        for (int i = 0; i < itemStatesList.size(); i++) {

            BeanItemStates selItem = itemStatesList.get(i);

            Log.e("itemPri", "Item " + selItem.getType() + " Priority " + selItem.getPriority());

            String priority = selItem.getPriority();
            if (priority.equalsIgnoreCase("HIGH")) {
                highCount++;
            }
        }

        if (highCount > 6) {
            toastMsg("Maximum Six items can have high priority..!");
            return;
        }

        if (highCount < 4) {
            toastMsg("Minimum Four items must have high priority..!");
            return;
        }

        boolean saved = database.saveChngedItemPriorities(itemStatesList);

        if (saved) {
            toastMsg("Items priorities changed..");
            printItemsList("after");
            finish();
        } else {
            toastMsg("failed to change priorities..!");
        }
    }

    public void toastMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    private void printItemsList(String tag) {

        ArrayList<BeanItemStates> itemStates = database.getItemStatesList();

        for (int i = 0; i < itemStates.size(); i++) {

            BeanItemStates oneItem = itemStatesList.get(i);
            String id = oneItem.getId();
            String type = oneItem.getType();
            String noTrans = oneItem.getNoOfTrans();
            String priority = oneItem.getPriority();
            String moneySpend = oneItem.getMoneySpend();
            int value = Integer.parseInt(moneySpend);

            Log.e(tag + i, "Item " + type + " noTrans " + noTrans + " Priority " + priority + " moneySpend " + moneySpend);

        }
    }

}
