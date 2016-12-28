package com.cambro.app.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cambro.app.R;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.interfce.Reg;
import com.cambro.app.model.FresFit;
import com.cambro.app.utils.image.ImageLoader;
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
public class FreshnessFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    List<FresFit> lstFruit = new ArrayList<FresFit>();
    List<FresFit> lstVeg = new ArrayList<FresFit>();
    ImageLoader imageLoader = new ImageLoader(getActivity());
    ImageLoader imageLoaderVeg = new ImageLoader(getActivity());
    private boolean wheelScrolled = false, WheelScrolledVeg = false;
    FresFit selectedFruit, selectedVeg;

    WheelView fr_whl_fruit, fr_whl_veg;

    public FreshnessFragment() {
        // Required empty public constructor
    }

    public static FreshnessFragment newInstance() {
        FreshnessFragment f = new FreshnessFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_freshness, container, false);
        fr_whl_fruit = (WheelView) v.findViewById(R.id.fr_whl_fruit);
        fr_whl_veg = (WheelView) v.findViewById(R.id.fr_whl_veg);
        v.findViewById(R.id.fr_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFruit = null;
                selectedVeg = null;
                Reg.selectedFruit = null;
                Reg.selectedVeg = null;
                onButtonPressed("5", false);
            }
        });

        v.findViewById(R.id.fr_txt_fdone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedFruit = lstFruit.get(fr_whl_fruit.getCurrentItem());
                if (selectedFruit == null)
                {
                    Toast.makeText(getActivity(), "Please Select Fruit.", Toast.LENGTH_LONG).show();
                    return;
                }
                Reg.selectedFruit = selectedFruit;
                Reg.selectedFresh = "Fruit";
                onButtonPressed("15", true);
            }
        });
        v.findViewById(R.id.fr_txt_vdone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedVeg = lstVeg.get(fr_whl_veg.getCurrentItem());

                if (selectedVeg == null)
                {
                    Toast.makeText(getActivity(), "Please Select Vegetable.", Toast.LENGTH_LONG).show();
                    return;
                }
                Reg.selectedVeg = selectedVeg;
                Reg.selectedFresh = "Vegetable";
                onButtonPressed("15", true);
            }
        });
        initWheelFruit(v, R.id.fr_whl_fruit);
        initWheelVeg(v, R.id.fr_whl_veg);
        return v;
    }

    private void initWheelFruit(final View rootView,final int id) {

        if (lstFruit.size() > 0){
            loadWheelItems(lstFruit, rootView, id, changedListener,scrolledListener);
            //selectedFruit = lstFruit.get(7);
            return;
        }
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Fruits");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                if (e == null) {
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            ParseObject fruit = list.get(i);

                            FresFit fresFit = new FresFit();
                            fresFit.setObjectId(fruit.getObjectId());
                            fresFit.setContainer(fruit.getString("Container"));
                            fresFit.setHumidity(fruit.getString("Humidity"));
                            fresFit.setImage(fruit.getParseFile("Image"));
                            //fresFit.setdImg(imageLoader.getBitmap(fresFit.getImage().getUrl()));
                            fresFit.setLink(fruit.getString("Link"));
                            fresFit.setName(fruit.getString("Name"));
                            fresFit.setProductImage(fruit.getString("ProductImage"));
                            fresFit.setTemperature(fruit.getString("Temperature"));
                            fresFit.setTip(fruit.getString("Tip"));

                            lstFruit.add(fresFit);
                        }
                        loadWheelItems(lstFruit, rootView, id, changedListener,scrolledListener);

                    }
                } else {

                }
            }
        });
    }

    private void loadWheelItems(List<FresFit> lstFress, View rootView, int id, OnWheelChangedListener cListener, OnWheelScrollListener sListener)
    {
        ArrayWheelAdapter<List<FresFit>> adapter = new ArrayWheelAdapter<List<FresFit>>(getActivity(), lstFress);
        WheelView wheel = getWheel(rootView, id);
        wheel.setViewAdapter(adapter);
        wheel.setCurrentItem((int) (Math.random() * 10));
        wheel.setVisibleItems(7);

        wheel.addChangingListener(cListener);
        wheel.addScrollingListener(sListener);
        wheel.setCyclic(true);
        wheel.setEnabled(true);
    }

    private void initWheelVeg(final View rootView,final int id) {

        if (lstVeg.size() > 0){
            loadWheelItems(lstVeg, rootView, id, changedListenerVeg,scrolledListenerVeg);
            //selectedVeg = lstVeg.get(7);
            return;
        }
        final ProgressDialog dlg = new ProgressDialog(getActivity());
        dlg.setMessage("Loading...");
        dlg.show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Vegetables");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                if (e == null) {
                    if (list.size() > 0) {
                        //Toast.makeText(getActivity(), Integer.toString(list.size()), Toast.LENGTH_LONG).show();
                        for (int i = 0; i < list.size(); i++) {
                            ParseObject veg = list.get(i);

                            FresFit fresFit = new FresFit();
                            fresFit.setObjectId(veg.getObjectId());
                            fresFit.setContainer(veg.getString("Container"));
                            fresFit.setHumidity(veg.getString("Humidity"));
                            fresFit.setImage(veg.getParseFile("Image"));
                            //fresFit.setdImg(imageLoaderVeg.getBitmap(fresFit.getImage().getUrl()));
                            fresFit.setLink(veg.getString("Link"));
                            fresFit.setName(veg.getString("Name"));
                            fresFit.setProductImage(veg.getString("ProductImage"));
                            fresFit.setTemperature(veg.getString("Temperature"));
                            fresFit.setTip(veg.getString("Tip"));

                            lstVeg.add(fresFit);
                        }
                        loadWheelItems(lstVeg, rootView, id, changedListenerVeg,scrolledListenerVeg);
                        dlg.dismiss();

                    }
                } else {
//                    Log.d("FresFit", e.toString());
                }
            }
        });
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
            selectedFruit = lstFruit.get(wheel.getCurrentItem());
//            Toast.makeText(getActivity(), lstFruit.get(wheel.getCurrentItem()).getName(), Toast.LENGTH_LONG).show();
        }
    };

    private OnWheelChangedListener changedListenerVeg = new OnWheelChangedListener() {
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!WheelScrolledVeg) {
                // updateStatus();
            }
        }
    };

    OnWheelScrollListener scrolledListenerVeg = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            WheelScrolledVeg = true;
        }

        public void onScrollingFinished(WheelView wheel) {
            WheelScrolledVeg = false;
            // updateStatus();
            selectedVeg = lstVeg.get(wheel.getCurrentItem());
//            Toast.makeText(getActivity(), lstVeg.get(wheel.getCurrentItem()).getName(), Toast.LENGTH_LONG).show();
        }
    };

    private WheelView getWheel(View rootView, int id) {
        return (WheelView) rootView.findViewById(id);
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
}
