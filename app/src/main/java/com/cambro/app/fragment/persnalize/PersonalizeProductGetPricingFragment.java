package com.cambro.app.fragment.persnalize;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cambro.app.PersonalizeToolActivity;
import com.cambro.app.R;
import com.cambro.app.interfce.Common;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.interfce.Reg;

import java.io.FileOutputStream;
import java.io.IOException;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalizeProductGetPricingFragment extends Fragment {

    String category;
    private OnFragmentInteractionListener mListener;
    ImageView psdg_iv_logo, pspgp_ll_category;
    String selectedColor;
    TextView pspgp_txt_getprice;
    LinearLayout pspgp_ll_whl;
    RelativeLayout pspgp_rl_round;
    private PhotoViewAttacher mAttacher;

    public PersonalizeProductGetPricingFragment() {
        // Required empty public constructor
    }

    public static PersonalizeProductGetPricingFragment newInstance() {
        PersonalizeProductGetPricingFragment f = new PersonalizeProductGetPricingFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_persnalize_prd_get_price, container, false);

        category = ((PersonalizeToolActivity)getActivity()).getPsCategory().getCategory();
        selectedColor = ((PersonalizeToolActivity)getActivity()).getSelectedColor();
        initialLoad(v);
        return v;
    }

    private void initialLoad(View v)
    {
        v.findViewById(R.id.pspg_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.gc();
                Runtime.getRuntime().gc();
                if (selectedColor != null)
                    onButtonPressed("5", false);
                else
                    onButtonPressed("4", false);
                selectedColor = null;
            }
        });
        psdg_iv_logo = (ImageView) v.findViewById(R.id.psdg_iv_logo);
        psdg_iv_logo.setImageBitmap(Reg.ptCapturedImage);
        mAttacher = new PhotoViewAttacher(psdg_iv_logo);

        pspgp_ll_category = (ImageView)v.findViewById(R.id.pspgp_ll_category);

        pspgp_ll_whl = (LinearLayout) v.findViewById(R.id.pspgp_ll_whl);
        pspgp_rl_round = (RelativeLayout) v.findViewById(R.id.pspgp_rl_round);

        pspgp_txt_getprice = (TextView) v.findViewById(R.id.pspgp_txt_getprice);
        pspgp_txt_getprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pspgp_ll_whl.setVisibility(View.INVISIBLE);
                Bitmap bm = viewToBitmap((View) pspgp_rl_round);
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
                ((PersonalizeToolActivity) getActivity()).setProductCode("1418TR");
                ((PersonalizeToolActivity) getActivity()).setDimension("14\" x 18\"");
                onButtonPressed("9", true);
            }
        });
        maskColorBitmap();
        String imgName = "ps_" + ((PersonalizeToolActivity)getActivity()).getPsCategory().getCategory().toLowerCase().trim().replace(" ", "_") + "_table";
        try {
            Drawable drawable = getResources().getDrawable(getResources().getIdentifier(imgName, "drawable", getActivity().getPackageName()));
            pspgp_ll_category.setImageDrawable(drawable);
        }catch (Exception e)
        {}
        v.refreshDrawableState();
    }

    public Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void maskColorBitmap()
    {
        //Drawable d = new BitmapDrawable(getResources(), Reg.ptCapturedImage);
        if (selectedColor != null)
            changeDrawable(psdg_iv_logo.getDrawable(), Integer.parseInt(selectedColor));
    }

    private Bitmap changeDrawable(Drawable drawableResource,int color)
    {
        Drawable drawable = drawableResource;//getResources().getDrawable(drawableResource);
        drawable.setColorFilter( color, PorterDuff.Mode.MULTIPLY );
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    public Bitmap getBitmapWithTransparentBG(Bitmap srcBitmap, int bgColor) {
        Bitmap result = srcBitmap.copy(Bitmap.Config.ARGB_8888, true);
        int nWidth = result.getWidth();
        int nHeight = result.getHeight();
        for (int y = 0; y < nHeight; ++y)
            for (int x = 0; x < nWidth; ++x) {
                int nPixelColor = result.getPixel(x, y);
                if (nPixelColor == bgColor)
                    result.setPixel(x, y, Color.TRANSPARENT);
            }

        return result;

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
