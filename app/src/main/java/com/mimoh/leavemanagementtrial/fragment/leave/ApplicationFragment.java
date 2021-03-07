package com.mimoh.leavemanagementtrial.fragment.leave;

import android.app.ProgressDialog;
import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.mimoh.leavemanagementtrial.Constant;
import com.mimoh.leavemanagementtrial.R;
import com.mimoh.leavemanagementtrial.SecurePreferences;
import com.mimoh.leavemanagementtrial.activity.HomepageActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ApplicationFragment extends Fragment {

    private static final String DATE_PICKER = "DATE_PICKER";
    private TextView TVdays;
    private String days;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_application, container, false);

        final Button leaveDatePickerBtn = view.findViewById(R.id.leaveDatePickerBtn);
        final Button headDatePickerBtn = view.findViewById(R.id.headDatePickerBtn);
        final Button BTNsubmit = view.findViewById(R.id.BTNsubmit);
        final Button BTNreset = view.findViewById(R.id.BTNreset);
        final EditText ETpfno = view.findViewById(R.id.ETpfno);
        final EditText ETsection = view.findViewById(R.id.ETsection);
        final EditText ETdesg = view.findViewById(R.id.ETdesg);
        final EditText ETdept = view.findViewById(R.id.ETdept);
        final EditText ETname = view.findViewById(R.id.ETname);
        final EditText ETpurpose = view.findViewById(R.id.ETpurpose);
        final EditText ETaddress = view.findViewById(R.id.ETaddress);
        final EditText ETleaveFrom = view.findViewById(R.id.ETleaveFrom);
        final EditText ETleaveTo = view.findViewById(R.id.ETleaveTo);
        final EditText ETheadFrom = view.findViewById(R.id.ETheadLeaveFrom);
        final EditText ETheadTo = view.findViewById(R.id.ETheadLeaveTo);
        TVdays = view.findViewById(R.id.TVdays);
        final LinearLayout LLhead = view.findViewById(R.id.LLhead);
        final AutoCompleteTextView menuLeave = view.findViewById(R.id.menuLeave);
        final AutoCompleteTextView menuHead = view.findViewById(R.id.menuHead);
        final AutoCompleteTextView menuForward = view.findViewById(R.id.menuForward);

        final String[] forwardPfno = new String[1];

        SecurePreferences securePreferences = new SecurePreferences(getContext());

        ETpfno.setText(securePreferences.getString(Constant.PFNO,Constant.NA));
        ETsection.setText(securePreferences.getString(Constant.SEC,Constant.NA));
        ETdesg.setText(securePreferences.getString(Constant.DESG,Constant.NA));
        ETdept.setText(securePreferences.getString(Constant.DEPT,Constant.NA));
        ETname.setText(securePreferences.getString(Constant.NAME,Constant.NA));

        final ArrayAdapter<String> natureAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.natureArray));
        menuLeave.setAdapter(natureAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            menuLeave.setText(natureAdapter.getItem(0),false);
        }

        final ArrayAdapter<String> headAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.headPermissionArray));
        menuHead.setAdapter(headAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            menuHead.setText(headAdapter.getItem(0),false);
        }

        LLhead.setVisibility(View.GONE);

        menuHead.setOnItemClickListener((parent, view1, position, id) -> {
            ETheadFrom.setText(null);
            ETheadTo.setText(null);
            if (position == 0) LLhead.setVisibility(View.GONE);
            else if(position == 1) LLhead.setVisibility(View.VISIBLE);
            else LLhead.setVisibility(View.GONE);
        });

        ArrayList<String> forwardNameArrayList = new ArrayList<>();
        ArrayList<String> forwardPfnoArrayList = new ArrayList<>();
        forwardNameArrayList.clear();
        forwardPfnoArrayList.clear();
        forwardNameArrayList.add(Constant.SELECT);
        forwardNameArrayList.add("Test User 1");
        forwardNameArrayList.add("Test User 2");
        forwardNameArrayList.add("Test User 3");
        forwardPfnoArrayList.add(Constant.VAL_0);
        forwardPfnoArrayList.add("1");
        forwardPfnoArrayList.add("2");
        forwardPfnoArrayList.add("3");

        ArrayAdapter<String> forwardAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, forwardNameArrayList);
        menuForward.setAdapter(forwardAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            menuForward.setText(forwardAdapter.getItem(0),false);
        }
        forwardAdapter.notifyDataSetChanged();

        menuForward.setOnItemClickListener((parent, view13, position, id) -> forwardPfno[0] = forwardPfnoArrayList.get(position));

        CalendarConstraints.Builder caBuilder = new CalendarConstraints.Builder();
        caBuilder.setValidator(DateValidatorPointForward.now());

        final MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText(R.string.select_dates);
        builder.setCalendarConstraints(caBuilder.build());
        final MaterialDatePicker<Pair<Long, Long>> leaveDatePicker = builder.build();
        final MaterialDatePicker<Pair<Long, Long>> headDatePicker = builder.build();

        leaveDatePickerBtn.setOnClickListener(v -> leaveDatePicker.show(getChildFragmentManager(), DATE_PICKER));

        menuLeave.setOnItemClickListener((parent, view12, position, id) -> {
            TVdays.setText(null);
            ETleaveFrom.setText(null);
            ETleaveTo.setText(null);
        });

        leaveDatePicker.addOnPositiveButtonClickListener(selection -> {
            if (selection.first != null && selection.second != null && !menuLeave.getText().toString().isEmpty() &&
                !menuLeave.getText().toString().equals(getResources().getStringArray(R.array.natureArray)[0])) {
//                Log.e("mimoh", formatDate(selection.first));
//                Log.e("mimoh", formatDate(selection.second));
                ETleaveFrom.setText(formatDate(selection.first));
                ETleaveTo.setText(formatDate(selection.second));
                diffdates(selection.first,selection.second, menuLeave.getText().toString().equals(getResources().getStringArray(R.array.natureArray)[1]));
            }
            else if (menuLeave.getText().toString().isEmpty() || menuLeave.getText().toString().equals(getResources().getStringArray(R.array.natureArray)[0]))
                Toast.makeText(getContext(),"Please select Leave Nature",Toast.LENGTH_LONG).show();
            else Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
        });

        headDatePickerBtn.setOnClickListener(v -> headDatePicker.show(getChildFragmentManager(), DATE_PICKER));

        headDatePicker.addOnPositiveButtonClickListener(selection -> {
            if (selection.first != null && selection.second != null) {
                ETheadFrom.setText(formatDate(selection.first));
                ETheadTo.setText(formatDate(selection.second));
            }
        });

        BTNreset.setOnClickListener(v -> {
            ETleaveFrom.setText(null);
            ETleaveTo.setText(null);
            ETheadFrom.setText(null);
            ETheadTo.setText(null);
            TVdays.setText(null);
            ETpurpose.setText(null);
            ETaddress.setText(null);
            LLhead.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                menuLeave.setText(getResources().getStringArray(R.array.natureArray)[0],false);
                menuHead.setText(getResources().getStringArray(R.array.headPermissionArray)[0],false);
                menuForward.setText(forwardAdapter.getItem(0),false);
            }
        });

        BTNsubmit.setOnClickListener(v -> {
            final String leaveNature = menuLeave.getText().toString();
            final String leaveFrom = ETleaveFrom.getText().toString();
            final String leaveTo = ETleaveTo.getText().toString();
            final String purpose = ETpurpose.getText().toString();
            final String address = ETaddress.getText().toString();
            final String headPermission = menuHead.getText().toString();
            final String headPermissionFrom = ETheadFrom.getText().toString();
            final String headPermissionTo = ETheadTo.getText().toString();

            if (!securePreferences.getString(Constant.NAME,Constant.NA).equals(Constant.NA) && !securePreferences.getString(Constant.PFNO,Constant.NA).equals(Constant.NA)
                    && !leaveNature.isEmpty() && !leaveNature.equals(Constant.SELECT) && !leaveFrom.isEmpty()&& !leaveTo.isEmpty() && !days.isEmpty()
                    && !purpose.isEmpty() && !address.isEmpty() && forwardPfno[0] != null && !forwardPfno[0].equals("0") && !headPermission.isEmpty()
                    && ((headPermission.equals("Yes") && !headPermissionFrom.isEmpty() && !headPermissionTo.isEmpty())
                        || headPermission.equals("No"))){

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
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                        builder1.setTitle("Leave Submitted");
                        builder1.setMessage("We will notify you after your leave is sanctioned.");
                        builder1.setCancelable(false);
                        builder1.setPositiveButton("OK", (dialog, which) -> startActivity(new Intent(getContext(), HomepageActivity.class).putExtra(Constant.TAG,true)));
                        builder1.create().show();
                    }
                }.start();
            }
            else {
                ETleaveFrom.setError(null);
                ETleaveTo.setError(null);
                menuLeave.setError(null);
                ETpurpose.setError(null);
                ETaddress.setError(null);
                menuForward.setError(null);
                menuHead.setError(null);
                ETheadFrom.setError(null);
                ETheadTo.setError(null);

                if (leaveFrom.isEmpty()) ETleaveFrom.setError(Constant.NOT_EMPTY);
                if (leaveTo.isEmpty()) ETleaveTo.setError(Constant.NOT_EMPTY);
                if (leaveNature.isEmpty() || leaveNature.equals(Constant.SELECT)) menuLeave.setError(Constant.REQUIRED);
                if (purpose.isEmpty()) ETpurpose.setError(Constant.NOT_EMPTY);
                if (address.isEmpty()) ETaddress.setError(Constant.NOT_EMPTY);
                if (forwardPfno[0] == null || forwardPfno[0].equals(Constant.SELECT)) menuForward.setError(Constant.REQUIRED);
                if (headPermission.isEmpty()) menuHead.setError(Constant.NOT_EMPTY);
                if (headPermission.equals("Yes")){
                    if (headPermissionFrom.isEmpty()) ETheadFrom.setError(Constant.NOT_EMPTY);
                    if (headPermissionTo.isEmpty()) ETheadTo.setError(Constant.NOT_EMPTY);
                    ETaddress.setError(Constant.NOT_EMPTY);
                }
            }
        });

        return view;
    }

    private String formatDate(long dateLong){
        return new SimpleDateFormat(Constant.myFormat, Locale.US).format(dateLong);
    }

    private void diffdates(long dateLong1,long dateLong2,boolean isCL){
        Date date1 = new Date(dateLong1);
        Date date2 = new Date(dateLong2);

        final double[] diffInDays = {(double) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24))};
        if (formatDate(Calendar.getInstance().getTimeInMillis()).equals(formatDate(dateLong1)) || (date1 == date2)){
            if (isCL) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                builder.setTitle(Constant.Confirm_msg);
                builder.setMessage(Constant.HalfFull);
                builder.setCancelable(false);
                builder.setPositiveButton(Constant.HalfDay, (dialog, which) -> setTextDays(diffInDays[0] - 0.5));
                builder.setNegativeButton(Constant.FullDay, (dialog, which) -> {
                    if(!formatDate(Calendar.getInstance().getTimeInMillis()).equals(formatDate(dateLong1))) diffInDays[0] += 1.0;
                    setTextDays(diffInDays[0]);
                });
                builder.create().show();
            }
            else setTextDays(diffInDays[0]);
        }
        else setTextDays(diffInDays[0]);
    }

    private void setTextDays(double daysDouble){
        days = String.valueOf(daysDouble);
        final String message = Constant.noOfDays + days;
        TVdays.setText(message);
    }
}