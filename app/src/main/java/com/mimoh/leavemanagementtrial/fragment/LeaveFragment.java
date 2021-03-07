package com.mimoh.leavemanagementtrial.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.mimoh.leavemanagementtrial.Constant;
import com.mimoh.leavemanagementtrial.R;
import com.mimoh.leavemanagementtrial.activity.LeaveActivity;

public class LeaveFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_leave, container, false);

        final Button BTNapplication = view.findViewById(R.id.BTNleaveApplication);
        final Button BTNbalance = view.findViewById(R.id.BTNleaveBalance);
        final Button BTNpending = view.findViewById(R.id.BTNleavePending);
        final Button BTNsanction = view.findViewById(R.id.BTNleaveSanction);

        final Intent intent = new Intent(getContext(), LeaveActivity.class);

        BTNapplication.setOnClickListener(v -> startActivity(intent.putExtra(Constant.TAG, Constant.APPLICATION_TAG)));
        BTNbalance.setOnClickListener(v -> startActivity(intent.putExtra(Constant.TAG, Constant.BALANCE_TAG)));
        BTNpending.setOnClickListener(v -> startActivity(intent.putExtra(Constant.TAG, Constant.PENDING_TAG)));
        BTNsanction.setOnClickListener(v -> startActivity(intent.putExtra(Constant.TAG, Constant.SANCTION_TAG)));

        return  view;
    }
}