package com.webfarmatics.exptrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.webfarmatics.exptrack.adapters.MonthDetailsListAdapter;
import com.webfarmatics.exptrack.bean.BeanStatesByDate;
import com.webfarmatics.exptrack.bean.BeanStatesByDateHelper;
import com.webfarmatics.exptrack.bean.BeanTransactions;
import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.GlobalData;

import java.util.ArrayList;

public class ActivityMonthExpensesDetails extends AppCompatActivity {

    private Context context;
    private LinearLayout llMonthExp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_expences_details);

        context = ActivityMonthExpensesDetails.this;
        llMonthExp = findViewById(R.id.llMonthExp);

        Intent intent = getIntent();

        BeanStatesByDateHelper states = (BeanStatesByDateHelper) intent.getSerializableExtra("states");

        int total = 0;

        for (int k = 0; k < states.getStatesByDatesList().size(); k++) {

            View view2 = LayoutInflater.from(context).inflate(R.layout.states_row, llMonthExp, false);

            TextView tvStates = view2.findViewById(R.id.tvStates);
            TextView tvStatesExp = view2.findViewById(R.id.tvStatesExp);
            tvStates.setText(states.getStatesByDatesList().get(k).getType());
            tvStatesExp.setText("Rs."+states.getStatesByDatesList().get(k).getMoneySpend());

            int temp = Integer.parseInt(states.getStatesByDatesList().get(k).getMoneySpend());
            total = total + temp;

            llMonthExp.addView(view2);

        }


        View view2 = LayoutInflater.from(context).inflate(R.layout.total_states_row, llMonthExp, false);

        TextView tvStates = view2.findViewById(R.id.tvStatesT);
        TextView tvStatesExp = view2.findViewById(R.id.tvStatesExpT);
        tvStates.setText("Total: ");
        tvStatesExp.setText("Rs." + total);

        llMonthExp.addView(view2);


    }
}
