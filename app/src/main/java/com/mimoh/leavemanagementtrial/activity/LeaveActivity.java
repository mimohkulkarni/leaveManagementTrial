package com.mimoh.leavemanagementtrial.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.mimoh.leavemanagementtrial.Constant;
import com.mimoh.leavemanagementtrial.R;
import com.mimoh.leavemanagementtrial.SecurePreferences;
import com.mimoh.leavemanagementtrial.fragment.leave.ApplicationFragment;
import com.mimoh.leavemanagementtrial.fragment.leave.BalanceFragment;
import com.mimoh.leavemanagementtrial.fragment.leave.SanctionFragment;
import com.mimoh.leavemanagementtrial.fragment.leave.SelectSanctionFragment;
import com.mimoh.leavemanagementtrial.fragment.leave.StatusFragment;

public class LeaveActivity extends AppCompatActivity {

    private SecurePreferences securePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        String tag = getIntent().getStringExtra(Constant.TAG);

//        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        securePreferences = new SecurePreferences(this);

        if(tag != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (tag) {
                case Constant.APPLICATION_TAG:
                    transaction.add(R.id.fragment_container, new ApplicationFragment(),Constant.APPLICATION_TAG);
                    break;
                case Constant.BALANCE_TAG:
                    transaction.add(R.id.fragment_container, new BalanceFragment(),Constant.BALANCE_TAG);
                    break;
                case Constant.PENDING_TAG:
                    transaction.add(R.id.fragment_container, new StatusFragment(),Constant.PENDING_TAG);
                    break;
                case Constant.SANCTION_TAG:
                    transaction.add(R.id.fragment_container, new SelectSanctionFragment(),Constant.SELECT_SANCTION_TAG);
                    break;
            }
//            transaction.addToBackStack(null);
            transaction.commit();
        }
        else startActivity(new Intent(LeaveActivity.this, HomepageActivity.class));
        
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (securePreferences.getString(Constant.PFNO, Constant.NA).equals(Constant.NA) ||
                securePreferences.getString(Constant.NAME, Constant.NA).equals(Constant.NA) ||
                securePreferences.getString(Constant.MOBILE, Constant.NA).equals(Constant.NA) ||
                securePreferences.getString(Constant.LEVEL, Constant.NA).equals(Constant.NA))
        new HomepageActivity().logout(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SanctionFragment sanctionFragment = (SanctionFragment) getSupportFragmentManager().findFragmentByTag(Constant.SANCTION_TAG);
        if(sanctionFragment != null && sanctionFragment.isVisible()){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new SelectSanctionFragment(),Constant.SELECT_SANCTION_TAG);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else
            startActivity(new Intent(LeaveActivity.this, HomepageActivity.class).putExtra(Constant.TAG,true).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}