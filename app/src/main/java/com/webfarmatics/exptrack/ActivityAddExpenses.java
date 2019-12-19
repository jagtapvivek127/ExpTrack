package com.webfarmatics.exptrack;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.webfarmatics.exptrack.bean.BeanPurchase;
import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.CommonUtil;
import com.webfarmatics.exptrack.utils.GlobalData;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityAddExpenses extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private EditText edtPaymentDesc, edtAmount, edtItemType;
    private TextView tvDate, tvError;
    private Spinner spinnerItem;
    private CheckBox cbAddItem;
    private LinearLayout llWalletPay, llBankPay, llOtherItem;
    private boolean paidByBank = false, paidByWallet = false;

    private AppDatabase database;

    private int day, month, year, count = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);

        initialize();

        initToolbar();

        MobileAds.initialize(context, GlobalData.ADD_ID);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        ArrayList<String> itemsList = database.getItemsList();

        if (itemsList != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, itemsList);
            spinnerItem.setAdapter(adapter);
        }

        spinnerItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSel = spinnerItem.getSelectedItem().toString();
                if (itemSel.equalsIgnoreCase("Other")) {
                    llOtherItem.setVisibility(View.VISIBLE);
                } else {
                    llOtherItem.setVisibility(View.GONE);
                    cbAddItem.setChecked(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initialize() {

        context = ActivityAddExpenses.this;
        tvDate = findViewById(R.id.tvDate);
        tvError = findViewById(R.id.tvError);
        edtAmount = findViewById(R.id.edtAmount);
        edtItemType = findViewById(R.id.edtItemType);
        edtPaymentDesc = findViewById(R.id.edtPaymentDesc);

        cbAddItem = findViewById(R.id.cbAddToItem);

        Button btnSave = findViewById(R.id.btnSave);

        spinnerItem = findViewById(R.id.spinnerItem);

        llWalletPay = findViewById(R.id.llWalletPay);
        llBankPay = findViewById(R.id.llBankPay);
        llOtherItem = findViewById(R.id.llOtherItem);

        database = AppDatabase.getInstance(this);

        Calendar calendar = Calendar.getInstance();

        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        showDateFirst(day, month, year);

        llBankPay.setOnClickListener(this);
        llWalletPay.setOnClickListener(this);

        tvDate.setOnClickListener(this);
        btnSave.setOnClickListener(this);

    }

    private void savePurchaseDetails() {

        String amtOne = edtAmount.getText().toString();
        if (amtOne.isEmpty()) {
            toastMsg("enter amount");
            edtAmount.setError("require");
            return;
        }

        String loanDesc = edtPaymentDesc.getText().toString();
        if (loanDesc.isEmpty()) {
            toastMsg("enter comment");
            edtPaymentDesc.setError("require");
            return;
        }

        String finalItem = null;
        String itemEdt = null;

        String item = spinnerItem.getSelectedItem().toString();

        if (cbAddItem.isChecked()) {
            itemEdt = edtItemType.getText().toString();
            if (itemEdt.isEmpty()) {
                toastMsg("enter item name");
                edtItemType.setError("require");
                return;
            }
            database.saveNewItem(itemEdt);
        } else {

        }

        if (item.equalsIgnoreCase("Other")) {

            itemEdt = edtItemType.getText().toString();

            if (itemEdt.equalsIgnoreCase("Item Name")) {
                finalItem = item;
            } else {
                finalItem = itemEdt;
            }
        } else {
            finalItem = item;
        }

        Log.e("finalItem", "" + finalItem);

        String date = tvDate.getText().toString();
        String desc = edtPaymentDesc.getText().toString();
        String amount = edtAmount.getText().toString();
        String paymentBy = null;

        int amt = Integer.parseInt(amount);

        if (paidByWallet) {
            String walletBal = database.getWalletBalance();
            int wBal = Integer.parseInt(walletBal);
            if (wBal < amt) {
                tvError.setText("Wallet balance is low... " + walletBal);
                tvError.setVisibility(View.VISIBLE);
                return;
            } else {
                database.reduceWalletBal(amt);
                paymentBy = "Wallet";
            }
        } else if (paidByBank) {
            String bankBal = database.getBankBalance();
            int bBal = Integer.parseInt(bankBal);
            if (bBal < amt) {
                tvError.setText("Bank balance is low... " + bankBal);
                tvError.setVisibility(View.VISIBLE);
                return;
            } else {
                database.reduceBankBal(amt);
                paymentBy = "Card";
            }
        } else {
            toastMsg("Select payment by Wallet or Card");
            return;
        }

        Log.e("tag", "wallet   " + paidByWallet);
        Log.e("tag", "bank    " + paidByBank);

        BeanPurchase purchase = new BeanPurchase(finalItem, amt, desc, date, item, paymentBy);

        boolean saved = database.savePurchase(purchase);

        if (saved) {
//            toastMsg("Purchase saved...");
            CommonUtil.setSharePreferenceString(context, GlobalData.PURCHASE_STATUS, GlobalData.COMPLETE);
            Log.e("savePurchase", "   savePurchase  " + saved);
            if (cbAddItem.isChecked()) {
                boolean trans = database.saveNewTransaction(itemEdt, amt);
                if (!trans) {
                    toastMsg("New Item failed...");
                }
            }

            database.printTransType();
            database.printTransactions();
            finish();
        } else {
            toastMsg("Purchase failed...");
            Log.e("savePurchase", "   savePurchase  " + saved);
        }

    }


    public void toastMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llBankPay:
                if (paidByWallet) {
                    paidByBank = true;
                    paidByWallet = false;
                    llBankPay.setBackgroundResource(R.drawable.select_box);
                    llWalletPay.setBackgroundResource(R.drawable.comment_box);
                    saveTransaction();
                } else {
                    paidByBank = true;
                    llBankPay.setBackgroundResource(R.drawable.select_box);
                    saveTransaction();
                }
                break;

            case R.id.llWalletPay:
                if (paidByBank) {
                    paidByBank = false;
                    paidByWallet = true;
                    llBankPay.setBackgroundResource(R.drawable.comment_box);
                    llWalletPay.setBackgroundResource(R.drawable.select_box);
                    saveTransaction();
                } else {
                    paidByWallet = true;
                    llWalletPay.setBackgroundResource(R.drawable.select_box);
                    saveTransaction();
                }
                break;

            case R.id.tvDate:
                showDialog(123);
                break;

            case R.id.btnSave:
                savePurchaseDetails();
                break;
        }
    }

    private void saveTransaction() {
        Log.e("purchase", "b " + paidByBank);
        Log.e("purchase", "w " + paidByWallet);
    }


    private void showDateFirst(int day, int month, int year) {
        month = month + 1;
        Log.e("error", "showDateFirst   day " + day + " month " + month + " year " + year);
        tvDate.setText(day + "/" + month + "/" + year);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(context, myDateListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int y, int m, int d) {
            count++;

            Log.e("error", "DatePickerDialog  initial date count " + count);
            Log.e("error", "DatePickerDialog  initial day " + day + " month " + month + " year " + year);

            int selectedDate = datePicker.getDayOfMonth();
            int selectedMonth = datePicker.getMonth();
            int selectedYear = datePicker.getYear();

            Log.e("error", "DatePickerDialog new Date  day " + selectedDate + " month " + selectedMonth + " year " + selectedYear);

            setPurchaseDate(selectedDate, selectedMonth, selectedYear);

        }
    };


    private void setPurchaseDate(int day, int month, int year) {
        month = month + 1;
        String date = day + "/" + month + "/" + year;
        tvDate.setText(date);
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
