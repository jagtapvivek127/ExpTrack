package com.webfarmatics.exptrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.webfarmatics.exptrack.adapters.TransactionListAdapter;
import com.webfarmatics.exptrack.bean.BeanTransactions;
import com.webfarmatics.exptrack.utils.AppDatabase;

import java.util.ArrayList;

public class ActivityWallet extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = ActivityWallet.class.getSimpleName();

    private Context context;
    private ListView lvWalletTransactions;
    private TextView tvWalBalance;
    private AppDatabase database;
    private ArrayList<BeanTransactions> transactionsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        initialize();

        changeView();

        initToolbar();

        lvWalletTransactions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BeanTransactions transaction = transactionsList.get(position);
                Intent intent = new Intent(ActivityWallet.this, ActivityTransactionDetails.class);
                intent.putExtra("transaction", transaction);
                startActivity(intent);
            }
        });

    }

    private void initialize() {

        context = ActivityWallet.this;
        lvWalletTransactions = findViewById(R.id.lvWalletTransactions);
        Button btnAddTransfer = findViewById(R.id.btnAddTransfer);
        Button btnAddWallBal = findViewById(R.id.btnAddWallBal);
        tvWalBalance = findViewById(R.id.tvWalBalance);

        database = AppDatabase.getInstance(this);

        btnAddTransfer.setOnClickListener(this);
        btnAddWallBal.setOnClickListener(this);

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


    private void changeView() {

        String bankBalance = database.getWalletBalance();
        database.logMessage(TAG, "BB " + bankBalance);
        String r = getResources().getString(R.string.rupee_sign);
        tvWalBalance.setText(r + " " + bankBalance);

        transactionsList = database.getTransactionsListByType("Wallet");

        if (transactionsList != null) {

            TransactionListAdapter adapter = new TransactionListAdapter(context, transactionsList);
            lvWalletTransactions.setAdapter(adapter);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        changeView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddTransfer:
                Intent intentP = new Intent(ActivityWallet.this, ActivityAddExpenses.class);
                startActivity(intentP);
                break;

            case R.id.btnAddWallBal:
                Intent intentB = new Intent(ActivityWallet.this, ActivityBank.class);
                startActivity(intentB);
                finish();
                break;

        }
    }
}
