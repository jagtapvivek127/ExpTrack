package com.webfarmatics.exptrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.webfarmatics.exptrack.utils.CommonUtil;
import com.webfarmatics.exptrack.utils.GlobalData;

public class ActivitySelectLanguage extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private Button btnEnglish, btnHindi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lang);

        initialize();

        startApp();

        MobileAds.initialize(context, GlobalData.ADD_ID);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);
    }

    private void initialize() {

        context = ActivitySelectLanguage.this;
        btnEnglish = findViewById(R.id.btnEnglish);
        btnHindi = findViewById(R.id.btnHindi);
        Button btnContinue = findViewById(R.id.btnContinue);

        btnEnglish.setOnClickListener(this);
        btnHindi.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEnglish:
                btnEnglish.setBackgroundResource(R.drawable.select_box);
                btnHindi.setBackgroundResource(R.drawable.comment_box);
                CommonUtil.setSharePreferenceString(context, GlobalData.LANGUAGE, GlobalData.ENGLISH_LANGUAGE);
                CommonUtil.setLocaleEnglish(context);
                Toast.makeText(context, "Language set to English", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnHindi:
                btnHindi.setBackgroundResource(R.drawable.select_box);
                btnEnglish.setBackgroundResource(R.drawable.comment_box);
                CommonUtil.setSharePreferenceString(context, GlobalData.LANGUAGE, GlobalData.HINDI_LANGUAGE);
                CommonUtil.setLocaleHindi(context);
                Toast.makeText(context, "Language set to Hindi", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnContinue:
                CommonUtil.setSharePreferenceString(context, GlobalData.LANGUAGE_SELECTED, GlobalData.YES);
                Intent intent = new Intent(ActivitySelectLanguage.this, ActivitySignUp.class);
                startActivity(intent);
                finish();
                break;

        }
    }


    private void startApp() {

        String langSel = CommonUtil.getSharePreferenceString(context, GlobalData.LANGUAGE_SELECTED, "0");
        String language = CommonUtil.getSharePreferenceString(context, GlobalData.LANGUAGE, "0");

        Log.e("tag", "" + langSel + "   " + language);

        if (language.equalsIgnoreCase(GlobalData.HINDI_LANGUAGE)) {
            CommonUtil.setLocaleHindi(context);
        }

        if (language.equalsIgnoreCase(GlobalData.ENGLISH_LANGUAGE)) {
            CommonUtil.setLocaleEnglish(context);
        }


        if (langSel.equalsIgnoreCase("yes")) {

            CommonUtil.setSharePreferenceString(context, GlobalData.LANGUAGE_SELECTED, GlobalData.YES);
            Intent intent = new Intent(ActivitySelectLanguage.this, ActivitySignUp.class);
            startActivity(intent);
            finish();
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
