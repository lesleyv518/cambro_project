package com.cambro.app.fragment;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cambro.app.R;
import com.cambro.app.interfce.Common;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.utils.Utils;
import com.cambro.app.utils.image.ImageLoader;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CamrackFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    ImageView cr_iv_stemware_down, cr_iv_cup_down, cr_iv_dinnerware_down;
    TextView cr_txt_error, cr_txt_prod, cr_txt_prdadd;
    EditText cr_edt_qty, cr_edt_h, cr_edt_d, cr_edt_name, cr_edt_email, cr_edt_phone, cr_edt_postal, cr_edt_country;
    String product_type = "stemware";
    String quoteEmailBody = "";
    Float unit = 1.0f;
    String diameter, height, qty;
    ImageLoader imgLoader = new ImageLoader(getActivity());
    NumberFormat formatter = NumberFormat.getInstance(Locale.US);
    LinearLayout cr_ll_prod, cr_ll_sq;
    ScrollView cr_scv;

    public CamrackFragment() {
        // Required empty public constructor
    }

    public static CamrackFragment newInstance() {
        CamrackFragment f = new CamrackFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.fragment_camrack, container, false);
        cr_iv_stemware_down = (ImageView) rootview.findViewById(R.id.cr_iv_stemware_down);
        cr_iv_cup_down = (ImageView) rootview.findViewById(R.id.cr_iv_cup_down);
        cr_iv_dinnerware_down = (ImageView) rootview.findViewById(R.id.cr_iv_dinnerware_down);
        cr_edt_h = (EditText) rootview.findViewById(R.id.cr_edt_h);
        cr_edt_d = (EditText) rootview.findViewById(R.id.cr_edt_d);
        cr_edt_qty = (EditText) rootview.findViewById(R.id.cr_edt_qty);
        cr_txt_error = (TextView) rootview.findViewById(R.id.cr_txt_error);
        cr_txt_prod = (TextView) rootview.findViewById(R.id.cr_txt_prod);
        cr_txt_prdadd = (TextView) rootview.findViewById(R.id.cr_txt_prdadd);
        cr_ll_prod = (LinearLayout) rootview.findViewById(R.id.cr_ll_prod);
        cr_ll_sq = (LinearLayout) rootview.findViewById(R.id.cr_ll_sq);

        cr_edt_name = (EditText) rootview.findViewById(R.id.cr_edt_name);
        cr_edt_email = (EditText) rootview.findViewById(R.id.cr_edt_email);
        cr_edt_phone = (EditText) rootview.findViewById(R.id.cr_edt_phone);
        cr_edt_postal = (EditText) rootview.findViewById(R.id.cr_edt_postal);
        cr_edt_country = (EditText) rootview.findViewById(R.id.cr_edt_country);

        cr_scv = (ScrollView) rootview.findViewById(R.id.cr_scv);

        rootview.findViewById(R.id.cr_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quoteEmailBody = "";
                resetInputFields();
                product_type = "stemware";
                onButtonPressed("4", false);
            }
        });
        rootview.findViewById(R.id.cr_ll_stemware).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_type = "stemware";
                cr_iv_stemware_down.setImageDrawable(getResources().getDrawable(R.drawable.radio_on));
                cr_iv_cup_down.setImageDrawable(getResources().getDrawable(R.drawable.radio_off));
                cr_iv_dinnerware_down.setImageDrawable(getResources().getDrawable(R.drawable.radio_off));
            }
        });
        rootview.findViewById(R.id.cr_ll_cup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_type = "mugs";
                cr_iv_stemware_down.setImageDrawable(getResources().getDrawable(R.drawable.radio_off));
                cr_iv_cup_down.setImageDrawable(getResources().getDrawable(R.drawable.radio_on));
                cr_iv_dinnerware_down.setImageDrawable(getResources().getDrawable(R.drawable.radio_off));
            }
        });
        rootview.findViewById(R.id.cr_ll_dinnerware).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_type = "dinnerware";
                cr_iv_stemware_down.setImageDrawable(getResources().getDrawable(R.drawable.radio_off));
                cr_iv_cup_down.setImageDrawable(getResources().getDrawable(R.drawable.radio_off));
                cr_iv_dinnerware_down.setImageDrawable(getResources().getDrawable(R.drawable.radio_on));
            }
        });

        rootview.findViewById(R.id.cr_btn_fmc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cr_txt_error.setText("");
                findProduct();
            }
        });
        ((Spinner)rootview.findViewById(R.id.cr_spn_unit)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1)
                    unit = 1/ 2.54f;
                else
                    unit = 1.0f;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        rootview.findViewById(R.id.cr_txt_sq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rootview.findViewById(R.id.cr_ll_quote).getVisibility() == View.VISIBLE)
                {
                    sendQuoteEmail();
                }
                else
                {
                    rootview.findViewById(R.id.cr_ll_quote).setVisibility(View.VISIBLE);
                    rootview.findViewById(R.id.cr_ll_input).setVisibility(View.GONE);
                    ParseUser user = ParseUser.getCurrentUser();
                    if (user != null) {
                        ((EditText) rootview.findViewById(R.id.cr_edt_name)).setText(user.getString("name"));
                        ((EditText) rootview.findViewById(R.id.cr_edt_email)).setText(user.getEmail());
                    }
                }
            }
        });

        return rootview;
    }

    private void sendQuoteEmail() {

        String mailBody = "";
        mailBody = " Selected Camracks: \n" + quoteEmailBody + "\n\n"
                + " Name: " + cr_edt_name.getText().toString() + "\n"
                + " Email: " + cr_edt_email.getText().toString() + "\n"
                + " Phone: " + cr_edt_phone.getText().toString() + "\n"
                + " Postal Code: " + cr_edt_postal.getText().toString() + "\n"
                + " Country: " + cr_edt_country.getText().toString();

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{Common.EmailOfCambro});
        i.putExtra(Intent.EXTRA_SUBJECT, "Camracks");
        i.putExtra(Intent.EXTRA_TEXT, mailBody);

        getActivity().startActivity(Intent.createChooser(i, "Send Email"));
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private boolean checkInputItems() {
        height = cr_edt_h.getText().toString();
        diameter = cr_edt_d.getText().toString();
        qty = cr_edt_qty.getText().toString();
        if (height.length() == 0 || diameter.length() == 0 || qty.length() == 0)
        {
            cr_txt_error.setText("");
            if (height.length() == 0) {
                cr_txt_error.setVisibility(View.VISIBLE);
                cr_txt_error.setText(cr_txt_error.getText() + "\n" + getString(R.string.cr_txt_peah));
            }
            if (diameter.length() == 0) {
                cr_txt_error.setVisibility(View.VISIBLE);
                cr_txt_error.setText(cr_txt_error.getText() + "\n" + getString(R.string.cr_txt_pead));
            }
            if (qty.length() == 0) {
                cr_txt_error.setVisibility(View.VISIBLE);
                cr_txt_error.setText(cr_txt_error.getText() + "\n" + getString(R.string.cr_txt_peaq));
            }
            //cr_scv.scrollTo(0, cr_scv.getBottom());
            cr_scv.post(new Runnable() {

                @Override
                public void run() {
                    cr_scv.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });

            return false;
        }

        return true;
    }

    private void findProduct() {
        if (!checkInputItems()) return;

        final ProgressDialog dlg = new ProgressDialog(getActivity());
        dlg.show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("CamrackProductList");
        query.whereEqualTo("prodtype", product_type);
        query.whereLessThan("minH", Float.parseFloat(height) * unit);
        query.whereGreaterThan("maxH", Float.parseFloat(height) * unit);
        query.whereLessThan("minD", Float.parseFloat(diameter) * unit);
        query.whereGreaterThan("maxD", Float.parseFloat(diameter) * unit);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if (list.size() > 0) {

                        ParseObject prd = list.get(0);
                        addProduct(prd);
                        cr_txt_prdadd.setText(getResources().getString(R.string.cr_txt_prdadd));
                        cr_ll_sq.setVisibility(View.VISIBLE);
                    } else
                        cr_txt_error.setText(getResources().getString(R.string.cr_txt_notfoundprd));
                } else {
                }
                dlg.dismiss();
                resetInputFields();
                cr_scv.post(new Runnable() {

                    @Override
                    public void run() {
                        cr_scv.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
            }
        });

    }

    private void resetInputFields()
    {
        height = "";
        diameter = "";
        qty = "";
        cr_edt_h.setText("");
        cr_edt_d.setText("");
        cr_edt_qty.setText("");
    }

    private void addProduct(ParseObject prd) {
        LinearLayout layout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView imageView = new ImageView(getActivity());
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        imageView.setLayoutParams(params1);
        imageView.setScaleType(ImageView.ScaleType.FIT_START);

        String imgUrl = prd.getString("imgname");
        imgLoader.DisplayImage(Common.cambroProdImageUrl + imgUrl, imageView);

        TextView textView = new TextView(getActivity());
        LinearLayout.LayoutParams paramsTxt = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 4.0f);
        textView.setLayoutParams(paramsTxt);
        textView.setPadding(16, 0, 0, 0);

        String sHtml = "<h1>" + prd.getString("prodnumber") + "</h1>"
                + "A total quantity of <font color='#FF0000'>&nbsp;" + camrackAmountString(prd) + "</font>"
                + " will be needed for your specified "
                + formatter.format(Integer.parseInt(qty))
                + " " + product_type
                + " " + diameter + " in diameter, "
                + height + " in height. <br/>"
                + "<font color='#FF0000'>&nbsp;" + prd.getString("buildrecipe") + "</font>";

        String sMail = prd.getString("prodnumber") + "\n"
                + "A total quantity of " + camrackAmountString(prd)
                + " will be needed for your specified "
                + formatter.format(Integer.parseInt(qty))
                + " " + product_type
                + " " + diameter + " in diameter, "
                + height + " in height. \n"
                + prd.getString("buildrecipe") + "\n\n";

        quoteEmailBody += sMail;
        textView.setText(Html.fromHtml(sHtml));
        layout.addView(imageView, 0);
        layout.addView(textView, 1);
        cr_ll_prod.addView(layout);
    }

    private String camrackAmountString(ParseObject prd) {
        int q = Integer.parseInt(qty);
        int camrack_count = (int) Math.ceil(q / prd.getDouble("compart"));
        return formatter.format(camrack_count) + " Full Size " + prd.getString("prodnumber") + " Camracks";
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
