package com.webfarmatics.exptrack;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.GlobalData;

import java.util.ArrayList;

public class ActivityClearBatchTransaction extends AppCompatActivity {

    private Context context;
    private AppDatabase database;
    private Spinner spinnerBatchItem;
    private Button btnBatchClear;
    private TextView tvHint, tvHint1, tvHeading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_batch_trans);

        initialize();

        initToolbar();

        MobileAds.initialize(context, GlobalData.ADD_ID);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        ArrayList<String> itemsList = database.getItemsList();
        itemsList.add(0, "Select Item");
        if (itemsList.size() > 1) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, itemsList);
            spinnerBatchItem.setAdapter(adapter);
        }

        spinnerBatchItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSel = spinnerBatchItem.getSelectedItem().toString();
                if (!itemSel.equalsIgnoreCase("Select Item")) {
                    tvHint.setText("The transactions made with ");
                    tvHeading.setText(itemSel);
                    tvHint1.setText("will be deleted permanently.");
                    tvHint.setVisibility(View.VISIBLE);
                    tvHint1.setVisibility(View.VISIBLE);
                    tvHeading.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnBatchClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemSel = spinnerBatchItem.getSelectedItem().toString();
                boolean deleted = database.deleteTransactionBatch(itemSel);
                if (deleted) {
                    toastMsg("All " + itemSel + " records deleted..");
                    finish();
                } else {
                    toastMsg("No Record found..!");
                }
            }
        });
    }

    private void initialize() {
        context = ActivityClearBatchTransaction.this;
        database = AppDatabase.getInstance(this);
        spinnerBatchItem = findViewById(R.id.spinnerBatchItem);
        btnBatchClear = findViewById(R.id.btnBatchClear);
        tvHint = findViewById(R.id.tvTransHint);
        tvHint1 = findViewById(R.id.tvTransHint1);
        tvHeading = findViewById(R.id.tvHeading);
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

    public void toastMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
