package com.webfarmatics.exptrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ActivityLoanDetails extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private TextView tvType, tvDate, tvAmount, tvDesc, tvLoHint, tvToForm, tvReDate;
    private LinearLayout ll_settle_loan;
    private Button btnSettleDown;
    private EditText edtLoanPaid;
    private ImageView imgUpdateLoan;
    private AppDatabase database;
    private boolean walletSelected = false, bankSelected = false, isllSettleVisible = false;
    private BeanLoan loan = null;
    private RadioButton rbLoWallet, rbLoBank;

    private String date = null, r = "Rs";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_details);

        initialize();

        initToolbar();

        MobileAds.initialize(context, GlobalData.ADD_ID);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        Intent intent = getIntent();

        loan = (BeanLoan) intent.getSerializableExtra("loan");

        tvType.setText(loan.getFrom());
        tvDate.setText(loan.getDate());
        tvReDate.setText(loan.getReturnDate());
        tvAmount.setText(r + " " + loan.getAmount());
        tvDesc.setText(loan.getDesc());

        String loanStatus = loan.getType();
        Log.e("type", "" + loanStatus);

        if (loanStatus.equalsIgnoreCase("TAKEN")) {
            tvToForm.setText("Taken From");
        } else if (loanStatus.equalsIgnoreCase("GIVEN")) {
            tvToForm.setText("Given To");
        }

        btnSettleDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isllSettleVisible) {
                    ll_settle_loan.setVisibility(View.GONE);
                    isllSettleVisible = false;
                } else {
                    ll_settle_loan.setVisibility(View.VISIBLE);
                    isllSettleVisible = true;
                }
            }
        });

        rbLoWallet.setOnClickListener(this);
        rbLoBank.setOnClickListener(this);
        imgUpdateLoan.setOnClickListener(this);

    }

    private void updateLoanDetails() {

        String owes1 = loan.getAmount();

        String paid1 = edtLoanPaid.getText().toString();
        if (paid1.isEmpty()) {
            toastMsg("enter amount");
            return;
        }
        int owesYou1 = Integer.parseInt(owes1);
        int paidYou1 = Integer.parseInt(paid1);

        if (owesYou1 < paidYou1) {

            Toast.makeText(context, "Enter correct amount.", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Check the amount below for more..", Toast.LENGTH_SHORT).show();

        } else {

            String desc = "N/A";
            if (loan.getType().equalsIgnoreCase("TAKEN")) {
                desc = "You paid " + r + " " + paidYou1 + " to " + loan.getFrom() + " as per Loan on you of " + r + " " + loan.getAmount() + " on Date " + date;
            } else if (loan.getType().equalsIgnoreCase("GIVEN")) {
                desc = loan.getFrom() + " paid " + r + " " + paidYou1 + " as per your Loan on him/her of " + r + " " + loan.getAmount() + " on Date " + date;
            }

            boolean update = database.updateLoanBill(loan.getFrom(), loan.getDate(), loan.getAmount(), loan.getType(), desc, paidYou1);

            if (update) {

                Toast.makeText(context, " Loan Details Updated...", Toast.LENGTH_SHORT).show();
                BeanPurchase purchase = null;
                String paymentBy = null;

                if (walletSelected) {
                    paymentBy = "Wallet";
                    Log.e("LoanTr", "   paymentBy  " + paymentBy);

                    purchase = new BeanPurchase("Loan Settled", paidYou1, desc, date, "Loan", paymentBy);
                } else if (bankSelected) {
                    paymentBy = "Card";
                    Log.e("LoanTr", "   paymentBy  " + paymentBy);

                    purchase = new BeanPurchase("Loan Settled", paidYou1, desc, date, "Loan", paymentBy);
                }

                if (purchase == null) {
                    return;
                }

                boolean saved = database.saveTransaction(purchase);

                if (saved) {
                    Log.e("144", "   Loan Settle saved Trans  " + saved);
                } else {
                    Log.e("144", "   Loan Settle failed Trans  " + saved);
                }
                finish();
            } else {
                Toast.makeText(context, " Loan Details Update failed...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean saveLoanTransaction(String loanStatus, int amt) {

        if (loanStatus.equalsIgnoreCase("TAKEN")) {

            if (walletSelected) {
                String walletbal = database.getWalletBalance();
                int bBal = Integer.parseInt(walletbal);
                if (bBal < amt) {
                    toastMsg("Wallet balance is low... " + walletbal);
                    return false;
                } else {
                    database.reduceWalletBal(amt);
                    return true;
                }
            } else if (bankSelected) {
                String bankBal = database.getBankBalance();
                int bBal = Integer.parseInt(bankBal);
                if (bBal < amt) {
                    toastMsg("Bank balance is low... " + bankBal);
                    return false;
                } else {
                    database.reduceBankBal(amt);
                    return true;
                }
            } else {
                toastMsg("Select option Wallet or App Bank");
            }

        } else if (loanStatus.equalsIgnoreCase("GIVEN")) {
            if (walletSelected) {
                database.addToWallet(amt);
                return true;
            } else if (bankSelected) {
                database.addToBank(amt);
                return true;
            } else {
                toastMsg("Select option Wallet or App Bank");
            }
        }

        return false;

    }

    private void initialize() {

        context = ActivityLoanDetails.this;
        database = AppDatabase.getInstance(this);
        tvType = findViewById(R.id.tvType);
        tvDate = findViewById(R.id.tvDate);
        tvReDate = findViewById(R.id.tvReDate);
        tvAmount = findViewById(R.id.tvAmount);
        tvDesc = findViewById(R.id.tvDesc);
        tvLoHint = findViewById(R.id.tvLoHint);
        tvToForm = findViewById(R.id.tvToForm);

        rbLoBank = findViewById(R.id.rbLoBank);
        rbLoWallet = findViewById(R.id.rbLoWallet);

        ll_settle_loan = findViewById(R.id.ll_settle_loan);
        btnSettleDown = findViewById(R.id.btnSettleDown);
        edtLoanPaid = findViewById(R.id.edtLoanPaid);
        imgUpdateLoan = findViewById(R.id.imgUpdateLoan);

        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        month = month + 1;

        date = day + "/" + month + "/" + year;

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

    public void toastMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rbLoBank:
                if (loan.getType().equalsIgnoreCase("TAKEN")) {
                    tvLoHint.setText("The paid amount will be Debited from Bank");
                    tvLoHint.setVisibility(View.VISIBLE);
                } else if (loan.getType().equalsIgnoreCase("GIVEN")) {
                    tvLoHint.setText("The paid amount will be Credited to Bank");
                    tvLoHint.setVisibility(View.VISIBLE);
                }
                bankSelected = true;
                walletSelected = false;
                break;

            case R.id.rbLoWallet:
                if (loan.getType().equalsIgnoreCase("TAKEN")) {
                    tvLoHint.setText("The paid amount will be Debited from Wallet");
                    tvLoHint.setVisibility(View.VISIBLE);
                } else if (loan.getType().equalsIgnoreCase("GIVEN")) {
                    tvLoHint.setText("The paid amount will be Credited to Wallet");
                    tvLoHint.setVisibility(View.VISIBLE);
                }
                walletSelected = true;
                bankSelected = false;
                break;

            case R.id.imgUpdateLoan:
                String owes1 = loan.getAmount();
                String paid1 = edtLoanPaid.getText().toString();

                if (paid1.isEmpty()) {
                    return;
                }
                int owesYou1 = Integer.parseInt(owes1);
                int paidYou1 = Integer.parseInt(paid1);

                boolean update = saveLoanTransaction(loan.getType(), paidYou1);
                if (update) {
                    updateLoanDetails();
                }
                break;
        }
    }
}
