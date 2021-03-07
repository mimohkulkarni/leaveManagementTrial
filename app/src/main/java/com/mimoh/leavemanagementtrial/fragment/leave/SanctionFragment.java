package com.mimoh.leavemanagementtrial.fragment.leave;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;
import com.mimoh.leavemanagementtrial.Constant;
import com.mimoh.leavemanagementtrial.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class SanctionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sanction, container, false);

        final EditText ETpfno = view.findViewById(R.id.ETpfno);
        final EditText ETstation = view.findViewById(R.id.ETstation);
        final EditText ETmobile = view.findViewById(R.id.ETmobile);
        final EditText ETdesg = view.findViewById(R.id.ETdesg);
        final EditText ETdept = view.findViewById(R.id.ETdept);
        final EditText ETname = view.findViewById(R.id.ETname);
        final EditText ETnature = view.findViewById(R.id.ETleaveNature);
        final EditText ETleaveFrom = view.findViewById(R.id.ETleaveFrom);
        final EditText ETleaveTo = view.findViewById(R.id.ETleaveTo);
        final EditText ETappDate = view.findViewById(R.id.ETappdate);
        final EditText ETpurpose = view.findViewById(R.id.ETpurpose);
        final EditText ETaddress = view.findViewById(R.id.ETaddress);
        final EditText ETheadPermission = view.findViewById(R.id.ETheadPermission);
        final EditText ETheadFrom = view.findViewById(R.id.ETheadLeaveFrom);
        final EditText ETheadTo = view.findViewById(R.id.ETheadLeaveTo);
        final EditText ETforwardBy = view.findViewById(R.id.ETforwardBy);
        final AutoCompleteTextView menuRemark = view.findViewById(R.id.menuRemark);
        final AutoCompleteTextView menuForward = view.findViewById(R.id.menuForward);
        final EditText ETreason = view.findViewById(R.id.ETreason);
        final TextView TVdays = view.findViewById(R.id.TVdays);
        final Button BTNsubmit = view.findViewById(R.id.BTNsubmit);
        final LinearLayout LLforward = view.findViewById(R.id.LLforward);
        final TextInputLayout TIremark = view.findViewById(R.id.TIremark);

        final String[] forwardPfno = new String[1];
        forwardPfno[0] = Constant.VAL_0;

        final ArrayAdapter<String> remarkAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.remarks));
        menuRemark.setAdapter(remarkAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            menuRemark.setText(remarkAdapter.getItem(0),false);
        }

        LLforward.setVisibility(View.GONE);
        TIremark.setVisibility(View.GONE);

        menuRemark.setOnItemClickListener((parent, view13, position, id) -> {
            if (position == 2){
                TIremark.setVisibility(View.VISIBLE);
                LLforward.setVisibility(View.GONE);
            }
            else if(position == 3){
                LLforward.setVisibility(View.VISIBLE);
                TIremark.setVisibility(View.GONE);
            }
            else{
                TIremark.setVisibility(View.GONE);
                LLforward.setVisibility(View.GONE);
            }
        });

        ArrayList<String> forwardNameArrayList = new ArrayList<>();
        ArrayList<String> forwardPfnoArrayList = new ArrayList<>();
        forwardNameArrayList.clear();
        forwardPfnoArrayList.clear();
        forwardNameArrayList.add(Constant.SELECT);
        forwardNameArrayList.add("Test User 2");
        forwardNameArrayList.add("Test User 3");
        forwardNameArrayList.add("Test User 4");
        forwardPfnoArrayList.add(Constant.VAL_0);
        forwardPfnoArrayList.add("1");
        forwardPfnoArrayList.add("2");
        forwardPfnoArrayList.add("3");

        final ArrayAdapter<String> forwardAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, forwardNameArrayList);
        menuForward.setAdapter(forwardAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            menuForward.setText(forwardAdapter.getItem(0),false);
        }
        forwardAdapter.notifyDataSetChanged();

        menuForward.setOnItemClickListener((parent, view13, position, id) -> forwardPfno[0] = forwardPfnoArrayList.get(position));

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(Constant.PLEASE_WAIT);
        progressDialog.show();
        progressDialog.setCancelable(false);

        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("pfno", "112233");
            jsonObject1.put("name", "Test User 1");
            jsonObject1.put("desg", "SR.S.O.");
            jsonObject1.put("dept", "Accounts");
            jsonObject1.put("station", "Pune");
            jsonObject1.put("appdate", "01/01/2021");
            jsonObject1.put("ltype", "C L");
            jsonObject1.put("lfrom", "01/01/2021");
            jsonObject1.put("lto", "02/01/2021");
            jsonObject1.put("hqperfrm", "01/01/2021");
            jsonObject1.put("hqperto", "02/01/2021");
            jsonObject1.put("nday", "1.5");
            jsonObject1.put("hqperm", "Yes");
            jsonObject1.put("purpose", "Test Purpose");
            jsonObject1.put("address", "Test Address");
            jsonObject1.put("mobile", "9876543210");
            jsonObject1.put("sanremark", "forward");
            jsonObject1.put("forward", "112233");
            jsonObject1.put("fname", "Test User 5");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
                try {
                    ETname.setText(jsonObject1.getString(Constant.NAME));
                    ETpfno.setText(jsonObject1.getString(Constant.PFNO));
                    ETdesg.setText(jsonObject1.getString(Constant.DESG));
                    ETstation.setText(jsonObject1.getString(Constant.STATION));
                    ETmobile.setText(jsonObject1.getString(Constant.MOBILE));
                    ETdept.setText(jsonObject1.getString(Constant.DEPT));
                    ETnature.setText(jsonObject1.getString("ltype"));
                    ETleaveFrom.setText(jsonObject1.getString("lfrom"));
                    ETleaveTo.setText(jsonObject1.getString("lto"));
                    final String days = Constant.noOfDays + jsonObject1.getString("nday");
                    TVdays.setText(days);
                    ETpurpose.setText(jsonObject1.getString("purpose"));
                    ETaddress.setText(jsonObject1.getString("address"));
                    ETheadPermission.setText(jsonObject1.getString("hqperm"));
                    ETforwardBy.setText(jsonObject1.getString("fname"));
                    ETappDate.setText(jsonObject1.getString("appdate"));

                    if (jsonObject1.getString("hqperm").equals("No")) {
                        ETheadFrom.setText(Constant.NA);
                        ETheadTo.setText(Constant.NA);
                    } else {
                        ETheadFrom.setText(jsonObject1.getString("hqperfrm"));
                        ETheadTo.setText(jsonObject1.getString("hqperto"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        BTNsubmit.setOnClickListener(v -> {

            final String remark = menuRemark.getText().toString();
            String forwardTo = null;
            if (remark.equals("Forward")) {
                forwardTo = menuForward.getText().toString();
            }
            else forwardPfno[0] = Constant.VAL_0;
            final String reason = ETreason.getText().toString();
            if (forwardPfno[0].isEmpty()) forwardPfno[0] = "";
            if (!remark.equals(Constant.SELECT)) {
                if (remark.equals("Sanctioned") || (remark.equals("Not Sanctioned") && !reason.isEmpty())
                        || (remark.equals("Forward") && !forwardTo.equals("Select") && !forwardTo.isEmpty() && !forwardPfno[0].equals("0"))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                    builder.setTitle(Constant.Confirm_msg);
                    builder.setMessage("Perform this action?");
                    builder.setPositiveButton("Yes", (dialog, which) -> {
                        progressDialog.show();
                        new CountDownTimer(2000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) { }

                            @Override
                            public void onFinish() {
                                progressDialog.dismiss();
                                SelectSanctionFragment select_sanction_fragment = new SelectSanctionFragment();
                                FragmentTransaction transaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
                                transaction.replace(R.id.fragment_container, select_sanction_fragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                Toast.makeText(getContext(), "Action taken successfully.", Toast.LENGTH_LONG).show();
                            }
                        }.start();
                    });
                    builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                    builder.create().show();
                } else {
                    Toast.makeText(getContext(), "Please don't keep fields empty", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Select action before giving remark", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}