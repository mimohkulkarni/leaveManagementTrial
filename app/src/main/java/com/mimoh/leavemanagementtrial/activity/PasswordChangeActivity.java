package com.mimoh.leavemanagementtrial.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mimoh.leavemanagementtrial.Constant;
import com.mimoh.leavemanagementtrial.R;
import com.mimoh.leavemanagementtrial.SecurePreferences;
import com.mimoh.leavemanagementtrial.firebase.NotificationHelper;

import java.util.concurrent.TimeUnit;

public class PasswordChangeActivity extends AppCompatActivity {

    private EditText ETotp;
    private TextView TVtimer;
    private boolean flagSendOTPAgain;
    private String pfno,pass,pass1;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        final boolean login = getIntent().getBooleanExtra("isLoginActivity",false);

        final SecurePreferences securePreferences = new SecurePreferences(this);

        final EditText ETnewPass1 = findViewById(R.id.ETnewpassfirst);
        final EditText ETnewPass2 = findViewById(R.id.ETnewpasssecond);
        final EditText EToldPass = findViewById(R.id.EToldpass);
        final EditText ETpfno = findViewById(R.id.ETpfno);
        final TextView TVforgotpass = findViewById(R.id.TVforgotpass);
        final LinearLayout LLoldPass = findViewById(R.id.LLoldPass);
        final Button BTNsubmit = findViewById(R.id.BTNsubmit);

        ETpfno.setText("123456");
        ETnewPass1.setText("123456");
        ETnewPass2.setText("123456");

        final LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View view1 = layoutInflater.inflate(R.layout.otp_dialog, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view1);
        alertDialog = builder.create();

        ETotp = view1.findViewById(R.id.ETotp);
        TVtimer = view1.findViewById(R.id.TVtimer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ETotp.setLetterSpacing(0.7f);
        }
        final Button BTNverify = view1.findViewById(R.id.BTNverify);

        if (login){
            LLoldPass.setVisibility(View.GONE);
            EToldPass.setEnabled(false);
        }
        else{
            ETpfno.setEnabled(false);
            ETpfno.setText(securePreferences.getString(Constant.PFNO, Constant.NA));
        }

        checkAndRequestPermissions();

        final String OTP_CHECK = "123456";


        TVforgotpass.setOnClickListener(v -> {
            EToldPass.setEnabled(false);
            TVforgotpass.setVisibility(View.GONE);
        });

        BTNsubmit.setOnClickListener(v -> {
            pfno = ETpfno.getText().toString();
            pass = ETnewPass1.getText().toString();
            pass1 = ETnewPass2.getText().toString();
            final String oldpass = EToldPass.getText().toString();

            if (!pfno.isEmpty() && pass.length() > 5  && pass.equals(pass1)){
                final String otpMessage = "Your OTP for resetting password is 123456. Do not disclose it to others.";
                sendOTP(otpMessage);
                alertDialog.show();
                ETotp.setText("123456");
                OTPTimer();

                TVtimer.setOnClickListener(v1 -> {
                    if(flagSendOTPAgain){
                        sendOTP(otpMessage);
                        OTPTimer();
                    }
                });

                BTNverify.setOnClickListener(v2 -> {
                    final String otp = ETotp.getText().toString();
                    Toast.makeText(PasswordChangeActivity.this,otp,Toast.LENGTH_LONG).show();
                    if (otp.length() == 6 && otp.matches(".*\\d.*") && otp.equals(OTP_CHECK)){
                        if (!login && !oldpass.equals("123456")){
                            EToldPass.setError("Incorrect Password");
                            return;
                        }
                        EToldPass.setError(null);
                        ETotp.setError(null);
                        alertDialog.dismiss();
                        ETpfno.setEnabled(false);
                        ETnewPass1.setEnabled(false);
                        ETnewPass2.setEnabled(false);
                        EToldPass.setEnabled(false);
                        ETotp.setEnabled(false);
                        final String successMessage =  "your password has been successfully changed.If you didn't do this contact you admin.";
                        sendOTP(successMessage);
                        alertDialog.dismiss();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                        builder1.setTitle("Password Changed");
                        builder1.setMessage("Your password has been successfully changed.\nPlease login for security reasons.\n Password will not change as it is trial application");
                        builder1.setPositiveButton("OK", (dialog, which) -> {
                            securePreferences.clear();
                            startActivity(new Intent(this,LoginActivity.class));
                        });
                        builder1.setCancelable(false);
                        builder1.create().show();
                    }
                    else ETotp.setError("Incorrect OTP");
                });
            }
            else {
                ETpfno.setError(null);
                ETnewPass1.setError(null);
                ETnewPass2.setError(null);
                if (pfno.isEmpty()) ETpfno.setError("Enter PF No");
                if (pass.isEmpty()) ETnewPass1.setError("Cannot be empty");
                if (pass1.isEmpty() || !pass.equals(pass1)) ETnewPass2.setError("Password don't match");
            }
        });

    }

    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.RECEIVE_SMS
            },100);
        }
    }

    private void sendOTP(String message){
        flagSendOTPAgain = false;
        NotificationHelper.displayNotification(getApplicationContext(),"Password",message);
        Toast.makeText(this, "OTP sent", Toast.LENGTH_LONG).show();
    }

    private void OTPTimer(){
        flagSendOTPAgain = false;
        new CountDownTimer(60000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                final String time = "Resend in " + TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + " Seconds";
                TVtimer.setText(time);
            }

            @Override
            public void onFinish() {
                flagSendOTPAgain = true;
                TVtimer.setText(R.string.send_again);
            }
        }.start();
    }

}