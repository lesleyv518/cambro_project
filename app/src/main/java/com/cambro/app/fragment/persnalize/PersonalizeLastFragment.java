package com.cambro.app.fragment.persnalize;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cambro.app.PersonalizeToolActivity;
import com.cambro.app.R;
import com.cambro.app.fragment.fitfactory.GMailSender;
import com.cambro.app.interfce.Common;
import com.cambro.app.interfce.SocialLoginService;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.interfce.Reg;
import com.cambro.app.utils.Utils;
import com.facebook.login.LoginManager;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalizeLastFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private String category;
    private String productCode;
    private String dimension;
    private ImageView pslp_iv_prd;
    private TextView pslp_txt_gp,pstgp_txt_category;
    private LinearLayout pslp_ll_prdinfo, pslp_ll_userinfo;
    private String firstname = "", lastname = "", email = "";
    private EditText pslp_edt_firstname, pslp_edt_lastname, pslp_edt_email, pslp_edt_count;
    LoginManager manager;

    TextView pslp_txt_prdname;
    ImageView pslp_iv_fb_share, pslp_iv_email_blue, pslp_iv_download;
    private String tray_count;

    public PersonalizeLastFragment() {
        // Required empty public constructor
    }

    public static PersonalizeLastFragment newInstance() {
        PersonalizeLastFragment f = new PersonalizeLastFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_persnalize_last_product, container, false);
        category = ((PersonalizeToolActivity) getActivity()).getPsCategory().getCategory();
        productCode = ((PersonalizeToolActivity) getActivity()).getProductCode();
        dimension = ((PersonalizeToolActivity) getActivity()).getDimension();

        pslp_txt_prdname = (TextView) v.findViewById(R.id.pslp_txt_prdname);
        pslp_iv_prd = (ImageView) v.findViewById(R.id.pslp_iv_prd);
        pslp_txt_gp = (TextView) v.findViewById(R.id.pslp_txt_gp);
        pslp_ll_userinfo = (LinearLayout) v.findViewById(R.id.pslp_ll_userinfo);
        pslp_ll_prdinfo = (LinearLayout) v.findViewById(R.id.pslp_ll_prdinfo);

        pslp_edt_email = (EditText) v.findViewById(R.id.pslp_edt_email);
        pslp_edt_firstname = (EditText) v.findViewById(R.id.pslp_edt_firstname);
        pslp_edt_lastname = (EditText) v.findViewById(R.id.pslp_edt_lastname);
        pslp_edt_count = (EditText) v.findViewById(R.id.pslp_edt_count);

//        pslp_txt_prdname.setText("CUSTOM " + category.toUpperCase());
//        pslp_txt_prdname.setSelected(true);
        pslp_iv_prd.setImageBitmap(Reg.ptLastImage);

        pstgp_txt_category = (TextView) v.findViewById(R.id.pstgp_txt_category);

        v.findViewById(R.id.pslp_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pslp_txt_gp.getText().toString().equals(getString(R.string.ps_get_pricing))) {
                    System.gc();
                    Runtime.getRuntime().gc();
                    if (Reg.ptKind.equals("tray"))
                        onButtonPressed("8", false);
                    else
                        onButtonPressed("6", false);
                } else if (pslp_txt_gp.getText().toString().equals(getString(R.string.submit))) {
                    pslp_txt_prdname.setVisibility(View.VISIBLE);
                    pstgp_txt_category.setText(getString(R.string.ps_txt_yct));
                    pslp_txt_gp.setText(getString(R.string.ps_get_pricing));
                    pslp_txt_gp.setVisibility(View.VISIBLE);
                    pslp_ll_prdinfo.setVisibility(View.VISIBLE);
                    pslp_ll_userinfo.setVisibility(View.GONE);
                }

            }
        });

        v.findViewById(R.id.pslp_txt_gp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pslp_txt_gp.getText().toString().equals(getString(R.string.ps_get_pricing))) {

                    final ParseUser user = ParseUser.getCurrentUser();
                    if (user != null || Reg.facebookId != null) {
                        firstname = user.get("name").toString().split(" ")[0];
                        lastname = user.get("name").toString().split(" ")[1];
                        email = user.getEmail();
                        if (email != null && email.length() > 0)pslp_edt_email.setText(email);
                        pslp_edt_firstname.setText(firstname);
                        pslp_edt_lastname.setText(lastname);
                    }
                    pstgp_txt_category.setText(getString(R.string.ps_txt_fqfyd));
                    pslp_ll_prdinfo.setVisibility(View.GONE);
                    pslp_ll_userinfo.setVisibility(View.VISIBLE);
                    pslp_txt_gp.setText(getString(R.string.submit));
                } else if (pslp_txt_gp.getText().toString().equals(getString(R.string.submit))) {
                    if (!checkInputItems()) return;
                    pslp_txt_prdname.setVisibility(View.INVISIBLE);
                    pslp_txt_gp.setVisibility(View.INVISIBLE);
                    pslp_ll_prdinfo.setVisibility(View.VISIBLE);
                    pslp_ll_userinfo.setVisibility(View.GONE);
                    new SendMailProcess().execute();
                    onButtonPressed("7", true);
                }
            }
        });

