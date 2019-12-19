package com.webfarmatics.exptrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.webfarmatics.exptrack.utils.CommonUtil;
import com.webfarmatics.exptrack.utils.GlobalData;


public class ActivityCopyRights extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy_rights);

        context = ActivityCopyRights.this;
        Button btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonUtil.setSharePreferenceString(context, GlobalData.READ_COPYRIGHTS, GlobalData.YES_READ);
                Intent intent = new Intent(ActivityCopyRights.this, ActivityAppLogin.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
