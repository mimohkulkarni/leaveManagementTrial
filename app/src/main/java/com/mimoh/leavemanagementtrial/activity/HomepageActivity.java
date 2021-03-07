package com.mimoh.leavemanagementtrial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.mimoh.leavemanagementtrial.Constant;
import com.mimoh.leavemanagementtrial.R;
import com.mimoh.leavemanagementtrial.SecurePreferences;
import com.mimoh.leavemanagementtrial.main.SectionsPagerAdapter;

import java.util.Objects;

public class HomepageActivity extends AppCompatActivity {

    private int doubleBackToExitPressed = 1;
    private TabLayout tabs;
    private int tabPosition;
    private  SecurePreferences securePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        securePreferences = new SecurePreferences(this);

        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        final FloatingActionButton BTNlogout = findViewById(R.id.BTNlogout);

        final boolean tag = getIntent().getBooleanExtra(Constant.TAG, false);

        final View parentLayout = findViewById(android.R.id.content);
        if (!tag) {
            Snackbar.make(parentLayout, "Login Success", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            final TabLayout.Tab tab = tabs.getTabAt(1);
            assert tab != null;
            tab.select();
            tabPosition = 1;
        }

        BTNlogout.setOnClickListener(view -> {
            final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle(Constant.Confirm_msg);
            builder1.setMessage("Confirm Logout?");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Yes", (dialog, which) -> logout(true));
            builder1.setNegativeButton("No", (dialog, which) -> { });
            builder1.create().show();
        });
    }

    @Override
    public void onBackPressed() {
        if (tabPosition == 1)
            logout(false);
        else{
            final TabLayout.Tab tab = tabs.getTabAt(1);
            Objects.requireNonNull(tab).select();
            tabPosition = 1;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (securePreferences.getString(Constant.PFNO, Constant.NA).equals(Constant.NA) ||
                securePreferences.getString(Constant.NAME, Constant.NA).equals(Constant.NA) ||
                securePreferences.getString(Constant.MOBILE, Constant.NA).equals(Constant.NA) ||
                securePreferences.getString(Constant.LEVEL, Constant.NA).equals(Constant.NA))
            logout(true);
    }

    public void logout(boolean exit){
        securePreferences.clear();
        if(exit) doubleBackToExitPressed = 2;
        if (doubleBackToExitPressed == 2)
            startActivity(new Intent(HomepageActivity.this, LoginActivity.class).putExtra("login",true));
        else {
            doubleBackToExitPressed++;
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressed = 1, 2000);
    }
}