//        pslp_iv_fb_share = (ImageView) v.findViewById(R.id.pslp_iv_fb_share);
//        pslp_iv_fb_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fbSharePhoto();
//            }
//        });
//
//        pslp_iv_email_blue = (ImageView) v.findViewById(R.id.pslp_iv_email_blue);
//        pslp_iv_email_blue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sendDefaultEmail();
//            }
//        });
//        pslp_iv_download = (ImageView) v.findViewById(R.id.pslp_iv_download);
//        pslp_iv_download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                saveImageToInternalStorage(Reg.ptLastImage);
//            }
//        });
        pslp_iv_fb_share = (ImageView) v.findViewById(R.id.pslp_iv_fb_share);
        pslp_iv_fb_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils utils = new Utils();
                utils.fbSharePhoto(getActivity(), PersonalizeLastFragment.this);
            }
        });

        pslp_iv_email_blue = (ImageView) v.findViewById(R.id.pslp_iv_email_blue);
        pslp_iv_email_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils utils = new Utils();
                utils.sendDefaultEmail(getActivity(), category, email);
            }
        });
        pslp_iv_download = (ImageView) v.findViewById(R.id.pslp_iv_download);
        pslp_iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils utils = new Utils();
                utils.saveImageToInternalStorage(Reg.ptLastImage);
                Toast.makeText(getActivity(), "Successfully saved.", Toast.LENGTH_LONG).show();
            }
        });


        return v;
    }

    private void sendDefaultEmail()
    {
        File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "cambro_product.jpg");
        Uri URI = Uri.fromFile(filelocation);

        try {
//            email = "";
            String subject = "Personalize Tool";
            String body = "";

            body += "Product Name: " + category.toUpperCase() + " TRAY\n";
            body += "Product Code: " + ((PersonalizeToolActivity) getActivity()).getProductCode() + "\n";
            body += "Size: " + ((PersonalizeToolActivity) getActivity()).getSize() + "\n";
            body += "Dimension: " + ((PersonalizeToolActivity) getActivity()).getDimension() + "\n";

            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
            if (URI != null) {
                emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
            }
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(emailIntent,"Sending email..."));
        } catch (Throwable t) {
            Toast.makeText(getActivity(),"Request failed try again: " + t.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private void fbSharePhoto()
    {
        ParseUser user = ParseUser.getCurrentUser();
        if(user == null)
        {
            SocialLoginService fbService = SocialLoginService.getInstance(getActivity());
            fbService.facebookLogin(getActivity(), PersonalizeLastFragment.this);
        }
        else
        {
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(Reg.ptLastImage)
                    .setCaption("Welcome To Facebook Photo Sharing on Cambro Product!")
                    .build();

            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();
            ShareDialog shareDialog = new ShareDialog(this);
            shareDialog.show(this, content);
        }
    }

    public boolean saveImageToInternalStorage(Bitmap image) {

        try {
            File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "cambro_tray_product.jpg");
            try {
                OutputStream fOut = null;
                fOut = new FileOutputStream(filelocation);
                image.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
                fOut.flush();
                fOut.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        } catch (Exception e) {
            Log.e("file Save", e.toString());
            return false;
        }
    }

    private class SendMailProcess extends AsyncTask {
        @Override

        protected Object doInBackground(Object... arg0) {

            sendEmail();
            return null;
        }
    }

    private void sendEmail() {
        try {
            String body = "";
            body += "Name: " + firstname + " " + lastname + "\n";
            body += "Email: " + email + "\n";
            body += "Product Name: " + category + "\n";
            body += "Product Code: " + ((PersonalizeToolActivity) getActivity()).getProductCode() + "\n";
            body += "Size: " + ((PersonalizeToolActivity) getActivity()).getSize() + "\n";
            body += "Dimension: " + ((PersonalizeToolActivity) getActivity()).getDimension() + "\n";
            body += "Quantity: " + tray_count + "\n";

            File fileCapture = null;
            File f1 = new File(Environment.getExternalStorageDirectory().toString().trim());
            for (File temp : f1.listFiles()) {
                if (temp.getName().equals("cambro_product.jpg")) {
                    fileCapture = temp;
                    break;
                }
            }
            GMailSender sender = new GMailSender(Common.cambroEmail, Common.cambroEmailPassword);
            sender.sendMail("Personalize Tool",
                    body,
                    email,
                    Common.cramosEmail,
                    fileCapture);
        } catch (Exception e) {
            Log.e("send mail", e.toString());
        }

    }

    private boolean checkInputItems() {

        if (pslp_edt_firstname.getText().toString().length() == 0) {

            Toast.makeText(getActivity(), "Please input  your first name.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (pslp_edt_lastname.getText().toString().length() == 0) {

            Toast.makeText(getActivity(), "Please input  your last name.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (pslp_edt_email.getText().toString().length() == 0) {

            Toast.makeText(getActivity(), "Please input  your email address.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (pslp_edt_count.getText().toString().length() == 0) {

            Toast.makeText(getActivity(), "Please input  tray count.", Toast.LENGTH_LONG).show();
            return false;
        }
        firstname = pslp_edt_firstname.getText().toString();
        lastname = pslp_edt_lastname.getText().toString();
        email = pslp_edt_email.getText().toString();
        tray_count = pslp_edt_count.getText().toString();
        return true;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

}
