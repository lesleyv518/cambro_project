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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cambro.app.PersonalizeToolActivity;
import com.cambro.app.R;
import com.cambro.app.interfce.Common;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.github.danielnilsson9.colorpickerview.view.ColorPickerView;


/**
 * A placeholder fragment containing a simple view.
 */
public class ColorPickerFragment extends Fragment implements ColorPickerView.OnColorChangedListener{

    private OnFragmentInteractionListener mListener;
//    private ImageView iv_original,iv_transparent;
    private ColorPickerView mColorPickerView;
    ProgressBar pBar;
    ImageView iv_sel_color;
    String selectedColor="";

    public ColorPickerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_colorpicker, container, false);
        mColorPickerView = (ColorPickerView) view.findViewById(R.id.colorpickerview__color_picker_view);
        mColorPickerView.setOnColorChangedListener(this);
        iv_sel_color = (ImageView) view.findViewById(R.id.iv_sel_color);

        selectedColor = ((PersonalizeToolActivity)getActivity()).getSelectedColor();
        if(selectedColor != null){
            iv_sel_color.setBackgroundColor(Integer.parseInt(selectedColor));
            iv_sel_color.setSelected(true);
        }

        view.findViewById(R.id.pspd_txt_getprice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PersonalizeToolActivity)getActivity()).setSelectedColor(selectedColor);
                onButtonPressed("6",true);
            }
        });
        view.findViewById(R.id.psc_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.gc();
                Runtime.getRuntime().gc();
//                ((PersonalizeToolActivity)getActivity()).setSelectedColor(null);
//                Intent i = new Intent(getActivity(), PesrsnalizeProductActivity.class);
//                getActivity().startActivityForResult(i, Common.captureRequestCode);
//                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                onButtonPressed("4",false);
            }
        });
        return view;
    }

    @Override
    public void onColorChanged(int i) {
        iv_sel_color.setBackgroundColor(i);
        selectedColor = Integer.toString(i);
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
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        Runtime.getRuntime().gc();
    }

}
