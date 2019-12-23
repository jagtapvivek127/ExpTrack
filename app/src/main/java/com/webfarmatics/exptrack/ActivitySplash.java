package com.webfarmatics.exptrack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;


import com.webfarmatics.exptrack.bean.BeanThought;
import com.webfarmatics.exptrack.utils.AppDatabase;

import java.util.Random;

public class ActivitySplash extends AppCompatActivity {

    private AppDatabase database;
    private TextView tvThought,tvAuther;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show the splash screen
        setContentView(R.layout.activity_splash);

        initialize();

        Random rand = new Random();

        int n = rand.nextInt(22) + 1;

        BeanThought thought = database.getTodaysThought(n);

        if(thought!=null){

            String fontPath = "fonts/vinque.ttf";
            Typeface typeface = Typeface.createFromAsset(getAssets(),fontPath);
            tvThought.setTypeface(typeface);
            tvAuther.setTypeface(typeface);
            tvThought.setText(thought.getThought());
            tvAuther.setText("-"+thought.getAuther());

        }

        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                doWork();
                startApp();
                finish();
            }
        }).start();
    }

    private void initialize() {
        database = AppDatabase.getInstance(this);
        tvThought = findViewById(R.id.tvThought);
        tvAuther = findViewById(R.id.tvAuther);
    }

    private void doWork() {
        for (int progress = 0; progress < 100; progress += 40) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startApp() {
        Intent intent = new Intent(ActivitySplash.this, ActivitySelectLanguage.class);
        startActivity(intent);
    }
}
