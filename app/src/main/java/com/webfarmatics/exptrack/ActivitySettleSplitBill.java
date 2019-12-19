package com.webfarmatics.exptrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.webfarmatics.exptrack.bean.BeanSplitHelper;
import com.webfarmatics.exptrack.bean.BeanSplitHelperHelper;
import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.CommonUtil;
import com.webfarmatics.exptrack.utils.GlobalData;

import java.util.ArrayList;

public class ActivitySettleSplitBill extends AppCompatActivity {

    private Context context;
    private AppDatabase database;

    private TextView tvSplitTitle, tvOwesYou, tvOwesTotal, tvHeaderDate, tvHeaderMonth, tvHeaderYear;
    private Button btnUpdateSplit;
    private EditText edt1;

    private ArrayList<BeanSplitHelper> splitHelpersList;

    private Spinner spinnerPrt;
    private BeanSplitHelper h2;
    private int grandTotal = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle_split_bill);

        initialize();

        initToolbar();

        MobileAds.initialize(context, GlobalData.ADD_ID);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        Intent intent = getIntent();

        BeanSplitHelperHelper helper = (BeanSplitHelperHelper) intent.getSerializableExtra("helper");

        if (helper != null) {

            tvSplitTitle.setText(helper.getTitle());
            ArrayList<String> dmy = CommonUtil.getDMY(helper.getDate());
            String year = dmy.get(0);
            String month = dmy.get(1);
            String day = dmy.get(2);

            tvHeaderDate.setText(day);
            tvHeaderMonth.setText(month);
            tvHeaderYear.setText(year);

            splitHelpersList = helper.getSplitHelpersList();

        }

        ArrayList<String> itemsList = new ArrayList<>();
        itemsList.add(0, "Select");

        for (int i = 0; i < splitHelpersList.size(); i++) {
            BeanSplitHelper h1 = splitHelpersList.get(i);
            String item = h1.getParticipant();
            String owes = h1.getOwesYou();
            int ow = Integer.parseInt(owes);
            grandTotal = grandTotal + ow;
            itemsList.add(item);
        }

        final String r = getResources().getString(R.string.rupee_sign);
        tvOwesTotal.setText("You Owe " + r + " " + grandTotal);

        if (itemsList.size() > 1) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, itemsList);
            spinnerPrt.setAdapter(adapter);
        }

        spinnerPrt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String part = spinnerPrt.getSelectedItem().toString();
                for (int i = 0; i < splitHelpersList.size(); i++) {
                    BeanSplitHelper h1 = splitHelpersList.get(i);
                    String participant = h1.getParticipant();
                    if (participant.equalsIgnoreCase(part)) {

                        String title = h1.getTitle();
                        String amount = h1.getAmount();
                        String date = h1.getDate();
                        String returnDate = h1.getReturnDate();
                        String comment = h1.getComment();
                        String owesYou = h1.getOwesYou();
                        Log.e("part", " title " + title + " amount " + amount + " date " + date + " returnDate " + returnDate + " comment " + comment + " owesYou " + owesYou + " participant " + participant);

                        tvOwesYou.setText(owesYou);

                        h2 = new BeanSplitHelper(title, amount, date, returnDate, comment, owesYou, participant);

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnUpdateSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String itemSel = spinnerPrt.getSelectedItem().toString();

                if (itemSel.equalsIgnoreCase("Select")) {
                    toastMsg("Selct Participant");
                    return;
                }

                String amtOne = edt1.getText().toString();
                if (amtOne.isEmpty()) {
                    toastMsg("enter amount");
                    edt1.setError("require");
                    return;
                }

                String owes = h2.getOwesYou();
                Log.e("update", " owes " + owes);
                int o = Integer.parseInt(owes);
                int o1 = Integer.parseInt(edt1.getText().toString());
                int ows = o - o1;
                h2.setOwesYou("" + ows);

                String title = h2.getTitle();
                String amount = h2.getAmount();
                String date = h2.getDate();
                String returnDate = h2.getReturnDate();
                String comment = h2.getComment();
                String owesYou = h2.getOwesYou();
                String participant = h2.getParticipant();

                Log.e("update", " title " + title + " amount " + amount + " date " + date + " returnDate " + returnDate + " comment " + comment + " owesYou " + owesYou + " participant " + participant);

                String owes1 = tvOwesYou.getText().toString();
                String paid1 = edt1.getText().toString();
                int owesYou1 = Integer.parseInt(owes1);
                int paidYou1 = Integer.parseInt(paid1);

                if (owesYou1 < paidYou1) {
                    Toast.makeText(context, participant + " owes " + owesYou1 + " is less than " + paidYou1, Toast.LENGTH_SHORT).show();
                } else {
                    boolean update = database.updateSplitBill(title, amount, date, participant, paidYou1);
                    if (update) {
//                        Toast.makeText(context, " Split Bill Updated...", Toast.LENGTH_SHORT).show();
                        spinnerPrt.setSelection(0);
                        tvOwesYou.setText("");
                        int newBal = grandTotal - paidYou1;
                        tvOwesTotal.setText(r + " " + newBal);
                        edt1.setText("");
                        finish();
                    } else {
                        Toast.makeText(context, " Split Update failed...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    private void initialize() {

        context = ActivitySettleSplitBill.this;
        tvSplitTitle = findViewById(R.id.tvUpSplitTitle);
        tvOwesYou = findViewById(R.id.tv1);
        tvOwesTotal = findViewById(R.id.tvOwesTotal);

        tvHeaderDate = (TextView) findViewById(R.id.tvHeaderDate);
        tvHeaderMonth = (TextView) findViewById(R.id.tvHeaderMonth);
        tvHeaderYear = (TextView) findViewById(R.id.tvHeaderYear);

        spinnerPrt = findViewById(R.id.spinnerPrt);
        btnUpdateSplit = findViewById(R.id.btnUpdateSplit);
        edt1 = findViewById(R.id.edt1);
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

    public void toastMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
