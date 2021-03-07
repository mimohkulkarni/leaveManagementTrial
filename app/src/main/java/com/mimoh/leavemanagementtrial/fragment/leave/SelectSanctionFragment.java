package com.mimoh.leavemanagementtrial.fragment.leave;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mimoh.leavemanagementtrial.Constant;
import com.mimoh.leavemanagementtrial.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class SelectSanctionFragment extends Fragment {

    private TextView TVempty;
    private LinearLayout topParentLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_sanction, container, false);

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
                    jsonObject2.put("pfno", "112233");
                    jsonObject2.put("name", "Test User 1");
                    jsonObject2.put("id", "1");
                    jsonObject2.put("sdate", "Pune");
                    jsonObject2.put("edate", "02/01/2021");
                    JSONObject jsonObject3 = new JSONObject();
                    jsonObject3.put("pfno", "112233");
                    jsonObject3.put("name", "Test User 2");
                    jsonObject3.put("id", "2");
                    jsonObject3.put("sdate", "Pune");
                    jsonObject3.put("edate", "02/01/2021");
                    JSONObject jsonObject4 = new JSONObject();
                    jsonObject4.put("pfno", "112233");
                    jsonObject4.put("name", "Test User 3");
                    jsonObject4.put("id", "3");
                    jsonObject4.put("sdate", "Pune");
                    jsonObject4.put("edate", "02/01/2021");
                    JSONObject jsonObject5 = new JSONObject();
                    jsonObject5.put("pfno", "112233");
                    jsonObject5.put("name", "Test User 4");
                    jsonObject5.put("id", "4");
                    jsonObject5.put("sdate", "Pune");
                    jsonObject5.put("edate", "02/01/2021");

                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(jsonObject2);
                    jsonArray.put(jsonObject3);
                    jsonArray.put(jsonObject4);
                    jsonArray.put(jsonObject5);

                    jsonObject1.put(Constant.RESULT_ARRAY,jsonArray);

                    processJsonDataForShow(jsonObject1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return view;
    }

    private void processJsonDataForShow(JSONObject parentObject) {
        try {
            JSONArray jsonArray = parentObject.getJSONArray(Constant.RESULT_ARRAY);
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    final String sid = jsonObject1.getString("id");

                    LinearLayout childOneParent = new LinearLayout(getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.leftMargin = 10;
                    layoutParams.rightMargin = 10;
                    childOneParent.setLayoutParams(layoutParams);
                    childOneParent.setOrientation(LinearLayout.VERTICAL);

                    LinearLayout.LayoutParams TVparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);

                    LinearLayout.LayoutParams TVparams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT,1);

                    final TextView TVoneone = new TextView(getContext());
                    TVoneone.setLayoutParams(TVparams);
                    TVoneone.setText(R.string.sanction_name);
                    TVoneone.setTextSize(15);
                    TVoneone.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) TVoneone.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TVoneone.setBackgroundResource(R.drawable.border);


                    final TextView TVtwoone = new TextView(getContext());
                    TVtwoone.setLayoutParams(TVparams);
                    TVtwoone.setText(jsonObject1.getString(Constant.NAME));
                    TVtwoone.setTextSize(20);
                    TVtwoone.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) TVtwoone.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TVtwoone.setBackgroundResource(R.drawable.border);


                    final TextView TVthreeone = new TextView(getContext());
                    TVthreeone.setLayoutParams(TVparams);
                    TVthreeone.setText(R.string.pf_no);
                    TVthreeone.setTypeface(TVthreeone.getTypeface(), Typeface.BOLD);
                    TVthreeone.setTextSize(15);
                    TVthreeone.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) TVthreeone.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TVthreeone.setBackgroundResource(R.drawable.border);


                    final TextView TVfourone = new TextView(getContext());
                    TVfourone.setLayoutParams(TVparams);
                    TVfourone.setText(jsonObject1.getString(Constant.PFNO));
                    TVfourone.setTextSize(20);
                    TVfourone.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) TVfourone.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TVfourone.setBackgroundResource(R.drawable.border);



                    LinearLayout childFiveOneParent = new LinearLayout(getContext());
                    childFiveOneParent.setWeightSum(2);
                    childFiveOneParent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    childFiveOneParent.setOrientation(LinearLayout.HORIZONTAL);

                    final TextView TVfiveone = new TextView(getContext());
                    TVfiveone.setLayoutParams(TVparams1);
                    TVfiveone.setText(R.string.leave_from);
                    TVfiveone.setTypeface(TVfiveone.getTypeface(),Typeface.BOLD);
                    TVfiveone.setTextSize(15);
                    TVfiveone.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) TVfiveone.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TVfiveone.setBackgroundResource(R.drawable.border);

                    final TextView TVfivetwo = new TextView(getContext());
                    TVfivetwo.setLayoutParams(TVparams1);
                    TVfivetwo.setText(R.string.leave_to);
                    TVfivetwo.setTypeface(TVfivetwo.getTypeface(),Typeface.BOLD);
                    TVfivetwo.setTextSize(15);
                    TVfivetwo.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) TVfivetwo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TVfivetwo.setBackgroundResource(R.drawable.border);



                    LinearLayout childSixOneParent = new LinearLayout(getContext());
                    childSixOneParent.setWeightSum(2);
                    childSixOneParent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    childSixOneParent.setOrientation(LinearLayout.HORIZONTAL);

                    final TextView TVsixone = new TextView(getContext());
                    TVsixone.setLayoutParams(TVparams1);
                    TVsixone.setText(jsonObject1.getString("sdate"));
                    TVsixone.setTextSize(20);
                    TVsixone.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) TVsixone.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TVsixone.setBackgroundResource(R.drawable.border);

                    final TextView TVsixtwo = new TextView(getContext());
                    TVsixtwo.setLayoutParams(TVparams1);
                    TVsixtwo.setText(jsonObject1.getString("edate"));
                    TVsixtwo.setTextSize(20);
                    TVsixtwo.setGravity(Gravity.CENTER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) TVsixtwo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TVsixtwo.setBackgroundResource(R.drawable.border);


                    Space space = new Space(getContext());
                    space.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            130));

                    topParentLayout.addView(childOneParent);
                    childOneParent.addView(TVoneone);
                    childOneParent.addView(TVtwoone);
                    childOneParent.addView(TVthreeone);
                    childOneParent.addView(TVfourone);
                    childOneParent.addView(childFiveOneParent);
                    childOneParent.addView(childSixOneParent);

                    childFiveOneParent.addView(TVfiveone);
                    childFiveOneParent.addView(TVfivetwo);

                    childSixOneParent.addView(TVsixone);
                    childSixOneParent.addView(TVsixtwo);

                    childOneParent.addView(space);
                    childOneParent.setTag(sid);

                    childOneParent.setOnClickListener(v -> {
                        String id = (String) v.getTag();
                        Bundle bundle = new Bundle();
                        bundle.putString("sid",id);
                        SanctionFragment sanction_fragment = new SanctionFragment();
                        sanction_fragment.setArguments(bundle);
                        FragmentTransaction transaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
                        transaction.replace(R.id.fragment_container, sanction_fragment);
//                        transaction.addToBackStack(null);
                        transaction.commit();
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