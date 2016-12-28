package com.cambro.app.fragment.persnalize;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cambro.app.PersonalizeToolActivity;
import com.cambro.app.R;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.interfce.Reg;
import com.cambro.app.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalizeProductDetailFragment extends Fragment {

    String category;
    private File fileCapture;
    private File fileSelected;
    protected static final int SELECT_FILE = 1;
    protected static final int REQUEST_CAMERA = 0;
    private Uri imageUri;
    private OnFragmentInteractionListener mListener;
    ImageView psd_iv_logo, pspd_iv_image, pspd_iv_category;
    LinearLayout ps_ll_colorpicker;
    TextView pspd_txt_getprice, ps_txt_addlogo;

    public PersonalizeProductDetailFragment() {
        // Required empty public constructor
    }

    public static PersonalizeProductDetailFragment newInstance() {
        PersonalizeProductDetailFragment f = new PersonalizeProductDetailFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_persnalize_prd_detail, container, false);

        category = ((PersonalizeToolActivity)getActivity()).getPsCategory().getCategory();
        initialLoad(v);

        return v;
    }

    private void initialLoad(View v)
    {
        v.findViewById(R.id.pspd_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.gc();
                Runtime.getRuntime().gc();
                onButtonPressed("1", false);
            }
        });
        psd_iv_logo = (ImageView) v.findViewById(R.id.psd_iv_logo);
        pspd_iv_category = (ImageView) v.findViewById(R.id.pspd_iv_category);
        pspd_iv_category.setImageDrawable(getResources().getDrawable(R.drawable.trans));
        pspd_iv_image = (ImageView) v.findViewById(R.id.pspd_iv_image);
        ps_ll_colorpicker = (LinearLayout) v.findViewById(R.id.ps_ll_colorpicker);
        pspd_txt_getprice = (TextView) v.findViewById(R.id.pspd_txt_getprice);
        ps_txt_addlogo = (TextView) v.findViewById(R.id.ps_txt_addlogo);
        pspd_txt_getprice = (TextView) v.findViewById(R.id.pspd_txt_getprice);

        pspd_txt_getprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed("6", true);
            }
        });

        v.findViewById(R.id.psd_iv_logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogToPromptForImage();
            }
        });
        ps_ll_colorpicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed("5", true);
            }
        });
        ((TextView)v.findViewById(R.id.pspd_txt_category)).setText(category);
        String imgName = "ps_" + ((PersonalizeToolActivity)getActivity()).getPsCategory().getCategory().toLowerCase().trim().replace(" ", "_") + "_table";
        try {
            Drawable drawable = getResources().getDrawable(getResources().getIdentifier(imgName, "drawable", getActivity().getPackageName()));
            pspd_iv_category.setImageDrawable(drawable);
        }catch (Exception e)
        {}
        v.refreshDrawableState();
    }

    private void dialogToPromptForImage() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.video_choose_dialog);
        dialog.findViewById(R.id.btn_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileCapture = null;
                fileSelected = new File(android.os.Environment.getExternalStorageDirectory(), "cambro_personalize.jpg");
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.btn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                takePhoto();
            }
        });
        dialog.show();
    }

    public void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStorageDirectory(), "cambro_personalize.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, REQUEST_CAMERA);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int reqWidth = 480;
        int reqHeight = 480;
        Bitmap scaledBitmap = null;
        File fileSelected = null;
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                File f = new File(Environment.getExternalStorageDirectory().toString().trim());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("cambro_personalize.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bm;
                    BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                    String strProfileImagePath = f.getAbsolutePath();
                    btmapOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(f.getAbsolutePath(), btmapOptions);

                    btmapOptions.inSampleSize = Utils.calculateInSampleSize(btmapOptions, reqWidth, reqHeight);
                    btmapOptions.inJustDecodeBounds = false;
                    bm = BitmapFactory.decodeFile(f.getAbsolutePath(), btmapOptions);
                    scaledBitmap = Bitmap.createScaledBitmap(bm, bm.getWidth(), bm.getHeight(), true);

                    OutputStream fOut = null;
                    File f1 = new File(Environment.getExternalStorageDirectory().toString().trim());
                    File fileCapture = null;
                    for (File temp : f1.listFiles()) {
                        if (temp.getName().equals("cambro_personalize.jpg")) {
                            fileCapture = temp;
                            break;
                        }
                    }
                    try {
                        fOut = new FileOutputStream(fileCapture);
                        bm.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
                        fOut.flush();
                        fOut.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    f.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String strProfileImagePath = Utils.getPath(selectedImageUri, getActivity());
                Bitmap bm;
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();

                btmapOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(strProfileImagePath, btmapOptions);

                btmapOptions.inSampleSize = Utils.calculateInSampleSize(btmapOptions, reqWidth, reqHeight);
                btmapOptions.inJustDecodeBounds = false;

                bm = BitmapFactory.decodeFile(strProfileImagePath, btmapOptions);
                scaledBitmap = Bitmap.createScaledBitmap(bm, bm.getWidth(), bm.getHeight(), true);
                ///////////
                OutputStream fOut = null;
                File f1 = new File(Environment.getExternalStorageDirectory()
                        .toString().trim());
                for (File temp : f1.listFiles()) {
                    if (temp.getName().equals("cambro_personalize.jpg")) {
                        fileSelected = temp;
                        break;
                    }
                }
                try {
                    fOut = new FileOutputStream(fileSelected);
                    bm.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                f1.delete();
            }
            Reg.ptCapturedImage = scaledBitmap;
            psd_iv_logo.setImageBitmap(scaledBitmap);
            ps_ll_colorpicker.setVisibility(View.VISIBLE);
            pspd_txt_getprice.setVisibility(View.VISIBLE);
            pspd_iv_image.setVisibility(View.VISIBLE);
            ps_txt_addlogo.setText("");
            //onButtonPressed("8", true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        Runtime.getRuntime().gc();
    }
}
