package com.mimoh.leavemanagementtrial.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.mimoh.leavemanagementtrial.Constant;
import com.mimoh.leavemanagementtrial.R;
import com.mimoh.leavemanagementtrial.SecurePreferences;

public class LoginActivity extends AppCompatActivity {

    private SecurePreferences securePreferences;
    private View parentLayout;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button BTNlogin = findViewById(R.id.BTNlogin);
        final CheckBox rememberMe = findViewById(R.id.CBremember);
        final EditText ETusername = findViewById(R.id.ETpfno);
        final EditText ETpassword = findViewById(R.id.ETpass);
        final TextView forgotpass = findViewById(R.id.TVforgot);
        final TextInputLayout TIpfno = findViewById(R.id.TIpfno);
        final TextInputLayout TIpass = findViewById(R.id.TIpass);
        rememberMe.setChecked(true);

        securePreferences = new SecurePreferences(this);
        securePreferences.clear();

        final boolean login = getIntent().getBooleanExtra("login",false);

        parentLayout = findViewById(android.R.id.content);
        if(login) {
            Snackbar.make(parentLayout, "Logout Success", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

//        ETusername.setEnabled(false);
//        ETpassword.setEnabled(false);
        ETusername.setText("123456");
        ETpassword.setText("123456");

        forgotpass.setOnClickListener(v1 -> {
            Intent forgotpass_intent = new Intent(LoginActivity.this, PasswordChangeActivity.class);
            forgotpass_intent.putExtra("isLoginActivity", true);
            startActivity(forgotpass_intent);
        });

        ETusername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 8 || s.length() > 8) ETusername.setError("Invalid PF No");
            }
        });

        ETpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 6) ETpassword.setError("Min length 6");
            }
        });


        BTNlogin.setOnClickListener(v -> {
            final String username = ETusername.getText().toString();
            final String password = ETpassword.getText().toString();

            if (SecurePreferences.isNetworkConnected(getApplication())) {
                if (!username.isEmpty() && !password.isEmpty() && password.length() > 5 && username.matches("[0-9]+")
                        && username.length() > 5){
                    if(username.equals("123456") && password.equals("123456")) {
                        TIpass.setError(null);
                        TIpfno.setError(null);

                        progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setMessage(Constant.PLEASE_WAIT);
                        progressDialog.show();
                        progressDialog.setCancelable(false);

                        new CountDownTimer(2000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                            }

                            @Override
                            public void onFinish() {
                                progressDialog.dismiss();
                                trialLogin();
                                startActivity(new Intent(LoginActivity.this, HomepageActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

                            }
                        }.start();
                    }
                    else
                        Snackbar.make(parentLayout, "Incorrect Username & Password Combination", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                } else {
                    TIpfno.setError(null);
                    TIpass.setError(null);
                    if (!username.matches("[0-9]+") || username.length() < 6)
                        TIpfno.setError("Invalid PF No.");
                    if (password.isEmpty())
                        TIpass.setError("Invalid Password");
                    else if (password.length() < 6)
                        TIpass.setError("Min length 6");
                }
            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(Constant.NO_CONNECTION_TITLE);
                builder.setMessage(Constant.NO_CONNECTION_MESSAGE);
                builder.setCancelable(false);
                builder.setPositiveButton(Constant.EXIT, (dialog, which) -> exitApp());
                builder.create().show();
            }


        });
    }

    private void exitApp(){
        finish();
        finishAffinity();
        System.exit(0);
    }

    private void trialLogin(){
        securePreferences.put(Constant.PFNO,"123456");
        securePreferences.put(Constant.NAME, "Test User Name");
        securePreferences.put(Constant.MOBILE, "9876543210");
        securePreferences.put(Constant.LEVEL, "1");
        securePreferences.put(Constant.PANNO, "ABCD1234");
        securePreferences.put(Constant.DESG, "SR.SO.");
        securePreferences.put(Constant.BDATE, "01/01/1980");
        securePreferences.put(Constant.STATION, "Pune");
        securePreferences.put(Constant.DEPT, "Accounts");
        securePreferences.put(Constant.APDATE, "01/01/2005");
        securePreferences.put(Constant.DSEC, "N.A.");
        securePreferences.put(Constant.VIIPAY, "10000");
        securePreferences.put(Constant.VIILVL, "2");
        securePreferences.put(Constant.SEC, "Engineering");
        securePreferences.put(Constant.CATG, "NA");

    }

    @Override
    public void onBackPressed() {
        exitApp();
    }
}