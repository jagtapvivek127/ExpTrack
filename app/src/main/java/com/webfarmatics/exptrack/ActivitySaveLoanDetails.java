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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.webfarmatics.exptrack.bean.BeanLoan;
import com.webfarmatics.exptrack.bean.BeanPurchase;
import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.GlobalData;

import java.util.Calendar;

public class ActivitySaveLoanDetails extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private AppDatabase database;
    private RadioButton rbGiven, rbTaken;
    private EditText edtFromTo, edtLoAmount, edtLoPaymentDesc;
    private TextView tvLoDate, tvReDate, tvLoError, tvLoanHint;
    private LinearLayout llWalletIn, llBankIn;

    private boolean bankSelected = false, walletSelected = false;
    private int day, month, year, count = 0, day1, month1, year1;
    private String date = null;

    private static final int LOAN_DATE_ID = 127;
    private static final int LOAN_RETURN_DATE_ID = 122;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_save_loan_details);

        initialize();

        initToolbar();

        MobileAds.initialize(context, GlobalData.ADD_ID);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

    }

    private void initialize() {

        context = ActivitySaveLoanDetails.this;
        database = AppDatabase.getInstance(this);
        rbGiven = findViewById(R.id.rbGiven);
        rbTaken = findViewById(R.id.rbTaken);
        edtFromTo = findViewById(R.id.edtFromTo);
        edtLoAmount = findViewById(R.id.edtLoAmount);
        edtLoPaymentDesc = findViewById(R.id.edtLoPaymentDesc);
        tvLoDate = findViewById(R.id.tvLoDate);
        tvReDate = findViewById(R.id.tvReDate);
        tvLoError = findViewById(R.id.tvLoError);
        tvLoanHint = findViewById(R.id.tvLoanHint);
        Button btnLoSave = findViewById(R.id.btnLoSave);
        llWalletIn = findViewById(R.id.llWalletIn);
        llBankIn = findViewById(R.id.llBankIn);

        Calendar calendar = Calendar.getInstance();

        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        showDateFirst(day, month, year);

        day1 = calendar.get(Calendar.DAY_OF_MONTH);
        month1 = calendar.get(Calendar.MONTH);
        year1 = calendar.get(Calendar.YEAR);

        showDateReturn(day1, month1, year1);

        llWalletIn.setOnClickListener(this);
        llBankIn.setOnClickListener(this);

        tvLoDate.setOnClickListener(this);
        tvReDate.setOnClickListener(this);
        btnLoSave.setOnClickListener(this);
        rbTaken.setOnClickListener(this);
        rbGiven.setOnClickListener(this);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.llBankIn:
                if (walletSelected) {
                    bankSelected = true;
                    walletSelected = false;
                    llBankIn.setBackgroundResource(R.drawable.select_box);
                    llWalletIn.setBackgroundResource(R.drawable.comment_box);
                } else {
                    bankSelected = true;
                    llBankIn.setBackgroundResource(R.drawable.select_box);
                }
                break;

            case R.id.llWalletIn:
                if (bankSelected) {
                    bankSelected = false;
                    walletSelected = true;
                    llBankIn.setBackgroundResource(R.drawable.comment_box);
                    llWalletIn.setBackgroundResource(R.drawable.select_box);
                } else {
                    walletSelected = true;
                    llWalletIn.setBackgroundResource(R.drawable.select_box);
                }
                break;

            case R.id.tvLoDate:
                showDialog(LOAN_DATE_ID);
                break;

            case R.id.tvReDate:
                showDialog(LOAN_RETURN_DATE_ID);
                break;

            case R.id.btnLoSave:
                saveLoanDetails();
                break;

            case R.id.rbGiven:
                tvLoanHint.setText("Debit from");
                tvLoanHint.setVisibility(View.VISIBLE);
                break;

            case R.id.rbTaken:
                tvLoanHint.setText("Credit in");
                tvLoanHint.setVisibility(View.VISIBLE);
                break;
        }
    }


    private void showDateFirst(int day, int month, int year) {
        month = month + 1;
        Log.e("error", "showDateFirst   day " + day + " month " + month + " year " + year);
        tvLoDate.setText(day + "/" + month + "/" + year);
    }

    private void showDateReturn(int day1, int month1, int year1) {
        month1 = month1 + 1;
        Log.e("error", "showReturnDate   day1 " + day1 + " month1 " + month1 + " year1 " + year1);
//        tvReDate.setText(day1 + "/" + month1 + "/" + year1);
        tvReDate.setText("N/A");
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        if (id == LOAN_DATE_ID) {
            return new DatePickerDialog(context, myDateListener, year, month, day);
        } else {
            return new DatePickerDialog(context, myDateListener1, year1, month1, day1);
        }
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

            setLoanDate(selectedDate, selectedMonth, selectedYear);

        }
    };

    private DatePickerDialog.OnDateSetListener myDateListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int y, int m, int d) {

            count++;

            Log.e("error", "DatePickerDialog  initial date count " + count);
            Log.e("error", "DatePickerDialog  initial day " + day + " month " + month + " year " + year);

            int selectedDate = datePicker.getDayOfMonth();
            int selectedMonth = datePicker.getMonth();
            int selectedYear = datePicker.getYear();

            Log.e("error", "DatePickerDialog new Date  day " + selectedDate + " month " + selectedMonth + " year " + selectedYear);

            setReturnDate(selectedDate, selectedMonth, selectedYear);

        }
    };

    private void setLoanDate(int day, int month, int year) {
        month = month + 1;
        date = day + "/" + month + "/" + year;
        tvLoDate.setText(date);
    }


    private void setReturnDate(int day, int month, int year) {
        month = month + 1;
        date = day + "/" + month + "/" + year;
        tvReDate.setText(date);
    }

    private void saveLoanDetails() {

        String loanStatus = null;
        boolean isGiven = rbGiven.isChecked();
        boolean isTaekn = rbTaken.isChecked();
        if (isGiven)
            loanStatus = "GIVEN";
        else if (isTaekn)
            loanStatus = "TAKEN";
        else {
            toastMsg("Select Loan Given or Taken");
            return;
        }

        String fromTo = edtFromTo.getText().toString();
        String loanAmt = edtLoAmount.getText().toString();
        String loanDesc = edtLoPaymentDesc.getText().toString();
        String loanDate = tvLoDate.getText().toString();
        String returnDate = tvReDate.getText().toString();

        if (fromTo.isEmpty()) {
            toastMsg("Enter Name...");
            edtFromTo.setError("require");
            return;
        }

        if (loanAmt.isEmpty()) {
            toastMsg("Enter Amount...");
            edtLoAmount.setError("require");
            return;
        }

        if (loanDesc.isEmpty()) {
            toastMsg("Enter Comment...");
            edtLoPaymentDesc.setError("require");
            return;
        }

        if (returnDate.equalsIgnoreCase("N/A")) {
            toastMsg("Select return Date.");
            return;
        }

        int amt = Integer.parseInt(loanAmt);

        if (loanStatus.equalsIgnoreCase("GIVEN")) {

            if (walletSelected) {
                String walletbal = database.getWalletBalance();
                int bBal = Integer.parseInt(walletbal);
                if (bBal < amt) {
                    tvLoError.setText("Wallet balance is low... " + walletbal);
                    tvLoError.setVisibility(View.VISIBLE);
                    return;
                } else {
                    database.reduceWalletBal(amt);
                }
            } else if (bankSelected) {
                String bankBal = database.getBankBalance();
                int bBal = Integer.parseInt(bankBal);
                if (bBal < amt) {
                    tvLoError.setText("Bank balance is low... " + bankBal);
                    tvLoError.setVisibility(View.VISIBLE);
                    return;
                } else {
                    database.reduceBankBal(amt);
                }
            }

        } else if (loanStatus.equalsIgnoreCase("TAKEN")) {
            if (walletSelected) {
                database.addToWallet(amt);
            } else if (bankSelected) {
                database.addToBank(amt);
            }
        } else {
            toastMsg("Select Bank or Wallet");
            return;
        }

        String amount = "" + amt;

        BeanLoan loan = new BeanLoan("", fromTo, amount, loanDate, returnDate, loanDesc, loanStatus);

        boolean saved = database.saveLoanDetails(loan);
        if (saved) {
            toastMsg("Loan details saved...");
            finish();
            Log.e("Loan", "   savePurchase  " + saved);
        } else {
            toastMsg("Loan failed...");
            Log.e("Loan", "   savePurchase  " + saved);
        }

        BeanPurchase purchase = null;
        String paymentBy = null;
        if (walletSelected) {
            paymentBy = "Wallet";
            Log.e("LoanTr", "   paymentBy  " + paymentBy);
            purchase = new BeanPurchase("Loan " + loanStatus, amt, loanDesc, loanDate, "Loan", paymentBy);
        } else if (bankSelected) {
            paymentBy = "Card";
            Log.e("LoanTr", "   paymentBy  " + paymentBy);
            purchase = new BeanPurchase("Loan " + loanStatus, amt, loanDesc, loanDate, "Loan", paymentBy);
        }

        if (purchase == null) {
            return;
        }

        boolean saved12 = database.saveTransaction(purchase);

        if (saved12) {
//            toastMsg("Loan Transaction saved...");
            Log.e("LoanTr", "   Loan Transaction saved  " + saved12);
            finish();
        } else {
            toastMsg("Loan Transaction failed..!");
            Log.e("LoanTr", "   Loan Transaction failed  " + saved12);
        }


    }

    public void toastMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
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
