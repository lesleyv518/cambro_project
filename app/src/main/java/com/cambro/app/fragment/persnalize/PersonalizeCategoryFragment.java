package com.cambro.app.fragment.persnalize;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cambro.app.R;
import com.cambro.app.adapter.PersonalizeCategoryAdapter;
import com.cambro.app.interfce.Common;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.interfce.Reg;
import com.cambro.app.model.PersonalizeCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalizeCategoryFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    ListView ps_lst_category;
    TextView ps_txt_title;
    LinearLayout ps_ll_title;
    View ps_v_bar;

    public PersonalizeCategoryFragment() {
        // Required empty public constructor
    }

    public static PersonalizeCategoryFragment newInstance() {
        PersonalizeCategoryFragment f = new PersonalizeCategoryFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_persnalize_category, container, false);
        v.findViewById(R.id.pst_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.gc();
                Runtime.getRuntime().gc();
                onButtonPressed("0", false);
            }
        });

        ps_txt_title = (TextView) v.findViewById(R.id.ps_txt_title);
        ps_ll_title = (LinearLayout)v.findViewById(R.id.ps_ll_title);
        ps_v_bar = v.findViewById(R.id.ps_v_bar);

        List<PersonalizeCategory> lst = new ArrayList<PersonalizeCategory>();
        if (Reg.ptKind.equals("tray"))
        {
            ps_ll_title.setBackgroundColor(getResources().getColor(R.color.color_bk_pans));
            ps_txt_title.setText(getString(R.string.ps_ps_a_tray));
            ps_v_bar.setBackground(getResources().getDrawable(R.drawable.ps_title_line_red));

            lst.add(new PersonalizeCategory(Reg.ptKind, Common.ptRound));
            lst.add(new PersonalizeCategory(Reg.ptKind, Common.ptRect));
            lst.add(new PersonalizeCategory(Reg.ptKind, Common.ptTrapezoid));
        }
        else
        {
            ps_ll_title.setBackgroundColor(getResources().getColor(R.color.color_bk_ps_other));
            ps_txt_title.setText(getString(R.string.ps_cambro_product));
            ps_v_bar.setBackground(getResources().getDrawable(R.drawable.ps_title_line_blue));

            lst.add(new PersonalizeCategory(Reg.ptKind, Common.ptPitcher));
            lst.add(new PersonalizeCategory(Reg.ptKind, Common.ptBarware));
            lst.add(new PersonalizeCategory(Reg.ptKind, Common.ptColdfest));
        }

        ps_lst_category = (ListView) v.findViewById(R.id.ps_lst_category);
        ps_lst_category.setAdapter(new PersonalizeCategoryAdapter(getActivity(), this, lst));


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        Runtime.getRuntime().gc();
    }

}
