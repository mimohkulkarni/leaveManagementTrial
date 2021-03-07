package com.mimoh.leavemanagementtrial.fragment.leave;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mimoh.leavemanagementtrial.Constant;
import com.mimoh.leavemanagementtrial.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class StatusFragment extends Fragment {

    private TextView TVempty;
    private LinearLayout topParentLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_status, container, false);

        TVempty = view.findViewById(R.id.TVnothing);
        topParentLayout = view.findViewById(R.id.parentlayout);

        TVempty.setVisibility(View.GONE);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(Constant.PLEASE_WAIT);
        progressDialog.show();
        progressDialog.setCancelable(false);

        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
                JSONObject jsonObject1 = new JSONObject();
                try {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("id", "1");
                    jsonObject2.put("ltype", "C L");
                    jsonObject2.put("sanremark", "Sanctioned");
                    jsonObject2.put("sdate", "01/01/2021");
                    jsonObject2.put("edate", "02/01/2021");
                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("id", "2");
                    jsonObject3.put("ltype", "On-Duty");
                    jsonObject3.put("sanremark", "Not-Sanctioned");
                    jsonObject3.put("sdate", "01/01/2021");
                    jsonObject3.put("edate", "02/01/2021");
                    JSONObject jsonObject4 = new JSONObject();
                    jsonObject4.put("id", "3");
                    jsonObject4.put("ltype", "HAPL");
                    jsonObject4.put("sanremark", "Forwarded");
                    jsonObject4.put("sdate", "01/01/2021");
                    jsonObject4.put("edate", "02/01/2021");
                    JSONObject jsonObject5 = new JSONObject();
                    jsonObject5.put("id", "4");
                    jsonObject5.put("ltype", "C L");
                    jsonObject5.put("sanremark", "Sanctioned");
                    jsonObject5.put("sdate", "01/01/2021");
                    jsonObject5.put("edate", "02/01/2021");
                    JSONObject jsonObject6 = new JSONObject();
                    jsonObject6.put("id", "5");
                    jsonObject6.put("ltype", "C L");
                    jsonObject6.put("sanremark", "Sanctioned");
                    jsonObject6.put("sdate", "01/01/2021");
                    jsonObject6.put("edate", "02/01/2021");


                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(jsonObject2);
                    jsonArray.put(jsonObject3);
                    jsonArray.put(jsonObject4);
                    jsonArray.put(jsonObject5);
                    jsonArray.put(jsonObject6);

                    jsonObject1.put(Constant.RESULT_ARRAY,jsonArray);

                    processJsonDataForLeaves(jsonObject1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return view;
    }

    private void processJsonDataForLeaves(JSONObject response) {

        try {
            JSONArray jsonArray = response.getJSONArray(Constant.RESULT_ARRAY);
//                Toast.makeText(getContext(),json,Toast.LENGTH_SHORT).show();
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    final JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    final String sid = jsonObject1.getString("id");

                    LinearLayout childOneParent = new LinearLayout(getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.leftMargin = 10;
                    layoutParams.rightMargin = 10;
                    childOneParent.setLayoutParams(layoutParams);
                    childOneParent.setOrientation(LinearLayout.VERTICAL);

                    LinearLayout childOneOneParent = new LinearLayout(getContext());
                    childOneOneParent.setWeightSum(2);
                    childOneOneParent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    childOneOneParent.setOrientation(LinearLayout.HORIZONTAL);

                    LinearLayout.LayoutParams TVparams = new LinearLayout.LayoutParams(0,
                            LinearLayout.LayoutParams.MATCH_PARENT,1);

                    final TextView TVoneone = new TextView(getContext());
                    TVoneone.setLayoutParams(TVparams);
                    TVoneone.setText(R.string.status_leave_type);
                    TVoneone.setTextSize(15);
                    TVoneone.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) TVoneone.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TVoneone.setBackgroundResource(R.drawable.border);

                    final TextView TVonetwo = new TextView(getContext());
                    TVonetwo.setLayoutParams(TVparams);
                    TVonetwo.setText(R.string.status_remark);
                    TVonetwo.setTextSize(15);
                    TVonetwo.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) TVonetwo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TVonetwo.setBackgroundResource(R.drawable.border);



                    LinearLayout childOneTwoParent = new LinearLayout(getContext());
                    childOneTwoParent.setWeightSum(2);
                    childOneTwoParent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    childOneTwoParent.setOrientation(LinearLayout.HORIZONTAL);

                    final TextView TVtwoone = new TextView(getContext());
                    TVtwoone.setLayoutParams(TVparams);
                    TVtwoone.setText(jsonObject1.getString("ltype"));
                    TVtwoone.setTextSize(20);
                    TVtwoone.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) TVtwoone.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TVtwoone.setBackgroundResource(R.drawable.border);

                    final TextView TVtwotwo = new TextView(getContext());
                    TVtwotwo.setLayoutParams(TVparams);
                    TVtwotwo.setText(jsonObject1.getString("sanremark"));
                    TVtwotwo.setTextSize(20);
                    TVtwotwo.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) TVtwotwo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TVtwotwo.setBackgroundResource(R.drawable.border);


                    LinearLayout childOneThreeParent = new LinearLayout(getContext());
                    childOneThreeParent.setWeightSum(2);
                    childOneThreeParent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    childOneThreeParent.setOrientation(LinearLayout.HORIZONTAL);

                    final TextView TVthreeone = new TextView(getContext());
                    TVthreeone.setLayoutParams(TVparams);
                    TVthreeone.setText(R.string.leave_from);
                    TVthreeone.setTextSize(15);
                    TVthreeone.setTypeface(TVthreeone.getTypeface(), Typeface.BOLD);
                    TVthreeone.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) TVthreeone.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TVthreeone.setBackgroundResource(R.drawable.border);

                    final TextView TVthreetwo = new TextView(getContext());
                    TVthreetwo.setLayoutParams(TVparams);
                    TVthreetwo.setText(R.string.leave_to);
                    TVthreetwo.setTextSize(15);
                    TVthreetwo.setTypeface(TVthreetwo.getTypeface(), Typeface.BOLD);
                    TVthreetwo.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) TVthreetwo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TVthreetwo.setBackgroundResource(R.drawable.border);


                    LinearLayout childOneFourParent = new LinearLayout(getContext());
                    childOneFourParent.setWeightSum(2);
                    childOneFourParent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    childOneFourParent.setOrientation(LinearLayout.HORIZONTAL);

                    final TextView TVfourone = new TextView(getContext());
                    TVfourone.setLayoutParams(TVparams);
                    TVfourone.setText(jsonObject1.getString("sdate"));
                    TVfourone.setTextSize(20);
                    TVfourone.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) TVfourone.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TVfourone.setBackgroundResource(R.drawable.border);


                    final TextView TVfourtwo = new TextView(getContext());
                    TVfourtwo.setLayoutParams(TVparams);
                    TVfourtwo.setText(jsonObject1.getString("edate"));
                    TVfourtwo.setTextSize(20);
                    TVfourtwo.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) TVfourtwo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TVfourtwo.setBackgroundResource(R.drawable.border);

                    Space spaceParent = new Space(getContext());
                    spaceParent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 130));

                    topParentLayout.addView(childOneParent);
                    childOneParent.addView(childOneOneParent);
                    childOneParent.addView(childOneTwoParent);
                    childOneParent.addView(childOneThreeParent);
                    childOneParent.addView(childOneFourParent);

                    childOneOneParent.addView(TVoneone);
                    childOneOneParent.addView(TVonetwo);

                    childOneTwoParent.addView(TVtwoone);
                    childOneTwoParent.addView(TVtwotwo);

                    childOneThreeParent.addView(TVthreeone);
                    childOneThreeParent.addView(TVthreetwo);

                    childOneFourParent.addView(TVfourone);
                    childOneFourParent.addView(TVfourtwo);

                    childOneParent.addView(spaceParent);
                    childOneParent.setTag(sid);

                    childOneParent.setOnClickListener(v -> {

                        final ProgressDialog progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage(Constant.PLEASE_WAIT);
                        progressDialog.show();
                        progressDialog.setCancelable(false);

                        new CountDownTimer(1500, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) { }

                            @Override
                            public void onFinish() {
                                progressDialog.dismiss();

                                final LayoutInflater layoutInflater = (LayoutInflater) Objects.requireNonNull(getContext()).getSystemService(LAYOUT_INFLATER_SERVICE);
                                final View view1 = layoutInflater.inflate(R.layout.activity_dialog, null);

                                final TextView TVdisplay_leavetype = view1.findViewById(R.id.TVdisplay_leavetype);
                                final TextView TVdisplay_leavefrom = view1.findViewById(R.id.TVdisplay_leavefrom);
                                final TextView TVdisplay_leaveto = view1.findViewById(R.id.TVdisplay_leaveto);
                                final TextView TVdisplay_forward = view1.findViewById(R.id.TVdisplay_forward);
                                final TextView TVdisplay_status = view1.findViewById(R.id.TVdisplay_status);
                                final Button BTNdone = view1.findViewById(R.id.dialog_button);

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setView(view1);
                                AlertDialog dialog = builder.create();

                                TVdisplay_leavetype.setText("C L");
                                TVdisplay_leavefrom.setText("01/01/2021");
                                TVdisplay_leaveto.setText("02/01/2021");
                                TVdisplay_forward.setText("Test User 1");
                                String sanremark1 = "Pending";
                                if (sanremark1.equals("")) sanremark1 = "Pending";
                                if (sanremark1.equals("Forward")) sanremark1 = "Forwarded";
                                TVdisplay_status.setText(sanremark1);

                                BTNdone.setOnClickListener(v1 -> dialog.dismiss());
                                dialog.show();
                            }
                        }.start();
                    });
                }
            }
            else {
                TVempty.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}