package com.webfarmatics.exptrack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.CommonUtil;
import com.webfarmatics.exptrack.utils.GlobalData;


public class ActivityAppLogin extends AppCompatActivity implements View.OnClickListener {


    private Context context;
    private Button btnLogin, btnSubmit;
    private EditText edtUsername, edtPassword, edtNickName;
    private TextView tvForgotPassword;
    private CheckBox cbRemberMe;
    private LinearLayout llForgotPass, llLogin, llBestBack;
    private static int count = 0;
    private AppDatabase database;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_login);

        initialize();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSignInDetails();
            }
        });

        String remberMeStatus = CommonUtil.getSharePreferenceString(context, GlobalData.REMEMBER_ME, "0");

        if (remberMeStatus.equalsIgnoreCase(GlobalData.YES_REMBER)) {

            Intent intent = new Intent(ActivityAppLogin.this, ActivityDashboard.class);
            startActivity(intent);
            finish();

        }

        tvForgotPassword.setOnClickListener(this);

        btnSubmit.setOnClickListener(this);

        cbRemberMe.setOnClickListener(this);
    }


    private void initialize() {

        context = ActivityAppLogin.this;

        MobileAds.initialize(context, GlobalData.ADD_ID);

        AdView adView = findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        database = AppDatabase.getInstance(this);

        btnLogin = findViewById(R.id.btnSignIn);
        btnSubmit = findViewById(R.id.btnSubmit);
        edtUsername = findViewById(R.id.edt_usename);
        edtPassword = findViewById(R.id.edt_password);
        edtNickName = findViewById(R.id.edt_nickname_1);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        llForgotPass = findViewById(R.id.llForgotPass);
        llLogin = findViewById(R.id.llLogin);
        llBestBack = findViewById(R.id.llBestBack);

        cbRemberMe = findViewById(R.id.cbRemeberMe);

        String fontPath = "fonts/SAMAN___.TTF";
        Typeface typeface = Typeface.createFromAsset(getAssets(), fontPath);

        llBestBack.setAlpha(0.36f);

    }


    public void toastMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cbRemeberMe:
                boolean isCheck = cbRemberMe.isChecked();
                if (isCheck) {
                    Log.e("isCheck", "" + isCheck);
                    CommonUtil.setSharePreferenceString(context, GlobalData.REMEMBER_ME, GlobalData.YES_REMBER);
                } else {
                    Log.e("isCheck", "" + isCheck);
                    CommonUtil.setSharePreferenceString(context, GlobalData.REMEMBER_ME, GlobalData.NO_REMBER);
                }
                break;

            case R.id.tvForgotPassword:
                llForgotPass.setVisibility(View.VISIBLE);
                llLogin.setVisibility(View.GONE);
                break;

            case R.id.btnSubmit:

                String nickname = edtNickName.getText().toString();

                boolean valid = database.checkNickname(nickname);

                if (valid) {

                    CommonUtil.setSharePreferenceString(context, GlobalData.APP_SIGNUP_STATUS, "0");

                    Intent intent = new Intent(ActivityAppLogin.this, ActivitySignUp.class);
                    intent.putExtra("nickname", nickname);
                    startActivity(intent);
                    finish();
                }

                break;

        }
    }


    private void checkSignInDetails() {

        String name = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        boolean valid = database.checkLogin(name, password);

        if (valid) {

            Intent intent = new Intent(context, ActivityDashboard.class);
            startActivity(intent);
            finish();

        } else {
            toastMsg("Wrong details...");
        }

    }

}
