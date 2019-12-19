package com.webfarmatics.exptrack.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.webfarmatics.exptrack.ActivityLoanDetails;
import com.webfarmatics.exptrack.R;
import com.webfarmatics.exptrack.adapters.LoanListAdapter;
import com.webfarmatics.exptrack.bean.BeanLoan;
import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.CommonUtil;

import java.util.ArrayList;


public class LoanTakenFragment extends Fragment {


    private Context context;
    private ListView lvLoanTaken;

    ArrayList<BeanLoan> loanArrayList;
    AppDatabase database;
    String todaysDate = null;

    private String TAG = LoanGivenFragment.class.getSimpleName();



    @Override
    public void onCreate(Bundle savedInstanceState) {

        //  setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = container.getContext();
        View v = inflater.inflate(R.layout.lay_loan_fragment, container, false);
        lvLoanTaken = (ListView) v.findViewById(R.id.lvLoanList);

        lvLoanTaken.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BeanLoan loan = loanArrayList.get(position);
                Intent intent = new Intent(context, ActivityLoanDetails.class);
                intent.putExtra("loan", loan);
                startActivity(intent);
            }
        });

        todaysDate = CommonUtil.getTodaysDate();
        Log.e("date","Todays date : "+todaysDate);

        database = new AppDatabase(context);
        loanArrayList = database.getLoanList("TAKEN");
        if (loanArrayList != null) {

            LoanListAdapter adapter = new LoanListAdapter(context, loanArrayList,todaysDate);
            lvLoanTaken.setAdapter(adapter);


        } else {

        }

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("resume", "LoanTaken Resume");

        database = new AppDatabase(context);
        loanArrayList = database.getLoanList("TAKEN");
        if (loanArrayList != null) {

            LoanListAdapter adapter = new LoanListAdapter(context, loanArrayList,todaysDate);
            lvLoanTaken.setAdapter(adapter);


        } else {

        }
    }

}
