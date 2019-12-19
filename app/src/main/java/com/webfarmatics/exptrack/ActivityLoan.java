package com.webfarmatics.exptrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.webfarmatics.exptrack.adapters.MyPagerAdapter;
import com.webfarmatics.exptrack.animation.ViewAnimation;

public class ActivityLoan extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loan);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        initialize();

        initToolbar();

        initializeFab();
    }

    private void initialize() {

        context = ActivityLoan.this;

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.given));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.taken));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
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


    private boolean rotate = false;
    private View lytAddLoan;
    private View lytClearLoan;


    private void initializeFab() {

        final FloatingActionButton fabClearLoan = findViewById(R.id.fabClearLoan);
        final FloatingActionButton fabAddLoan = findViewById(R.id.fabAddLoan);
        final FloatingActionButton fab_add = findViewById(R.id.fab_add);
        lytAddLoan = findViewById(R.id.lytAddLoan);
        lytClearLoan = findViewById(R.id.lytClearLoan);
        ViewAnimation.initShowOut(lytAddLoan);
        ViewAnimation.initShowOut(lytClearLoan);


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFabMode(v);
            }
        });

        fabClearLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentB = new Intent(ActivityLoan.this, ActivityClearLoanDetails.class);
                startActivity(intentB);
            }
        });

        fabAddLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentB = new Intent(ActivityLoan.this, ActivitySaveLoanDetails.class);
                startActivity(intentB);
            }
        });

    }


    private void toggleFabMode(View v) {
        rotate = ViewAnimation.rotateFab(v, !rotate);
        if (rotate) {
            ViewAnimation.showIn(lytAddLoan);
            ViewAnimation.showIn(lytClearLoan);
        } else {
            ViewAnimation.showOut(lytAddLoan);
            ViewAnimation.showOut(lytClearLoan);
        }
    }


}
