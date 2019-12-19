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


public class LoanGivenFragment extends Fragment {


    private Context context;
    private ListView lvLoanGiven;
    private AppDatabase database;

    ArrayList<BeanLoan> loanArrayList;

    private String TAG = LoanGivenFragment.class.getSimpleName();
    String todaysDate = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //  setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = container.getContext();
        View v = inflater.inflate(R.layout.lay_loan_fragment, container, false);
        lvLoanGiven = (ListView) v.findViewById(R.id.lvLoanList);

        lvLoanGiven.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BeanLoan loan = loanArrayList.get(position);
                Intent intent = new Intent(context, ActivityLoanDetails.class);
                intent.putExtra("loan", loan);
                startActivity(intent);
            }
        });

        todaysDate = CommonUtil.getTodaysDate();
        Log.e("date", "Todays date : " + todaysDate);

        database = new AppDatabase(context);
        loanArrayList = database.getLoanList("GIVEN");
        if (loanArrayList != null) {

            LoanListAdapter adapter = new LoanListAdapter(context, loanArrayList, todaysDate);
            lvLoanGiven.setAdapter(adapter);


        } else {

        }

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("resume", "LoanGiven Resume");

        database = new AppDatabase(context);
        loanArrayList = database.getLoanList("GIVEN");
        if (loanArrayList != null) {

            LoanListAdapter adapter = new LoanListAdapter(context, loanArrayList, todaysDate);
            lvLoanGiven.setAdapter(adapter);


        } else {

        }

    }
}
