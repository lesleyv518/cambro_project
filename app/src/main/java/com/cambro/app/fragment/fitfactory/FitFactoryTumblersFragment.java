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
import com.cambro.app.adapter.FitFacotyLidListViewAdapter;
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
public class FitFactoryTumblersFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    List<FresFit> lstCapacity = new ArrayList<FresFit>();
    List<FresFit> lstFamily = new ArrayList<FresFit>();
    List<FresFit> lstFit = new ArrayList<FresFit>();
    private boolean wheelScrolled = false, WheelScrolledVeg = false;
    FresFit selectedCapacity, selectedFamily;
    WheelView ff_whl_tc, ff_whl_tf;
    LinearLayout ff_ll_images, ff_ll_tum_im;
    Integer indexFamily = 0, indexCapacity = 0;
    View ff_v_blank;
    View rootView;
    TextView fft_txt_tf;

    public FitFactoryTumblersFragment() {
        // Required empty public constructor
    }

    public static FitFactoryTumblersFragment newInstance() {
        FitFactoryTumblersFragment f = new FitFactoryTumblersFragment();
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

        rootView = inflater.inflate(R.layout.fragment_fitfactory_thmbler, container, false);

        ff_whl_tc = (WheelView) rootView.findViewById(R.id.ff_whl_tc);
        ff_whl_tf = (WheelView) rootView.findViewById(R.id.ff_whl_tf);
        ff_ll_images = (LinearLayout) rootView.findViewById(R.id.ff_ll_images);
        ff_ll_tum_im = (LinearLayout) rootView.findViewById(R.id.ff_ll_tum_im);
        ff_v_blank = (View) rootView.findViewById(R.id.ff_v_blank);

        fft_txt_tf = (TextView) rootView.findViewById(R.id.fft_txt_tf);

        rootView.findViewById(R.id.ff_ll_thumbler).setBackgroundColor(getResources().getColor(R.color.color_bk_thumbler));
        rootView.findViewById(R.id.ff_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed("0", false);
            }
        });

        initLoad(rootView);

        return rootView;
    }

    private void initLoad(View v) {
        if (!Reg.blLoadModeTumbler) {
            loadWheelItems(lstCapacity, v, R.id.ff_whl_tc, changedListener, scrolledListener, indexCapacity);
            loadWheelItems(lstFamily, v, R.id.ff_whl_tf, changedListenerFamily, scrolledListenerFamily, indexFamily);
            addFitImages(lstFit);
        } else {
            indexCapacity = 0;
            indexFamily = 0;
            initWheelCapacity(v, R.id.ff_whl_tc);
        }
    }

    private void loadFitImages(boolean bCapacity, boolean bFamily) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DisposableLids");
        if (bCapacity) {
            if (selectedCapacity == null)
                selectedCapacity = lstCapacity.get(ff_whl_tc.getCurrentItem());
            query.whereEqualTo("ML", selectedCapacity.getMl());
        }
        if (bFamily) {
            query.whereEqualTo("ProductDescription", selectedFamily.getProductDescription());
//            Log.d("ProductDescription", selectedFamily.getProductDescription());
        }

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                if (e == null) {
                    ff_ll_tum_im.removeAllViews();
                    if (list.size() > 0) {

                        lstFit = new ArrayList<FresFit>();
                        String temp = "";
                        for (int i = 0; i < list.size(); i++) {
                            ParseObject fruit = list.get(i);

                            if (fruit.getString("ProductCode").equals(temp))
                                continue;
                            temp = fruit.getString("ProductCode");
                            FresFit fresFit = new FresFit();
                            fresFit.setObjectId(fruit.getObjectId());
                            fresFit.setName(fruit.getString("ProductCode"));
                            fresFit.setProductCode(fruit.getString("ProductCode"));
                            fresFit.setMl(fruit.getString("ML"));
                            fresFit.setProductDescription(fruit.getString("ProductDescription"));

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
            final FresFit fresFit = lstFit.get(i);

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
                        ((FitFactoryActivity) getActivity()).setCategory("DisposableLids");
                        loadLids(fresFit);
                        Reg.blLoadModeTumbler = false;
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
                ff_ll_tum_im.addView(layout);

            } catch (Exception e1) {
                //Log.d("imagename", imgName);
            }
        }
    }

    private void loadLids(FresFit fresFit) {

        final List<FresFit> lstLids = new ArrayList<FresFit>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DisposableLids");
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
                        if(lstLids.size() > 0)
                        {
                            ((FitFactoryActivity) getActivity()).setLstLids(lstLids);
                            onButtonPressed("4", true);
                        }
                        else
                            onButtonPressed("5", true);
                    }
                    else
                    {
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
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DisposableLids");
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
                            if (capacity.getString("ML").equals(temp))
                                continue;
                            fit.setObjectId(capacity.getObjectId());
                            fit.setProductCode(capacity.getString("ProductCode"));
                            fit.setName(capacity.getString("OZ") + " OZ (" + capacity.getString("ML") + " ML)");
                            fit.setMl(capacity.getString("ML"));
                            temp = capacity.getString("ML");
                            fit.setOz(capacity.getString("OZ"));
                            fit.setTxtColor("red");
                            lstCapacity.add(fit);
                        }
                        loadWheelItems(lstCapacity, rootView, id, changedListener, scrolledListener, indexCapacity);
                        selectedCapacity = lstCapacity.get(indexCapacity);
                        initWheelFamily(rootView, R.id.ff_whl_tf);
                        loadFitImages(true, false);
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
            selectedCapacity = lstCapacity.get(wheel.getCurrentItem());
            if (selectedCapacity == null) {
                Toast.makeText(getActivity(), "Please Select Capacity.", Toast.LENGTH_LONG).show();
                return;
            }
            indexCapacity = wheel.getCurrentItem();
            loadFitImages(true, false);
            lstFamily = new ArrayList<FresFit>();
            initWheelFamily(rootView, R.id.ff_whl_tf);
            Reg.blLoadModeTumbler = false;
        }
    };

    private void initWheelFamily(final View rootView, final int id) {

        if (lstFamily.size() > 0) {
            loadWheelItems(lstFamily, rootView, id, changedListenerFamily, scrolledListenerFamily, indexFamily);
            return;
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("DisposableLids");
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
                        if (lstFamily.size() > 1)
                        {
                            loadWheelItems(lstFamily, rootView, id, changedListenerFamily, scrolledListenerFamily, indexFamily);
                            ff_whl_tf.setVisibility(View.VISIBLE);
                            fft_txt_tf.setVisibility(View.GONE);
                        }
                        else
                        {
                            fft_txt_tf.setText(lstFamily.get(0).getName());
                            ff_whl_tf.setVisibility(View.GONE);
                            fft_txt_tf.setVisibility(View.VISIBLE);
                        }
//                        Log.d("size-family", Integer.toString(lstFamily.size()));

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
            loadFitImages(true, true);
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
