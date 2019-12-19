package com.webfarmatics.exptrack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.webfarmatics.exptrack.utils.AppDatabase;
import com.webfarmatics.exptrack.utils.CommonUtil;
import com.webfarmatics.exptrack.utils.GlobalData;


public class ActivitySignUp extends AppCompatActivity {

    private Context context;
    private Button btnSignUp;
    private EditText edtUsername, edtPassword, edtNickname;
    private AppDatabase database;
    private String nickname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initialize();

        initToolbar();

        nickname = getIntent().getStringExtra("nickname");

        String firstCom = CommonUtil.getSharePreferenceString(context, GlobalData.FIRST_TIME, "0");

        if (firstCom.equalsIgnoreCase(GlobalData.FIRST_COMPLETE)) {
            edtNickname.setVisibility(View.GONE);
        }

        String signUpStatus = CommonUtil.getSharePreferenceString(context, GlobalData.APP_SIGNUP_STATUS, "0");

        if (signUpStatus.equalsIgnoreCase(GlobalData.SIGNUP_COMPLETE)) {
            Intent intent = new Intent(ActivitySignUp.this, ActivityAppLogin.class);
            startActivity(intent);
            finish();
        }

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String firstCom = CommonUtil.getSharePreferenceString(context, GlobalData.FIRST_TIME, "0");

                if (firstCom.equalsIgnoreCase(GlobalData.FIRST_COMPLETE)) {

                    String userName = edtUsername.getText().toString().trim();
                    String password = edtPassword.getText().toString().trim();

                    boolean saved1 = database.updateLoginDetails(userName, password, nickname);

                    if (saved1) {

                        Intent intent = new Intent(ActivitySignUp.this, ActivityAppLogin.class);
                        startActivity(intent);
                        finish();

                    }


                } else {

                    String userName1 = edtUsername.getText().toString().trim();
                    String password1 = edtPassword.getText().toString().trim();
                    String nickname1 = edtNickname.getText().toString().trim();

                    boolean saved = database.saveLoginDetails(userName1, password1, nickname1);

                    if (saved) {

                        CommonUtil.setSharePreferenceString(context, GlobalData.FIRST_TIME, GlobalData.FIRST_COMPLETE);
                        CommonUtil.setSharePreferenceString(context, GlobalData.APP_SIGNUP_STATUS, "" + GlobalData.SIGNUP_COMPLETE);

                        Intent intent = new Intent(ActivitySignUp.this, ActivityAppLogin.class);
                        startActivity(intent);
                        finish();

                    }

                }

            }
        });
    }

    private void initialize() {

        context = ActivitySignUp.this;

        btnSignUp = findViewById(R.id.btnSignIn);
        edtUsername = findViewById(R.id.edt_usename);
        edtPassword = findViewById(R.id.edt_password);
        edtNickname = findViewById(R.id.edt_pet_name);
        btnSignUp = findViewById(R.id.btnSignUp);

        database = AppDatabase.getInstance(this);

        LinearLayout llBestBack = findViewById(R.id.llBestBack);
        llBestBack.setAlpha(0.36f);


        String fontPath = "fonts/SAMAN___.TTF";
        Typeface typeface = Typeface.createFromAsset(getAssets(), fontPath);

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
