package com.cambro.app.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cambro.app.R;
import com.cambro.app.SocialActivity;
import com.cambro.app.interfce.Common;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.interfce.Reg;
import com.cambro.app.utils.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TriviaFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private int sSel = -1;
    TextView tr_txt_question, txt1, txt2, txt3, txt4;
    String answer_user_flsg = null, answer_correct, answer;
    Integer quesNum;
    private ImageView tr_iv_week;

    public TriviaFragment() {
        // Required empty public constructor
    }

    public static TriviaFragment newInstance() {
        TriviaFragment f = new TriviaFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        answer_user_flsg = null;
        View v = inflater.inflate(R.layout.fragment_trivia, container, false);
        tr_txt_question = ((TextView) v.findViewById(R.id.tr_txt_question));
        txt1 = ((TextView) v.findViewById(R.id.tv_txt_1));
        txt2 = ((TextView) v.findViewById(R.id.tv_txt_2));
        txt3 = ((TextView) v.findViewById(R.id.tv_txt_3));
        txt4 = ((TextView) v.findViewById(R.id.tv_txt_4));
        tr_iv_week = (ImageView) v.findViewById(R.id.tr_iv_week);
        v.findViewById(R.id.tv_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("4", false);
            }
        });
        v.findViewById(R.id.tv_txt_rule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SocialActivity.class);
                i.putExtra("social", 13);
                i.putExtra("link", Common.TriviaRulsUrl);
                i.putExtra("title", "TRIVIA RULES");
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawButtons(v, 1);
            }
        });
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawButtons(v, 2);
            }
        });
        txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawButtons(v, 3);
            }
        });
        txt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawButtons(v, 4);
            }
        });
        v.findViewById(R.id.tr_iv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sSel == -1) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.tr_info_psa), Toast.LENGTH_LONG).show();
                    return;
                }
                ParseUser user = ParseUser.getCurrentUser();
                if (user == null && Reg.facebookId == null) {
                    onButtonPressed("13", true);
                } else {
                    ParseObject ans = new ParseObject("TriviaAnswers");
                    ans.put("Name", user.get("name"));
                    ans.put("Contact", user.getEmail());
                    ans.put("UserAnswer", answer);
                    ans.put("Correct", (answer_user_flsg.equals(answer_correct) ? "Correct" : "Incorrect"));
                    ans.put("Number", Integer.toString(quesNum));
                    ans.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getActivity(), "Submit Successed.", Toast.LENGTH_LONG).show();
                                SharedPreferences prefs = getActivity().getSharedPreferences("Cambro_Trivia", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putInt("answer_number", quesNum);
                                editor.commit();
                                onButtonPressed("13", true);
                            } else {
                                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
//                                Log.d("Parse.com error", e.toString());
                            }
                        }
                    });

                }
            }
        });
        loadQuestions();
        return v;
    }

    private void loadQuestions() {
        final ProgressDialog dlg = new ProgressDialog(getActivity());
        dlg.setMessage("Loading...");
        dlg.show();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("TriviaQuestions");
        quesNum = Utils.calculateQuestionNumber();
        query.whereEqualTo("Number", quesNum);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                dlg.dismiss();
                if (e == null) {
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            ParseObject ques = list.get(i);
                            Log.e("trivia", ques.toString());
                            tr_txt_question.setText(ques.getString("Question"));
                            String sImageName = ques.getString("Image");
                            if (sImageName != null && sImageName.length() > 0)
                            {
                                sImageName = sImageName.trim().toLowerCase().replace(".jpg","").replace(".png","");
                                Drawable drawable = getActivity().getResources().getDrawable(getActivity().getResources()
                                        .getIdentifier(sImageName, "drawable", getActivity().getPackageName()));
                                tr_iv_week.setImageDrawable(drawable);
                                tr_iv_week.setVisibility(View.VISIBLE);
                            }
                            txt1.setText(ques.getString("A").replace("_D_", getString(R.string.frs_txt_degree)).replace("_R_", getString(R.string.ff_txt_srt)));
                            txt2.setText(ques.getString("B").replace("_D_", getString(R.string.frs_txt_degree)).replace("_R_", getString(R.string.ff_txt_srt)));
                            if(ques.get("C") != null)
                            {
                                txt3.setText(ques.getString("C").replace("_D_", getString(R.string.frs_txt_degree)).replace("_R_", getString(R.string.ff_txt_srt)));
                                txt4.setText(ques.getString("D").replace("_D_", getString(R.string.frs_txt_degree)).replace("_R_", getString(R.string.ff_txt_srt)));
                                txt3.setVisibility(View.VISIBLE);
                                txt4.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                txt3.setVisibility(View.INVISIBLE);
                                txt4.setVisibility(View.INVISIBLE);
                            }
                            answer_correct = ques.getString("answer");
                        }
                    }
                } else {
                    Log.d("Trivia", e.toString());
                }
            }
        });
    }

    private void drawButtons(View v, int i) {
        txt1.setBackground(getResources().getDrawable(R.drawable.trivia_rectangle_off));
        txt2.setBackground(getResources().getDrawable(R.drawable.trivia_rectangle_off));
        txt3.setBackground(getResources().getDrawable(R.drawable.trivia_rectangle_off));
        txt4.setBackground(getResources().getDrawable(R.drawable.trivia_rectangle_off));

        txt1.setTextColor(getResources().getColor(R.color.black));
        txt2.setTextColor(getResources().getColor(R.color.black));
        txt3.setTextColor(getResources().getColor(R.color.black));
        txt4.setTextColor(getResources().getColor(R.color.black));

        if (i == 1) {
            answer = txt1.getText().toString().replace(getString(R.string.frs_txt_degree),"_D_").replace(getString(R.string.ff_txt_srt),"_R_");
            answer_user_flsg = "A";
            txt1.setBackground(getResources().getDrawable(R.drawable.trivia_rectangle_on));
            txt1.setTextColor(getResources().getColor(R.color.white));
        } else if (i == 2) {
            answer = txt2.getText().toString().replace(getString(R.string.frs_txt_degree),"_D_").replace(getString(R.string.ff_txt_srt),"_R_");
            answer_user_flsg = "B";
            txt2.setBackground(getResources().getDrawable(R.drawable.trivia_rectangle_on));
            txt2.setTextColor(getResources().getColor(R.color.white));
        } else if (i == 3) {
            answer = txt3.getText().toString().replace(getString(R.string.frs_txt_degree),"_D_").replace(getString(R.string.ff_txt_srt),"_R_");
            answer_user_flsg = "C";
            txt3.setBackground(getResources().getDrawable(R.drawable.trivia_rectangle_on));
            txt3.setTextColor(getResources().getColor(R.color.white));
        } else if (i == 4) {
            answer = txt4.getText().toString().replace(getString(R.string.frs_txt_degree),"_D_").replace(getString(R.string.ff_txt_srt),"_R_");
            answer_user_flsg = "D";
            txt4.setBackground(getResources().getDrawable(R.drawable.trivia_rectangle_on));
            txt4.setTextColor(getResources().getColor(R.color.white));
        }

        sSel = i;
    }

    public void onButtonPressed(String uri, boolean bl) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri, bl);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
}
