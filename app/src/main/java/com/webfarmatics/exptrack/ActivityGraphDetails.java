package com.webfarmatics.exptrack;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.webfarmatics.exptrack.adapters.GraphListAdapter;
import com.webfarmatics.exptrack.animation.ViewAnimation;
import com.webfarmatics.exptrack.bean.BeanItemStates;
import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.CommonUtil;
import com.webfarmatics.exptrack.utils.GlobalData;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityGraphDetails extends AppCompatActivity {


    private Context context;
    private AppDatabase database;
    private PieChart mPieChart;
    private ListView lvGraphList;
    private LinearLayout llResponse, llServerError;
    private String date = null;

    private String[] priorityColors = {"#d7f24a39", "#9B59B6", "#2980B9", "#077b64", "#FE6DA8", "#0e68a3", "#4af190", "#8914ba"};
    private String[] colors = {"#EC7063", "#FE6DA8", "#566573", "#5D6D7E", "#AAB7B8", "#DC7633", "#EB984E", "#F5B041", "#45B39D",
            "#48C9B0", "#5DADE2"};

    private ArrayList<BeanItemStates> itemStatesList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_details);

        initialize();

        initializeFab();

        changeViewToPie();

        initToolbar();

        MobileAds.initialize(context, GlobalData.ADD_ID);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

    }

    private void changeViewToPie() {

        int highColorCount = 0;
        int ordinaryColorCount = 0;

        itemStatesList = database.getItemStatesList();

        int colorLen = colors.length;

        if (itemStatesList == null) {
            llResponse.setVisibility(View.GONE);
            llServerError.setVisibility(View.VISIBLE);
            return;
        }


        for (int i = 0; i < itemStatesList.size(); i++) {

            BeanItemStates itemStates = itemStatesList.get(i);
            String id = itemStates.getId();
            String type = itemStates.getType();
            String noTrans = itemStates.getNoOfTrans();
            String priority = itemStates.getPriority();
            String moneySpend = itemStates.getMoneySpend();
            int value = Integer.parseInt(moneySpend);

            if (priority.equalsIgnoreCase("HIGH")) {
                Log.e("highColor", "count " + highColorCount);
                String highColor = priorityColors[highColorCount];
                mPieChart.addPieSlice(new PieModel(type, value, Color.parseColor(highColor)));
                highColorCount++;
                setInfoView(type, highColor, highColorCount);
            } else {
                String color = "#9E9E9E";
                if (i < colorLen) {
                    color = colors[i];
                    mPieChart.addPieSlice(new PieModel(type, value, Color.parseColor(color)));
                } else {
                    mPieChart.addPieSlice(new PieModel(type, value, Color.parseColor("#CDA67F")));
                }
            }

        }

        mPieChart.startAnimation();


        GraphListAdapter adapter = new GraphListAdapter(context,itemStatesList);
        lvGraphList.setAdapter(adapter);

    }

    private void setInfoView(String type, String highColor, int highColorCount) {

        if (highColorCount == 0) {
            View viewRem = findViewById(R.id.view1);
            TextView tv1 = findViewById(R.id.tv1);
            viewRem.setBackgroundColor(Color.parseColor(highColor));
            tv1.setText(type);
        }
        if (highColorCount == 1) {
            View viewRem = findViewById(R.id.view2);
            TextView tv2 = findViewById(R.id.tv2);
            viewRem.setBackgroundColor(Color.parseColor(highColor));
            tv2.setText(type);
        }
        if (highColorCount == 2) {
            View viewRem = findViewById(R.id.view3);
            TextView tv3 = findViewById(R.id.tv3);
            viewRem.setBackgroundColor(Color.parseColor(highColor));
            tv3.setText(type);
        }
        if (highColorCount == 3) {
            View viewRem = findViewById(R.id.view4);
            TextView tv4 = findViewById(R.id.tv4);
            viewRem.setBackgroundColor(Color.parseColor(highColor));
            tv4.setText(type);
        }
        if (highColorCount == 4) {
            View viewRem = findViewById(R.id.view5);
            TextView tv5 = findViewById(R.id.tv5);
            viewRem.setBackgroundColor(Color.parseColor(highColor));
            tv5.setText(type);
        }
        if (highColorCount == 5) {
            View viewRem = findViewById(R.id.view6);
            TextView tv6 = findViewById(R.id.tv6);
            viewRem.setBackgroundColor(Color.parseColor(highColor));
            tv6.setText(type);
        }
        if (highColorCount == 6) {
            View viewRem = findViewById(R.id.view7);
            TextView tv7 = findViewById(R.id.tv7);
            viewRem.setBackgroundColor(Color.parseColor(highColor));
            tv7.setText(type);
        }

    }

    private void initialize() {

        context = ActivityGraphDetails.this;
        database = AppDatabase.getInstance(this);

        lvGraphList = findViewById(R.id.lvGraphList);
        mPieChart = (PieChart) findViewById(R.id.piechart);

        llResponse = findViewById(R.id.llResponse);
        llServerError = findViewById(R.id.llServerError);

        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        month = month + 1;
        date = day + "/" + month + "/" + year;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return true;
    }

    private boolean getIsCleared() {

        String lastDateCleared = CommonUtil.getSharePreferenceString(context, GlobalData.LAST_CLEAR_DATE, "0");

        if (!lastDateCleared.equalsIgnoreCase(date)) {
            return false;
        } else {
            toastMsg("You can't clear record twice a day!");
            return true;
        }
    }

    public void toastMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        changeViewToPie();
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


    public class CustomDialogClass extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Dialog d;
        public Button yes, no;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.lay_custom_dialog);
            yes = (Button) findViewById(R.id.btnClearRecords);
            no = (Button) findViewById(R.id.btnCancelClear);
            yes.setOnClickListener(this);
            no.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnClearRecords:
                    clearRecords();
                    break;
                case R.id.btnCancelClear:
                    this.dismiss();
                    break;
                default:
                    break;
            }
        }
    }


    private void clearRecords() {

        boolean w = database.saveMonthGraphDetails(date);

        if (w) {

            CommonUtil.setSharePreferenceString(context, GlobalData.LAST_CLEAR_DATE, date);
            CommonUtil.setSharePreferenceString(context, GlobalData.PURCHASE_STATUS, "0");

            boolean init = database.initializeTransStatsToZero(itemStatesList);

            if (init) {
                toastMsg("Records are saved..");
                finish();
            }
        } else {
            toastMsg("Failed to clear..!");
        }

    }


    private boolean rotate = false;
    private View lyt_change_pri;
    private View lyt_save_clr;


    private void initializeFab() {

        View back_drop = findViewById(R.id.back_drop);
        final FloatingActionButton fab_change_pri = findViewById(R.id.fab_change_pri);
        final FloatingActionButton fab_save_clr = findViewById(R.id.fab_save_clr);
        final FloatingActionButton fab_add = findViewById(R.id.fab_add);
        lyt_change_pri = findViewById(R.id.lyt_change_pri);
        lyt_save_clr = findViewById(R.id.lyt_save_clr);
        ViewAnimation.initShowOut(lyt_change_pri);
        ViewAnimation.initShowOut(lyt_save_clr);

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

        fab_change_pri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePriority();
            }
        });

        fab_save_clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndClear();
            }
        });

    }


    private void toggleFabMode(View v) {
        rotate = ViewAnimation.rotateFab(v, !rotate);
        if (rotate) {
            ViewAnimation.showIn(lyt_change_pri);
            ViewAnimation.showIn(lyt_save_clr);
        } else {
            ViewAnimation.showOut(lyt_change_pri);
            ViewAnimation.showOut(lyt_save_clr);
        }
    }


    private void saveAndClear() {

        boolean isCleared = getIsCleared();

        if (!isCleared) {

            String purchaseStatus = CommonUtil.getSharePreferenceString(context, GlobalData.PURCHASE_STATUS, "0");

            if (purchaseStatus.equalsIgnoreCase(GlobalData.COMPLETE)) {
                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.lay_custom_dialog);
                Button yes = (Button) dialog.findViewById(R.id.btnClearRecords);
                Button no = (Button) dialog.findViewById(R.id.btnCancelClear);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearRecords();
                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            } else {
                toastMsg("No record added yet");
            }

        }
    }


    private void changePriority() {
        Intent intent = new Intent(ActivityGraphDetails.this, ActivityChangeItemsPriority.class);
        startActivity(intent);
        finish();
    }


}

