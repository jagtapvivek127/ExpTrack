package com.webfarmatics.exptrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.webfarmatics.exptrack.bean.BeanLoanHelper;
import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.CommonUtil;
import com.webfarmatics.exptrack.utils.GlobalData;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private int count = 0;
    private Context context;
    private String date = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        initialize();


        AppDatabase database = AppDatabase.getInstance(this);
        ArrayList<BeanLoanHelper> loanArrayList = database.getSingleLoanList();
        if (loanArrayList != null) {

            for (int i = 0; i < loanArrayList.size(); i++) {
                BeanLoanHelper loanHelper = loanArrayList.get(i);
                String retDate = loanHelper.getReturnDate();
                if (retDate.equalsIgnoreCase(date)) {
                    count++;
                }
            }
        }

        if (count > 0) {
            toastMsg("Check Loan Details...");
        }
    }

    private void initialize() {

        context = ActivityDashboard.this;

        LinearLayout llCashLoan = findViewById(R.id.ll_cash_loan);
        LinearLayout llBag = findViewById(R.id.ll_bag);
        LinearLayout llWallet = findViewById(R.id.ll_wallet);
        LinearLayout llCalculator = findViewById(R.id.ll_calc);
        LinearLayout llBank = findViewById(R.id.ll_bank);
        LinearLayout llGraph = findViewById(R.id.ll_graph);

        LinearLayout ll_All_details = findViewById(R.id.ll_All_details);
        LinearLayout ll_Splitbill = findViewById(R.id.ll_SplitBill);

        llCashLoan.setOnClickListener(this);
        llBag.setOnClickListener(this);
        llWallet.setOnClickListener(this);
        llCalculator.setOnClickListener(this);
        llBank.setOnClickListener(this);
        llGraph.setOnClickListener(this);

        ll_All_details.setOnClickListener(this);
        ll_Splitbill.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        showDateFirst(day, month, year);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_feedback) {
            Intent intentL = new Intent(context, ActivityFeedback.class);
            startActivity(intentL);
        } else if (id == R.id.nav_exit) {
            finish();
        } else if (id == R.id.nav_logout) {
            CommonUtil.setSharePreferenceString(context, GlobalData.REMEMBER_ME, GlobalData.NO_REMBER);
            Intent intentL = new Intent(context, ActivityAppLogin.class);
            intentL.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentL);
        } else if (id == R.id.nav_about_us) {
            Intent intentL = new Intent(context, ActivityAboutUs.class);
            startActivity(intentL);
        }
        /*else if (id == R.id.nav_chglang) {
            CommonUtil.setSharePreferenceString(context, GlobalData.LANGUAGE_SELECTED, GlobalData.NO);
            CommonUtil.setSharePreferenceString(context, GlobalData.LANGUAGE, GlobalData.ENGLISH_LANGUAGE);
            Intent intentS = new Intent(context, ActivitySelectLanguage.class);
            intentS.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentS);
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ll_cash_loan:
                Intent intentL = new Intent(context, ActivityLoan.class);
                startActivity(intentL);
                break;

            case R.id.ll_bag:
                Intent intentP = new Intent(context, ActivityAddExpenses.class);
                startActivity(intentP);
                break;

            case R.id.ll_calc:
//                Intent intentT = new Intent(context, TransactionListAcitivty.class);
                Intent intentT = new Intent(context, ActivityTransactionListTwo.class);
                startActivity(intentT);
                break;

            case R.id.ll_graph:
                Intent intentG = new Intent(context, ActivityGraphDetails.class);
                startActivity(intentG);
                break;

            case R.id.ll_wallet:
                Intent intentW = new Intent(context, ActivityWallet.class);
                startActivity(intentW);
                break;

            case R.id.ll_bank:
                Intent intentB = new Intent(context, ActivityBank.class);
                startActivity(intentB);
                break;

            case R.id.ll_SplitBill:
                Intent intentF = new Intent(context, ActivitySplitBillList.class);
                startActivity(intentF);
                break;

            case R.id.ll_All_details:
                Intent intentA = new Intent(context, ActivityMonthExpenses.class);
                startActivity(intentA);
                break;
        }
    }

    public void toastMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    private void showDateFirst(int day, int month, int year) {
        month = month + 1;
        Log.e("error", "showDateFirst   day " + day + " month " + month + " year " + year);
        date = day + "/" + month + "/" + year;
    }
}
