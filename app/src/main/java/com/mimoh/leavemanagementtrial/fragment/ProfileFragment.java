package com.mimoh.leavemanagementtrial.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mimoh.leavemanagementtrial.Constant;
import com.mimoh.leavemanagementtrial.R;
import com.mimoh.leavemanagementtrial.SecurePreferences;
import com.mimoh.leavemanagementtrial.activity.PasswordChangeActivity;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        final EditText ETpfno = view.findViewById(R.id.ETpfno);
        final EditText ETmobile = view.findViewById(R.id.ETmobile);
        final EditText ETpanno = view.findViewById(R.id.ETpanno);
        final EditText ETdesg = view.findViewById(R.id.ETdesg);
        final EditText ETdept = view.findViewById(R.id.ETdept);
        final EditText ETdob = view.findViewById(R.id.ETdob);
        final EditText ETstation = view.findViewById(R.id.ETstation);
        final EditText ETdop = view.findViewById(R.id.ETdop);
        final EditText ETsincesection = view.findViewById(R.id.ETsincesection);
        final EditText ET7pay = view.findViewById(R.id.ET7pay);
        final EditText ET7paylevel = view.findViewById(R.id.ET7paylevel);
        final EditText ETsection = view.findViewById(R.id.ETsection);
        final EditText ETcategory = view.findViewById(R.id.ETcategory);
        final EditText ETnxctinc = view.findViewById(R.id.ETnxctinc);
        final TextView TVname = view.findViewById(R.id.TVname);
        final ImageView IVprofilePhoto = view.findViewById(R.id.IVprofilePhoto);
        final TextView TVpass = view.findViewById(R.id.TVpass);

        final SecurePreferences securePreferences = new SecurePreferences(getContext());

        ETpfno.setText(securePreferences.getString(Constant.PFNO, Constant.NA));
        ETmobile.setText(securePreferences.getString(Constant.MOBILE,Constant.NA));
        ETpanno.setText(securePreferences.getString(Constant.PANNO,Constant.NA));
        ETdesg.setText(securePreferences.getString(Constant.DESG,Constant.NA));
        ETdept.setText(securePreferences.getString(Constant.DEPT,Constant.NA));
        ETdob.setText(securePreferences.getString(Constant.BDATE,Constant.NA));
        ETstation.setText(securePreferences.getString(Constant.STATION,Constant.NA));
        ETdop.setText(securePreferences.getString(Constant.APDATE,Constant.NA));
        ETsincesection.setText(securePreferences.getString(Constant.DSEC,Constant.NA));
        ET7pay.setText(securePreferences.getString(Constant.VIIPAY,Constant.NA));
        ET7paylevel.setText(securePreferences.getString(Constant.VIILVL,Constant.NA));
        ETsection.setText(securePreferences.getString(Constant.SEC,Constant.NA));
        ETcategory.setText(securePreferences.getString(Constant.CATG,Constant.NA));
        ETnxctinc.setText(securePreferences.getString(Constant.NEXTINC,Constant.NA));
        TVname.setText(securePreferences.getString(Constant.NAME,Constant.NA));

        TVpass.setOnClickListener(v -> startActivity(new Intent(getContext(), PasswordChangeActivity.class).putExtra("login",false)));

        return view;
    }
}