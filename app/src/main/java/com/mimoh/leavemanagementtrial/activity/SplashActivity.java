package com.mimoh.leavemanagementtrial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mimoh.leavemanagementtrial.R;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setTitle("Trial Notification!!!");
        builder.setMessage("This is a trial application. All inputs does not make any changes in real world.\nAll data shown is only for testing purposes\n\nThis project is licensed under a Creative Commons Attribution- NonCommercial- NoDerivatives 4.0 International License.\n\nhttp://creativecommons.org/licenses/by-nc-nd/4.0/");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", (dialog, which) -> showDialog());
        builder.create().show();

    }

    private void splashProcess(){
        new CountDownTimer(3000,1000){
            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }.start();
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setTitle("Device Registration Failed");
        builder.setMessage("If you continue you wont be receiving any notifications from Firebase or try reinstalling app.\nIgnore if this is Trial Application");
        builder.setCancelable(false);
        builder.setNegativeButton("Exit", (dialog, which) -> {
            finish();
            finishAffinity();
            System.exit(0);
        });
        builder.setPositiveButton("Continue", (dialog, which) -> splashProcess());
        builder.create().show();
    }
}