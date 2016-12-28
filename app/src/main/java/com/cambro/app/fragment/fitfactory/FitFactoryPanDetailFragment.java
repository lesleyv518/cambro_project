package com.cambro.app.fragment.fitfactory;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cambro.app.R;
import com.cambro.app.FitFactoryActivity;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.interfce.Reg;
import com.cambro.app.model.FresFit;
import com.cambro.app.utils.Utils;
import com.cambro.app.utils.wheel.ArrayWheelAdapter;
import com.cambro.app.utils.wheel.OnWheelChangedListener;
import com.cambro.app.utils.wheel.OnWheelScrollListener;
import com.cambro.app.utils.wheel.WheelView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FitFactoryPanDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    List<FresFit> lstSize = new ArrayList<FresFit>();
    List<FresFit> lstFit = new ArrayList<FresFit>();
    private boolean wheelScrolled = false;
    FresFit selectedPan;
    WheelView ffpd_whl_size;
    LinearLayout ffpd_ll_images, ffpd_ll_tum_im;
    Integer indexSize = 0;
    String category;
    TextView ffpd_txt_categoryname;

    public FitFactoryPanDetailFragment() {
        // Required empty public constructor
    }

    public static FitFactoryPanDetailFragment newInstance() {
        FitFactoryPanDetailFragment f = new FitFactoryPanDetailFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_fitfactory_pans_detail, container, false);

        ffpd_whl_size = (WheelView) v.findViewById(R.id.ffpd_whl_size);
        ffpd_ll_images = (LinearLayout) v.findViewById(R.id.ffpd_ll_images);
        ffpd_ll_tum_im = (LinearLayout) v.findViewById(R.id.ffpd_ll_tum_im);
        ffpd_txt_categoryname = (TextView) v.findViewById(R.id.ffpd_txt_categoryname);

        v.findViewById(R.id.ffpd_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("2", false);
            }
        });

        initLoad(v);

        return v;
    }

    private void initLoad(View v) {

        category = ((FitFactoryActivity) getActivity()).getCategory();
        if (category.equals("TranslucentFoodPans"))
            ffpd_txt_categoryname.setText(getString(R.string.ff_txt_tfp));
        else if (category.equals("CamwearPans"))
            ffpd_txt_categoryname.setText(getString(R.string.ff_txt_cp));
        else if (category.equals("HighHeatFoodPans"))
            ffpd_txt_categoryname.setText(getString(R.string.ff_txt_hhfp));

        if (!Reg.blLoadModePan) {
            loadWheelItems(lstSize, v, R.id.ffpd_whl_size, changedListener, scrolledListener, indexSize);
            addFitImages(lstFit);
        } else {
            indexSize = 0;
            lstSize.clear();
            lstFit.clear();
            initWheelCapacity(v, R.id.ffpd_whl_size);
        }
    }

    private void loadFitImages() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(category);

        if (selectedPan == null)
            selectedPan = lstSize.get(ffpd_whl_size.getCurrentItem());

        query.whereEqualTo("Size", selectedPan.getSize());
        query.orderByAscending("ProductCode");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                if (e == null) {
                    ffpd_ll_tum_im.removeAllViews();
                    if (list.size() > 0) {

                        lstFit = new ArrayList<FresFit>();
                        String temp = "";
                        for (int i = 0; i < list.size(); i++) {
                            ParseObject fruit = list.get(i);

                            if (fruit.getString("ProductCode").equals(temp)) continue;
                            temp = fruit.getString("ProductCode");

                            FresFit fresFit = new FresFit();
                            fresFit.setObjectId(fruit.getObjectId());
                            fresFit.setName(fruit.getString("ProductCode"));
                            fresFit.setProductCode(fruit.getString("ProductCode"));
                            fresFit.setSize(fruit.getString("Size"));
                            fresFit.setProductDescription(fruit.getString("ProductDescription"));
                            fresFit.setLidDescription(fruit.getString("LidDescription"));
                            fresFit.setPanDepth(fruit.getString("PanDepth"));
                            fresFit.setMetricPanDepth(fruit.getString("MetricPanDepth"));

                            lstFit.add(fresFit);
                        }
                        addFitImages(lstFit);
                    }
                } else {
//                    Log.d("FresFit", e.toString());
                }
            }
        });
    }

    private void addFitImages(List<FresFit> lstFit) {

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        for (int i = 0; i < lstFit.size(); i++) {
            final FresFit fit = lstFit.get(i);

            try {

                LinearLayout layout = new LinearLayout(getActivity());
                int dimen = (int) getResources().getDimension(R.dimen.font_image);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dimen, LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins((int)getResources().getDimension(R.dimen.font_textview), 0, (int)getResources().getDimension(R.dimen.font_textview), 0);
                layout.setLayoutParams(layoutParams);

                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((FitFactoryActivity) getActivity()).setSelectedFit(fit);
                        loadLids(fit);
                        Reg.blLoadModePan = false;
                    }
                });

                ImageView imageView = new ImageView(getActivity());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 3.0f));
                Utils.loadFitImageByDrawableName(getActivity(), fit, imageView);

                TextView textView = new TextView(getActivity());
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                textView.setPadding(2, 2, 2, 2);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(getResources().getColor(R.color.black));
                textView.setTextSize(getResources().getDimension(R.dimen.font_textview) / metrics.density);
                String pName = fit.getProductDescription().replace("_R_", getString(R.string.ff_txt_srt)) + "\n"
                        + fit.getPanDepth().replace("_12_", getString(R.string.ff_txt_sub_12)) + "/" + fit.getMetricPanDepth();
                textView.setText(pName);

                layout.addView(imageView);
                layout.addView(textView);
                ffpd_ll_tum_im.addView(layout);

            } catch (Exception e1) {
                //Log.d("imagename", imgName);
            }
        }
    }

    private void loadLids(FresFit fresFit) {

        final List<FresFit> lstLids = new ArrayList<FresFit>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(category);
        query.whereEqualTo("Size", fresFit.getSize());
        query.whereEqualTo("ProductCode", fresFit.getProductCode());
        query.whereEqualTo("ProductDescription", fresFit.getProductDescription());
        query.orderByAscending("ProductCode");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                if (e == null) {
                    if (list.size() > 0) {
                        String temp = "";
                        for (int i = 0; i < list.size(); i++) {
                            ParseObject lid = list.get(i);
                            if (lid.getString("LidCode").equals("N/A")) continue;
                            if (lid.getString("LidCode").equals(temp)) continue;
                            temp = lid.getString("LidCode");

                            FresFit fit = new FresFit();
                            fit.setObjectId(lid.getObjectId());
                            fit.setName(lid.getString("LidCode"));

                            fit.setSize(lid.getString("Size"));
                            fit.setLidDescription(lid.getString("LidDescription"));
                            fit.setProductCode(lid.getString("ProductCode"));
                            fit.setLidCode(fit.getName());
                            fit.setMl(lid.getString("ML"));
                            fit.setPanDepth(lid.getString("PanDepth"));
                            fit.setMetricPanDepth(lid.getString("MetricPanDepth"));

                            fit.setProductDescription(lid.getString("ProductDescription"));
                            lstLids.add(fit);
                        }
                        if (lstLids.size() > 0) {
                            ((FitFactoryActivity) getActivity()).setLstLids(lstLids);
                            onButtonPressed("4", true);
                        } else
                            onButtonPressed("5", true);
                    } else {
                        onButtonPressed("5", true);
                    }
                } else {
//                    Log.d("FresFit", e.toString());
                }
            }
        });
    }

    private void initWheelCapacity(final View rootView, final int id) {

        if (lstSize.size() > 0) {
            loadWheelItems(lstSize, rootView, id, changedListener, scrolledListener, indexSize);
            return;
        }
        final ProgressDialog dlg = new ProgressDialog(getActivity());
        dlg.setMessage("Loading...");
        dlg.show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(category);
        query.orderByAscending("Size");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                if (e == null) {
                    if (list.size() > 0) {

                        String temp = "";
                        for (int i = 0; i < list.size(); i++) {
                            ParseObject capacity = list.get(i);

                            FresFit fit = new FresFit();
                            fit.setObjectId(capacity.getObjectId());
                            fit.setName(capacity.getString("Size"));
                            fit.setSize(capacity.getString("Size"));
                            fit.setProductCode(capacity.getString("ProductCode"));
                            if (capacity.getString("Size").equals(temp)) continue;
                            temp = capacity.getString("Size");
                            fit.setTxtColor("red");
                            lstSize.add(fit);
                        }
                        loadWheelItems(lstSize, rootView, id, changedListener, scrolledListener, indexSize);
                        indexSize = ffpd_whl_size.getCurrentItem();
                        loadFitImages();
                        dlg.dismiss();
                    }
                } else {
//                    Log.d("FresFit", e.toString());
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
                // updateStatus();
            }
        }
    };

    OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            wheelScrolled = true;
        }

        public void onScrollingFinished(WheelView wheel) {
            wheelScrolled = false;
            // updateStatus();
            selectedPan = lstSize.get(wheel.getCurrentItem());
            //selectedStorage = lstLids.get(ffpdp_whl_size.getCurrentItem());
            if (selectedPan == null) {
                Toast.makeText(getActivity(), "Please Select Capacity.", Toast.LENGTH_LONG).show();
                return;
            }
            indexSize = wheel.getCurrentItem();
            loadFitImages();
            Reg.blLoadModePan = false;
        }
    };

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
}
