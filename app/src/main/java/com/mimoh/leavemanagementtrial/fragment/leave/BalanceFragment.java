package com.mimoh.leavemanagementtrial.fragment.leave;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.mimoh.leavemanagementtrial.Constant;
import com.mimoh.leavemanagementtrial.R;
import com.mimoh.leavemanagementtrial.SecurePreferences;

public class BalanceFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_balance, container, false);

        final EditText ETclLeave = view.findViewById(R.id.ETclLeave);
        final EditText ETaplLeave = view.findViewById(R.id.ETaplLeave);
        final EditText ETcclLeave = view.findViewById(R.id.ETcclLeave);
        final EditText EThaplLeave = view.findViewById(R.id.EThaplLeave);
        final EditText ETrhLeave = view.findViewById(R.id.ETrhLeave);

        SecurePreferences securePreferences = new SecurePreferences(getContext());

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
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
                int a = 10;
                int b = 20;
                int c = 125;
                int d = 50;
                int e = 280;
                String colorA = setcolor(a);
                String colorB = setcolor(b);
                String colorC = setcolor(c);
                String colorD = setcolor(d);
                String colorE = setcolor(e);

                ETclLeave.setTextColor(Color.parseColor(colorA));
                ETrhLeave.setTextColor(Color.parseColor(colorB));
                ETaplLeave.setTextColor(Color.parseColor(colorC));
                EThaplLeave.setTextColor(Color.parseColor(colorD));
                ETcclLeave.setTextColor(Color.parseColor(colorE));

                String clLeave = a + " Days";
                String rhLeave = b + " Days";
                String aplLeave = c + " Days";
                String haplLeave = d + " Days";
                String cclLeave = e + " Days";
                ETclLeave.setText(clLeave);
                ETrhLeave.setText(rhLeave);
                ETaplLeave.setText(aplLeave);
                EThaplLeave.setText(haplLeave);
                ETcclLeave.setText(cclLeave);
            }
        }.start();

        return view;
    }

    private String setcolor(int value){
        String colour1 = "#00BF00";
        String colour2 = "#D9AD00";
        String colour3 = "#F20000";
        if (value > 275)  return colour1;
        else if (value >= 100) return colour2;
        else return  colour3;
    }
}