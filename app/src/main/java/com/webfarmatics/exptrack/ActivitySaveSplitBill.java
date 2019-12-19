package com.webfarmatics.exptrack;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.webfarmatics.exptrack.bean.BeanPurchase;
import com.webfarmatics.exptrack.bean.BeanSplit;
import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.GlobalData;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivitySaveSplitBill extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private LinearLayout llAddParti, llAddParticipants, llAddDet, llAddDetails, ll_submit_issue;
    private EditText edtTitel, edtTotalAmount, edtParticipantName, edtSplitComment;
    private TextView tvSplitDate, tvError;
    private LinearLayout llParticipantsList;
    private LinearLayout llWalletPay, llBankPay;
    private AppDatabase database;

    private ArrayList<String> participantsList;
    private int count = 0;

    private int day, month, year, cnt = 0;

    private boolean paidByBank = false, paidByWallet = false;


    private int[] colors = {R.color.o3, R.color.o4, R.color.o5, R.color.o6, R.color.o7, R.color.o8, R.color.o9, R.color.o10, R.color.o11, R.color.o12, R.color.o13, R.color.o14};

    @Override
    protected void onCreate(@Nullable Bundle  savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_save_split_bill);

        initialize();

        initToolbar();

        MobileAds.initialize(context, GlobalData.ADD_ID);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

    }

    private void initialize() {

        context = ActivitySaveSplitBill.this;
        database = AppDatabase.getInstance(this);
        edtTitel = findViewById(R.id.edtSplitTitle);
        edtTotalAmount = findViewById(R.id.edtTotalAmount);
        edtParticipantName = findViewById(R.id.edtParticipantName);
        edtSplitComment = findViewById(R.id.edtSplitComment);
        tvSplitDate = findViewById(R.id.tvSplitDate);
        ImageView imgAddParticipant = findViewById(R.id.imgAddParticipant);
        llParticipantsList = findViewById(R.id.llParticipantsList);
        Button btnSaveSplit = findViewById(R.id.btnSaveSplit);

        llWalletPay = findViewById(R.id.llWalletPay);
        llBankPay = findViewById(R.id.llBankPay);
        tvError = findViewById(R.id.tvError);

        llAddParti = findViewById(R.id.llAddParti);
        llAddParticipants = findViewById(R.id.llAddParticipants);
        llAddDet = findViewById(R.id.llAddDet);
        llAddDetails = findViewById(R.id.llAddDetails);
        ll_submit_issue = findViewById(R.id.ll_submit_issue);

        Calendar calendar = Calendar.getInstance();

        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        showDateFirst(day, month, year);


        participantsList = new ArrayList<>();

        btnSaveSplit.setOnClickListener(this);

        imgAddParticipant.setOnClickListener(this);

        tvSplitDate.setOnClickListener(this);

        llAddParti.setOnClickListener(this);
        llAddDet.setOnClickListener(this);

        llBankPay.setOnClickListener(this);
        llWalletPay.setOnClickListener(this);

        llAddParticipants.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {

        boolean isAddPrtiVis = false;
        boolean isAddDetVis = false;
        switch (v.getId()) {

            case R.id.btnSaveSplit:
                saveTransaction();
                break;

            case R.id.imgAddParticipant:
                if (participantsList.size() > 10) {
                    toastMsg("Maximum 10 participants are allowed");
                    return;
                }
                String p = edtParticipantName.getText().toString();
                if (!p.isEmpty()) {
                    addNewParticipant(p);
                    edtParticipantName.setText("");
//                    toastMsg(p + " added.");
                } else {
                    toastMsg("enter name");
                }
                break;

            case R.id.tvSplitDate:
                showDialog(156);
                break;

            case R.id.llAddParti:
                if (!isAddPrtiVis) {
                    llAddParticipants.setVisibility(View.VISIBLE);
                    llAddDetails.setVisibility(View.GONE);
                    ll_submit_issue.setVisibility(View.GONE);
                } else {
                    llAddParticipants.setVisibility(View.GONE);
                    llAddDetails.setVisibility(View.VISIBLE);
                    ll_submit_issue.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.llAddDet:
                if (!isAddDetVis) {
                    llAddParticipants.setVisibility(View.GONE);
                    llAddDetails.setVisibility(View.VISIBLE);
                    ll_submit_issue.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.llBankPay:
                if (paidByWallet) {
                    paidByBank = true;
                    paidByWallet = false;
                    llBankPay.setBackgroundResource(R.drawable.select_box);
                    llWalletPay.setBackgroundResource(R.drawable.comment_box);
                } else {
                    paidByBank = true;
                    llBankPay.setBackgroundResource(R.drawable.select_box);
                }
                break;

            case R.id.llWalletPay:
                if (paidByBank) {
                    paidByBank = false;
                    paidByWallet = true;
                    llBankPay.setBackgroundResource(R.drawable.comment_box);
                    llWalletPay.setBackgroundResource(R.drawable.select_box);
                } else {
                    paidByWallet = true;
                    llWalletPay.setBackgroundResource(R.drawable.select_box);
                }
                break;

        }
    }

    private int getOwesYouAmount() {

        int owes = -1;
        String amount = edtTotalAmount.getText().toString();
        Log.e("owesYou", "" + amount);
        if (!amount.isEmpty()) {
            int amt = Integer.parseInt(amount);
            int size = participantsList.size() + 1;
            Log.e("owesYou", "" + size);
            owes = amt / size;
        } else {
            toastMsg("Enter correct amount");
        }

        Log.e("owesYou", "" + owes);
        return owes;
    }

    private void addNewParticipant(String p) {

        count++;

        participantsList.add(p);

        TextView textView = new TextView(context);
        textView.setTextColor(getResources().getColor(R.color.o1));
        textView.setText(count + ") " + p);
        textView.setTextSize(20f);
//        textView.setTextColor(colors[count]);
        llParticipantsList.addView(textView);
    }


    private void showDateFirst(int day, int month, int year) {
        month = month + 1;
        Log.e("error", "showDateFirst   day " + day + " month " + month + " year " + year);
        tvSplitDate.setText(day + "/" + month + "/" + year);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(context, myDateListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int y, int m, int d) {
            cnt++;

            Log.e("error", "DatePickerDialog  initial date cnt " + cnt);
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
        tvSplitDate.setText(date);
    }


    public void toastMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    private void saveTransaction() {

        String title = edtTitel.getText().toString();
        String amount = edtTotalAmount.getText().toString();

        if (title.isEmpty()) {
            toastMsg("enter title");
            edtTitel.setError("require");
            return;
        }
        if (amount.isEmpty()) {
            toastMsg("enter amount");
            edtTotalAmount.setError("require");
            return;
        }

        String loanDesc = edtSplitComment.getText().toString();
        if (loanDesc.isEmpty()) {
            toastMsg("enter comment");
            edtSplitComment.setError("require");
            return;
        }

        String date = tvSplitDate.getText().toString();
        String returnDate = "N/A";
        String comment = edtSplitComment.getText().toString();
        int owes = getOwesYouAmount();
        String owesYou = "" + owes;

        for (int i = 0; i < participantsList.size(); i++) {
            String parti = participantsList.get(i);
            Log.e("joker", " title " + title + " amount " + amount + " date " + date + " returnDate " + returnDate + " comment " + comment + " parit " + parti + " owesYou " + owesYou);
        }


        if (!title.isEmpty() && !amount.isEmpty() && !date.isEmpty() && !comment.isEmpty() && participantsList.size() > 0) {
            BeanSplit split = new BeanSplit(title, amount, date, returnDate, comment, owesYou, participantsList);
            boolean saved = database.saveSplitRecord(split);
            if (saved) {
//                toastMsg("Record saved...");
            } else {
                toastMsg("failed to insert...");
            }
        } else {
            toastMsg("fill all details");
        }

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

        BeanPurchase purchase = new BeanPurchase("Split", amt, comment, date, "Split", paymentBy);

        boolean saved = database.saveTransaction(purchase);

        if (saved) {
            toastMsg("Split Bill saved...");
            finish();
        } else {
            toastMsg("Split Bill failed...");
            Log.e("savePurchase", "   savePurchase  " + saved);
        }
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
