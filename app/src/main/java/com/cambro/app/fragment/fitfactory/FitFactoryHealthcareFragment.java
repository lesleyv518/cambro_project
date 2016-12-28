package com.cambro.app.fragment.fitfactory;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class FitFactoryHealthcareFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    List<FresFit> lstCapacity = new ArrayList<FresFit>();
    List<FresFit> lstFamily = new ArrayList<FresFit>();
    List<FresFit> lstHealthcare = new ArrayList<FresFit>();
    private boolean wheelScrolled = false, WheelScrolledVeg = false;
    FresFit selectedCapacity, selectedFamily;
    WheelView ffh_whl_healthcare, ffh_whl_family;
    LinearLayout ffh_ll_tum_im;
    Integer indexCapacity = 0, indexFamily = 0;
    View rootView;
    private TextView ffh_txt_tf;

    public FitFactoryHealthcareFragment() {
        // Required empty public constructor
    }

    public static FitFactoryHealthcareFragment newInstance() {
        FitFactoryHealthcareFragment f = new FitFactoryHealthcareFragment();
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

        rootView = inflater.inflate(R.layout.fragment_fitfactory_healthcare, container, false);
        ffh_txt_tf = (TextView) rootView.findViewById(R.id.ffh_txt_tf);

        ffh_whl_healthcare = (WheelView) rootView.findViewById(R.id.ffh_whl_healthcare);
        ffh_whl_family = (WheelView) rootView.findViewById(R.id.ffh_whl_family);

        ffh_ll_tum_im = (LinearLayout) rootView.findViewById(R.id.ffh_ll_tum_im);
        rootView.findViewById(R.id.ffh_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("0", false);
            }
        });

        initLoad(rootView);
        return rootView;
    }

    private void initLoad(View v) {
        if (!Reg.blLoadModeHealthcare) {
            loadWheelItems(lstCapacity, v, R.id.ffh_whl_healthcare, changedListener, scrolledListener, indexCapacity);
            loadWheelItems(lstFamily, v, R.id.ffh_whl_family, changedListenerFamily, scrolledListenerFamily, indexFamily);
            addFitImages(ffh_ll_tum_im, lstHealthcare);
        } else {
            indexCapacity = 0;
            initWheelCapacity(v, R.id.ffh_whl_healthcare);
        }
    }

    private void loadLids(FresFit fresFit) {

        final List<FresFit> lstLids = new ArrayList<FresFit>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Healthcare");
        query.whereEqualTo("ML", fresFit.getMl());
        query.whereEqualTo("ProductDescription", fresFit.getProductDescription());
        query.orderByAscending("LidCode");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                if (e == null) {
                    if (list.size() > 0) {
                        String temp = "";
                        for (int i = 0; i < list.size(); i++) {
                            ParseObject lid = list.get(i);
                            if (lid.getString("LidCode").equals("N/A")) continue;
                            FresFit fit = new FresFit();
                            fit.setObjectId(lid.getObjectId());
                            fit.setName(lid.getString("LidCode"));
                            if (fit.getName().equals(temp)) continue;
                            temp = fit.getName();
                            fit.setProductCode(lid.getString("ProductCode"));
                            fit.setLidCode(fit.getName());
                            fit.setMl(lid.getString("ML"));

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

        if (lstCapacity.size() > 0) {
            loadWheelItems(lstCapacity, rootView, id, changedListener, scrolledListener, indexCapacity);
            return;
        }
        final ProgressDialog dlg = new ProgressDialog(getActivity());
        dlg.setMessage("Loading...");
        dlg.show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Healthcare");
        query.orderByAscending("ML");
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
                            fit.setName(capacity.getString("OZ") + " OZ (" + capacity.getString("ML") + " ML)");
                            fit.setMl(capacity.getString("ML"));
                            fit.setProductCode(capacity.getString("ProductCode"));
                            if (capacity.getString("ML").equals(temp)) continue;
                            temp = capacity.getString("ML");
                            fit.setOz(capacity.getString("OZ"));
                            fit.setTxtColor("red");
                            lstCapacity.add(fit);
                        }
                        loadWheelItems(lstCapacity, rootView, id, changedListener, scrolledListener, indexCapacity);
                        selectedCapacity = lstCapacity.get(indexCapacity);
                        loadFitImages(false);
                        initWheelFamily(rootView, R.id.ffh_whl_family);
                        dlg.dismiss();
                    }
                } else {
//                    Log.d("FresFit", e.toString());
                }
            }
        });
    }

    private void initWheelFamily(final View rootView, final int id) {

        if (lstFamily.size() > 0) {
            loadWheelItems(lstFamily, rootView, id, changedListenerFamily, scrolledListenerFamily, indexFamily);
            return;
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Healthcare");
        query.whereEqualTo("ML", selectedCapacity.getMl());
        query.orderByAscending("ProductDescription");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                if (e == null) {
                    if (list.size() > 0) {

                        String temp = "";
                        for (int i = 0; i < list.size(); i++) {
                            ParseObject family = list.get(i);

                            if (family.getString("ProductDescription").equals(temp))
                                continue;
                            temp = family.getString("ProductDescription");
//                            Log.d("temp", temp);

                            FresFit fresFit = new FresFit();
                            fresFit.setObjectId(family.getObjectId());
                            fresFit.setName(family.getString("ProductDescription"));
                            fresFit.setProductCode(family.getString("ProductCode"));
                            fresFit.setName(fresFit.getName().replace("_R_", getResources().getString(R.string.ff_txt_srt)));
                            fresFit.setProductDescription(family.getString("ProductDescription"));
                            fresFit.setMl(family.getString("ML"));
                            fresFit.setTxtColor("red");

                            lstFamily.add(fresFit);
                        }
//                        Log.d("size-family", Integer.toString(lstFamily.size()));
                        if (lstFamily.size() > 1)
                        {
                            loadWheelItems(lstFamily, rootView, id, changedListenerFamily, scrolledListenerFamily, indexFamily);
                            ffh_whl_family.setVisibility(View.VISIBLE);
                            ffh_txt_tf.setVisibility(View.GONE);
                        }
                        else
                        {
                            ffh_txt_tf.setText(lstFamily.get(0).getName());
                            ffh_whl_family.setVisibility(View.GONE);
                            ffh_txt_tf.setVisibility(View.VISIBLE);
                        }

                    }
                } else {
//                    Log.d("FresFit", e.toString());
                }
            }
        });
    }

    private void addFitImages(LinearLayout container, List<FresFit> lstFit) {

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        for (int i = 0; i < lstFit.size(); i++) {
            final FresFit fresFit = lstFit.get(i);
            String imgName = "";

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
                        ((FitFactoryActivity) getActivity()).setSelectedFit(fresFit);
                        ((FitFactoryActivity) getActivity()).setCategory("Healthcare");
                        loadLids(fresFit);
                    }
                });

                ImageView imageView = new ImageView(getActivity());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 3.0f));
                Utils.loadFitImageByDrawableName(getActivity(), fresFit, imageView);

                TextView textView = new TextView(getActivity());
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                textView.setPadding(2, 2, 2, 2);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(getResources().getColor(R.color.black));
                textView.setTextSize(getResources().getDimension(R.dimen.font_textview2) / metrics.density);
                textView.setText(fresFit.getProductDescription().replace("_R_", getString(R.string.ff_txt_srt)));

                layout.addView(imageView);
                layout.addView(textView);
                container.addView(layout);

            } catch (Exception e1) {
//                Log.d("imagename", imgName);
            }
        }
        Reg.blLoadModeHealthcare = false;
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
            selectedCapacity = lstCapacity.get(wheel.getCurrentItem());
            //selectedStorage = lstLids.get(ffp_whl_size.getCurrentItem());
            if (selectedCapacity == null) {
                Toast.makeText(getActivity(), "Please Select Capacity.", Toast.LENGTH_LONG).show();
                return;
            }

            indexCapacity = wheel.getCurrentItem();
            loadFitImages(false);
            lstFamily = new ArrayList<FresFit>();
            initWheelFamily(rootView, R.id.ffh_whl_family);
            Reg.blLoadModeHealthcare = false;
        }
    };

    private void loadFitImages(boolean bFamily) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Healthcare");
        if (selectedCapacity == null)
            selectedCapacity = lstCapacity.get(ffh_whl_healthcare.getCurrentItem());
        query.whereEqualTo("ML", selectedCapacity.getMl());
        if (bFamily) {
            query.whereEqualTo("ProductDescription", selectedFamily.getProductDescription());
        }
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                if (e == null) {
                    ffh_ll_tum_im.removeAllViews();
                    if (list.size() > 0) {

                        lstHealthcare = new ArrayList<FresFit>();
                        String temp = "";
                        for (int i = 0; i < list.size(); i++) {

                            ParseObject fruit = list.get(i);

                            if (fruit.getString("ProductCode").equals(temp)) continue;
                            temp = fruit.getString("ProductCode");

                            FresFit fresFit = new FresFit();
                            fresFit.setObjectId(fruit.getObjectId());
                            fresFit.setName(fruit.getString("ProductCode"));
                            fresFit.setProductCode(fruit.getString("ProductCode"));
                            fresFit.setMl(fruit.getString("ML"));
                            fresFit.setProductDescription(fruit.getString("ProductDescription"));

                            lstHealthcare.add(fresFit);
                        }
                        addFitImages(ffh_ll_tum_im, lstHealthcare);
                    }
                } else {
//                    Log.d("FresFit", e.toString());
                }
            }
        });
    }


    private OnWheelChangedListener changedListenerFamily = new OnWheelChangedListener() {
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!WheelScrolledVeg) {
                // updateStatus();
            }
        }
    };

    OnWheelScrollListener scrolledListenerFamily = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            WheelScrolledVeg = true;
        }

        public void onScrollingFinished(WheelView wheel) {
            WheelScrolledVeg = false;
            // updateStatus();
            selectedFamily = lstFamily.get(wheel.getCurrentItem());
            if (selectedFamily == null) {
                Toast.makeText(getActivity(), "Please Select Family.", Toast.LENGTH_LONG).show();
                return;
            }
            indexFamily = wheel.getCurrentItem();
            loadFitImages(true);
            Reg.blLoadModeTumbler = false;
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
