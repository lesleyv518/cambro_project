package com.cambro.app.fragment.persnalize;


import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cambro.app.PersonalizeToolActivity;
import com.cambro.app.R;
import com.cambro.app.interfce.Common;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.interfce.Reg;
import com.cambro.app.model.FresFit;
import com.cambro.app.model.PersonalizeCategory;
import com.cambro.app.utils.wheel.ArrayWheelAdapter;
import com.cambro.app.utils.wheel.OnWheelChangedListener;
import com.cambro.app.utils.wheel.OnWheelScrollListener;
import com.cambro.app.utils.wheel.WheelView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalizeTrayGetPriceFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private boolean wheelScrolled = false;
    FresFit selectedDimen;
    WheelView ps_whl_dimen;
    private String category;
    List<FresFit> lstSize = new ArrayList<FresFit>();
    private Integer indexSize = 0;
    private TextView pstgp_txt_gp;
    RelativeLayout pstn_rl_image;
    private ImageView pstn_iv_category, pstn_iv_category_top;
    private PhotoViewAttacher mAttacher;

    public PersonalizeTrayGetPriceFragment() {
        // Required empty public constructor
    }

    public static PersonalizeTrayGetPriceFragment newInstance() {
        PersonalizeTrayGetPriceFragment f = new PersonalizeTrayGetPriceFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_persnalize_tray_gp, container, false);
        category = ((PersonalizeToolActivity)getActivity()).getPsCategory().getCategory();
        initialLoad(v);
        return v;
    }

    private void initialLoad(View v)
    {
        v.findViewById(R.id.pstgp_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reg.ptLastImage = null;
                Reg.ptLastImage = null;
                pstn_iv_category.setImageDrawable(null);
                pstn_iv_category_top.setImageDrawable(null);
                System.gc();
                Runtime.getRuntime().gc();
                onButtonPressed("3", false);
            }
        });

        pstgp_txt_gp = (TextView) v.findViewById(R.id.pstgp_txt_gp);
        pstgp_txt_gp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ps_whl_dimen.getCurrentItem() == 0) return;
                saveProductImage();
                onButtonPressed("9", true);
            }
        });

        pstn_iv_category = (ImageView) v.findViewById(R.id.pstn_iv_category);
        pstn_iv_category_top = (ImageView) v.findViewById(R.id.pstn_iv_category_top);

        if(category.equals(Common.ptRound))
        {
            pstn_iv_category_top.setImageDrawable(getResources().getDrawable(R.drawable.ps_round_tray_outline));
        }
        else if(category.equals(Common.ptRect))
        {
            pstn_iv_category_top.setImageDrawable(getResources().getDrawable(R.drawable.ps_rect_tray_outline));
        }
        else if(category.equals(Common.ptTrapezoid))
        {
            pstn_iv_category_top.setImageDrawable(getResources().getDrawable(R.drawable.ps_trip_tray_outline));
        }
        pstn_iv_category.setImageBitmap(Reg.ptCapturedImage);

        mAttacher = new PhotoViewAttacher(pstn_iv_category);

        pstn_rl_image = (RelativeLayout) v.findViewById(R.id.pstn_rl_image);
        ps_whl_dimen = (WheelView) v.findViewById(R.id.ps_whl_dimen);
        initWheelSize(v, R.id.ps_whl_dimen);

    }

    public Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void saveProductImage()
    {
        Bitmap bm = viewToBitmap((View) pstn_rl_image);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(android.os.Environment.getExternalStorageDirectory()+ "/cambro_product.jpg");
            bm.compress(Bitmap.CompressFormat.PNG, 50, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Reg.ptLastImage = bm;
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

    private void initWheelSize(final View rootView, final int id) {

        if (lstSize.size() > 0) {
            loadWheelItems(lstSize, rootView, id, changedListener, scrolledListener, indexSize);
            return;
        }
        final ProgressDialog dlg = new ProgressDialog(getActivity());
        dlg.setMessage("Loading...");
        dlg.show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(category + "Camtrays");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                if (e == null) {
                    if (list.size() > 0) {

                        String temp = "";

                        FresFit f = new FresFit();
                        f.setObjectId("");
                        f.setName("Select Size");
                        f.setSize("Select Size");
                        f.setDimension("Select Size");
                        f.setTxtColor("red");
                        lstSize.add(f);

                        for (int i = 0; i < list.size(); i++) {
                            ParseObject capacity = list.get(i);
                            FresFit fit = new FresFit();
                            fit.setObjectId(capacity.getObjectId());
                            fit.setName(capacity.getString("Dimension"));
                            String size = capacity.getString("Size");
                            if (size == null) size = Integer.toString(capacity.getInt("Size"));
                            fit.setSize(size);
                            fit.setDimension(capacity.getString("Dimension"));
                            if (capacity.getString("Dimension").equals(temp)) continue;
                            temp = capacity.getString("Dimension");
                            fit.setTxtColor("red");
                            lstSize.add(fit);
                        }
                        loadWheelItems(lstSize, rootView, id, changedListener, scrolledListener, indexSize);
                        indexSize = ps_whl_dimen.getCurrentItem();
                        dlg.dismiss();
                    }
                }
            }
        });
    }

    private void loadWheelItems(List<FresFit> lstFress, View rootView, int id, OnWheelChangedListener cListener, OnWheelScrollListener sListener, Integer indexOfWhl) {
        ArrayWheelAdapter<List<FresFit>> adapter = new ArrayWheelAdapter<List<FresFit>>(getActivity(), lstFress);
        WheelView wheel = getWheel(rootView, id);
        wheel.setViewAdapter(adapter);
        wheel.setCurrentItem(indexOfWhl);
        wheel.setVisibleItems(7);

        wheel.addChangingListener(cListener);
        wheel.addScrollingListener(sListener);
        wheel.setCyclic(true);
        wheel.setEnabled(true);
    }

    private WheelView getWheel(View rootView, int id) {
        return (WheelView) rootView.findViewById(id);
    }

    private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!wheelScrolled) {

            }
        }
    };

    OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            wheelScrolled = true;
        }

        public void onScrollingFinished(WheelView wheel) {
            wheelScrolled = false;
            selectedDimen = lstSize.get(wheel.getCurrentItem());
            if (selectedDimen == null) {
                Toast.makeText(getActivity(), "Please Select Size.", Toast.LENGTH_LONG).show();
                return;
            }
            ((PersonalizeToolActivity)getActivity()).setDimension(selectedDimen.getDimension());
            ((PersonalizeToolActivity) getActivity()).setSize(selectedDimen.getSize());
            ((PersonalizeToolActivity)getActivity()).setProductCode("");
            indexSize = wheel.getCurrentItem();
        }
    };
}
