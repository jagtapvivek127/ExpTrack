package com.webfarmatics.exptrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.webfarmatics.exptrack.adapters.TransactionListAdapter;
import com.webfarmatics.exptrack.bean.BeanPurchase;
import com.webfarmatics.exptrack.bean.BeanTransactions;
import com.webfarmatics.exptrack.utils.AppDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityBank extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = ActivityBank.class.getSimpleName();
    private Context context;
    private Button btnProceedPayment, btnAddToWallet, btnProceedPayment1, btnAddToBank, btnBankTransfer;
    private EditText edtRupees, edtRupees1;
    private TextView tvBalance, tvAvailBalance, tvAvailBalance1;
    private ImageView imgCancel, imgCancel1;
    private ListView lvBankTransactions;

    private BottomSheetBehavior mBottomSheetBehaviour, mBottomSheetBehaviour1;

    private AppDatabase database;
    private String bankBalance;

    private Calendar calendar;
    private int day, month, year;
    private String date = null;
    private ArrayList<BeanTransactions> transactionsList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);

        initialize();

        initToolbar();

        bankBalance = database.getBankBalance();

        database.logMessage(TAG, "BB " + bankBalance);
        String r = getResources().getString(R.string.rupee_sign);
        tvBalance.setText(r + " " + bankBalance);
        tvAvailBalance.setText("Bank Balance " + r + " " + bankBalance);

        tvAvailBalance1.setText("Current Balance " + r + " " + bankBalance);

        btnAddToWallet.setOnClickListener(this);
        imgCancel.setOnClickListener(this);
        btnAddToBank.setOnClickListener(this);
        btnBankTransfer.setOnClickListener(this);
        imgCancel1.setOnClickListener(this);
        btnProceedPayment.setOnClickListener(this);
        btnProceedPayment1.setOnClickListener(this);

        lvBankTransactions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BeanTransactions transaction = transactionsList.get(position);
                Intent intent = new Intent(ActivityBank.this, ActivityTransactionDetails.class);
                intent.putExtra("transaction", transaction);
                startActivity(intent);
            }
        });

        changeView();

    }

    private void initialize() {

        context = ActivityBank.this;
        btnProceedPayment = findViewById(R.id.btnProceedPayment);
        btnAddToWallet = findViewById(R.id.btnAddToWallet);
        btnBankTransfer = findViewById(R.id.btnBankTransfer);
        edtRupees = findViewById(R.id.edtRupees);
        tvBalance = findViewById(R.id.tvBalance);
        tvAvailBalance = findViewById(R.id.tvAvailBalance);
        btnAddToBank = findViewById(R.id.btnAddToBank);
        imgCancel = findViewById(R.id.imgCancel);
        lvBankTransactions = findViewById(R.id.lvBankTransactions);

        tvAvailBalance1 = findViewById(R.id.tvAvailBalance1);
        imgCancel1 = findViewById(R.id.imgCancel1);
        edtRupees1 = findViewById(R.id.edtRupees1);
        btnProceedPayment1 = findViewById(R.id.btnProceedPayment1);

        View nestedScrollView = (View) findViewById(R.id.nestedScrollView);

        View nestedScrollView1 = (View) findViewById(R.id.nestedScrollView1);

        mBottomSheetBehaviour = BottomSheetBehavior.from(nestedScrollView);

        mBottomSheetBehaviour1 = BottomSheetBehavior.from(nestedScrollView1);

//        mBottomSheetBehaviour.setPeekHeight(200);    //Set the peek height

        mBottomSheetBehaviour.setPeekHeight(0);    //Set the peek height

        mBottomSheetBehaviour1.setPeekHeight(0);    //Set the peek height

//        mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);    // Will show the bottom sheet
        mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);    // Will hide the bottom sheet
        mBottomSheetBehaviour1.setState(BottomSheetBehavior.STATE_COLLAPSED);    // Will hide the bottom sheet

        database = AppDatabase.getInstance(this);


        calendar = Calendar.getInstance();

        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        month = month + 1;

        date = day + "/" + month + "/" + year;

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
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnAddToWallet:

                if (mBottomSheetBehaviour.getState() == 4) {
                    mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);    // Will show the bottom sheet
                }

                break;

            case R.id.imgCancel:

                if (mBottomSheetBehaviour.getState() == 3) {
                    mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);    // Will show the bottom sheet
                }

                break;

            case R.id.btnAddToBank:

                if (mBottomSheetBehaviour1.getState() == 4) {
                    mBottomSheetBehaviour1.setState(BottomSheetBehavior.STATE_EXPANDED);    // Will show the bottom sheet
                }

                break;

            case R.id.imgCancel1:

                if (mBottomSheetBehaviour1.getState() == 3) {
                    mBottomSheetBehaviour1.setState(BottomSheetBehavior.STATE_COLLAPSED);    // Will show the bottom sheet
                }

                break;

            case R.id.btnProceedPayment:

                addMoneyToWallet();

                break;

            case R.id.btnProceedPayment1:

                addMoneyToBank();

                break;

            case R.id.btnBankTransfer:

                Intent intentP = new Intent(ActivityBank.this, ActivityAddExpenses.class);
                startActivity(intentP);

                break;

        }
    }


    private void addMoneyToBank() {

        String amtOne = edtRupees1.getText().toString();
        if (amtOne.isEmpty()) {
            toastMsg("enter amount");
            edtRupees1.setError("require");
            return;
        }

        int amount = Integer.parseInt(edtRupees1.getText().toString());

        boolean w = database.addToBank(amount);
        if (w) {
//            toastMsg("Money added to Bank account..");
            changeView();
            addBankTransaction(amount);
        } else {
            toastMsg("Failed..! Bank return wrong..");
        }
    }

    private void addMoneyToWallet() {

        String amtOne = edtRupees.getText().toString();
        if (amtOne.isEmpty()) {
            toastMsg("enter amount");
            edtRupees.setError("require");
            return;
        }

        int amount = Integer.parseInt(edtRupees.getText().toString());

        int availBal = Integer.parseInt(bankBalance);
        if (amount > availBal) {
            toastMsg("Available balance is less than entered amount...");
        } else {
            boolean b = database.reduceBankBal(amount);
            if (b) {
                boolean w = database.addToWallet(amount);
                if (w) {
//                    toastMsg("Money added to Wallet..");
                    mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);    // Will show the bottom sheet
                    edtRupees.setText("");
                    changeView();
                    addWalletTransaction(amount);
                } else
                    toastMsg("Failed to add money in wallet..!");
            }
            String bankbal = database.getBankBalance();
            String walletbal = database.getWalletBalance();
            Log.e("msg", "---Bank   " + bankbal + "   wallet   " + walletbal);
        }
    }

    private void changeView() {

        bankBalance = database.getBankBalance();

        database.logMessage(TAG, "BB " + bankBalance);
        String r = getResources().getString(R.string.rupee_sign);
        tvBalance.setText(r + " " + bankBalance);
        tvAvailBalance.setText("Bank Balance " + r + " " + bankBalance);
        edtRupees1.setText("");

        tvAvailBalance1.setText("Current Balance " + r + " " + bankBalance);

        mBottomSheetBehaviour1.setState(BottomSheetBehavior.STATE_COLLAPSED);    // Will show the bottom sheet

        transactionsList = database.getTransactionsListByType("Card");

        if (transactionsList != null) {

            TransactionListAdapter adapter = new TransactionListAdapter(context, transactionsList);
            lvBankTransactions.setAdapter(adapter);
        }

    }

    private void addBankTransaction(int amount) {

        Log.e("error", "showDateFirst   day " + day + " month " + month + " year " + year);

        BeanPurchase purchase = new BeanPurchase("Money", amount, "Money added to bank.", date, "Bank", "N/A");
        boolean saved = database.savePurchase(purchase);

        if (saved) {
//            toastMsg("Bank Transaction saved...");
            Log.e("savePurchase", "   savePurchase  " + saved);
            database.printTransType();
            database.printTransactions();
        } else {
            toastMsg("Bank Transaction failed...");
            Log.e("savePurchase", "   savePurchase  " + saved);
        }
    }

    private void addWalletTransaction(int amount) {

        Log.e("addWalletTrans", "showDateFirst   day " + day + " month " + month + " year " + year);

        BeanPurchase purchase = new BeanPurchase("Money", amount, "Money added to wallet.", date, "Wallet", "N/A");
        boolean saved = database.savePurchase(purchase);

        if (saved) {
//            toastMsg("Wallet Transaction saved...");
            database.printTransType();
            database.printTransactions();
        } else {
            toastMsg("Wallet Transaction failed...");
        }

    }

    public void toastMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        changeView();
    }
}
