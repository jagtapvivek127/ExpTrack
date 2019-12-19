package com.webfarmatics.exptrack.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.webfarmatics.exptrack.fragments.LoanGivenFragment;
import com.webfarmatics.exptrack.fragments.LoanTakenFragment;

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public MyPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                LoanGivenFragment newIssue = new LoanGivenFragment();
                return newIssue;
            case 1:
                LoanTakenFragment oldIssue = new LoanTakenFragment();
                return oldIssue;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
