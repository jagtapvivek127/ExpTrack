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
import com.webfarmatics.exptrack.adapters.SingleLoanListAdapter;
import com.webfarmatics.exptrack.bean.BeanLoanHelper;
import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.GlobalData;

import java.util.ArrayList;

public class ActivityClearLoanDetails extends AppCompatActivity {

    private Context context;
    private ListView lvLoanList;
    private LinearLayout llServerError;
    private AppDatabase database;
    private FloatingActionButton fabDelete;
    private ArrayList<BeanLoanHelper> loanHelpersList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_loan_details);

        initialize();

        initToolbar();

        MobileAds.initialize(context, GlobalData.ADD_ID);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        loanHelpersList = database.getSingleLoanList();

        if (loanHelpersList != null) {

            SingleLoanListAdapter adapter = new SingleLoanListAdapter(context, loanHelpersList);
            lvLoanList.setAdapter(adapter);

        } else {
            lvLoanList.setVisibility(View.GONE);
            llServerError.setVisibility(View.VISIBLE);
        }


        lvLoanList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageView img = view.findViewById(R.id.img_state);

                BeanLoanHelper transaction = loanHelpersList.get(position);
                String state = transaction.getState();
                if (state.equalsIgnoreCase("unselected")) {
                    transaction.setState("selected");
                    img.setImageResource(R.drawable.selected_icon);
                } else {
                    transaction.setState("unselected");
                    img.setImageResource(R.drawable.unsel_icon);
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


    private void initialize() {
        context = ActivityClearLoanDetails.this;
        lvLoanList = findViewById(R.id.lvLoanList);
        llServerError = findViewById(R.id.llServerError);
        fabDelete = findViewById(R.id.fabDelete);
        database = AppDatabase.getInstance(this);
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

        for (int i = 0; i < loanHelpersList.size(); i++) {
            BeanLoanHelper transaction = loanHelpersList.get(i);
            String state = transaction.getState();
            Log.e("clearSingle", " " + state + " " + transaction.getAmount());
        }

        boolean deleted = database.clearSelectedLoans(loanHelpersList);
        if (deleted) {
            toastMsg("Selected Loan details deleted.");
            finish();
        } else {
            toastMsg("failed to delete Loan details..!");
        }
    }

    public void toastMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
