package com.webfarmatics.exptrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.webfarmatics.exptrack.bean.BeanTransactions;
import com.webfarmatics.exptrack.utils.GlobalData;


public class ActivityTransactionDetails extends AppCompatActivity {

    private Context context;
    private TextView tvType, tvDate, tvAmount, tvDesc;
    private String r = "Rs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_details);

        initialize();

        initToolbar();

        MobileAds.initialize(context, GlobalData.ADD_ID);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        Intent intent = getIntent();

        BeanTransactions transaction = (BeanTransactions) intent.getSerializableExtra("transaction");

        tvType.setText(transaction.getItem());
        tvDate.setText(transaction.getDate());
        tvAmount.setText(r+" "+transaction.getAmount());
        tvDesc.setText(transaction.getDesc());
    }

    private void initialize() {
        context = ActivityTransactionDetails.this;
        tvType = findViewById(R.id.tvType);
        tvDate = findViewById(R.id.tvDate);
        tvAmount = findViewById(R.id.tvAmount);
        tvDesc = findViewById(R.id.tvDesc);
        r = getResources().getString(R.string.rupee_sign);
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
}
