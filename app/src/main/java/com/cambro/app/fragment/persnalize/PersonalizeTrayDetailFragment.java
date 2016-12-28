package com.cambro.app.fragment.persnalize;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import com.cambro.app.SocialActivity;
import com.cambro.app.interfce.Common;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.interfce.Reg;
import com.cambro.app.utils.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalizeTrayDetailFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private String category;
    private LinearLayout pstd_ll_europe;
    private ImageView pstd_iv_category, pstd_iv_category_top;
    private Uri imageUri;
    private File fileCapture;
    private File fileSelected;

    protected static final int SELECT_FILE = 1;
    protected static final int REQUEST_CAMERA = 0;

    public PersonalizeTrayDetailFragment() {
        // Required empty public constructor
    }

    public static PersonalizeTrayDetailFragment newInstance() {
        PersonalizeTrayDetailFragment f = new PersonalizeTrayDetailFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_persnalize_tray_detail, container, false);
        category = ((PersonalizeToolActivity) getActivity()).getPsCategory().getCategory();
        initialLoad(v);
        return v;
    }

    private void initialLoad(View v) {
        v.findViewById(R.id.pstd_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.gc();
                Runtime.getRuntime().gc();
                onButtonPressed("1", false);
            }
        });

        pstd_ll_europe = (LinearLayout) v.findViewById(R.id.pstd_ll_europe);
        pstd_ll_europe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SocialActivity.class);
                i.putExtra("social", 13);
                i.putExtra("link", Common.ptEuropePanUrl);
                i.putExtra("title", "EUROPEAN TRAYS");
                getActivity().startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        ((TextView) v.findViewById(R.id.pstd_txt_category)).setText(category.toUpperCase() + " TRAY");

        pstd_iv_category = (ImageView) v.findViewById(R.id.pstd_iv_category);
        pstd_iv_category_top = (ImageView) v.findViewById(R.id.pstd_iv_category_top);

        if (category.equals(Common.ptRound)) {
            pstd_ll_europe.setVisibility(View.GONE);
            pstd_iv_category.setImageDrawable(getResources().getDrawable(R.drawable.ps_round_tray));
            pstd_iv_category_top.setImageDrawable(getResources().getDrawable(R.drawable.ps_round_tray_outline));
        } else if (category.equals(Common.ptRect)) {
            pstd_ll_europe.setVisibility(View.VISIBLE);
            pstd_iv_category.setImageDrawable(getResources().getDrawable(R.drawable.ps_rect_tray));
            pstd_iv_category_top.setImageDrawable(getResources().getDrawable(R.drawable.ps_rect_tray_outline));
        } else if (category.equals(Common.ptTrapezoid)) {
            pstd_ll_europe.setVisibility(View.GONE);
            pstd_iv_category.setImageDrawable(getResources().getDrawable(R.drawable.ps_trapzoid_tray));
            pstd_iv_category_top.setImageDrawable(getResources().getDrawable(R.drawable.ps_trip_tray_outline));
        }

        pstd_iv_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogToPromtForVideo();
            }
        });
        String imgTray = "ps_" + ((PersonalizeToolActivity)getActivity()).getPsCategory().getCategory().toLowerCase().trim().replace(" ", "_");
        String imgOut = "ps_" + ((PersonalizeToolActivity)getActivity()).getPsCategory().getCategory().toLowerCase().trim().replace(" ", "_") + "_outline";
        try {
            Drawable drawable = getResources().getDrawable(getResources().getIdentifier(imgTray, "drawable", getActivity().getPackageName()));
            Drawable drawableOut = getResources().getDrawable(getResources().getIdentifier(imgOut, "drawable", getActivity().getPackageName()));
            pstd_iv_category.setImageDrawable(drawable);
            pstd_iv_category_top.setImageDrawable(drawableOut);
        }catch (Exception e)
        {}
        v.refreshDrawableState();

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

    private void dialogToPromtForVideo() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int reqWidth = 640;
        int reqHeight = 480;
        Bitmap scaledBitmap = null;
        Bitmap rotatedBitmap = null;
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

                    Matrix matrix = new Matrix();
                    matrix.setRotate(90, (float) scaledBitmap.getWidth(), (float) scaledBitmap.getHeight());
                    rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

                    OutputStream fOut = null;
//                    File f1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath());// + "/cambro_personalize.jpg");
                    fileCapture = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "cambro_personalize.jpg");
//                    File fileCapture = null;
//                    for (File temp : f1.listFiles()) {
//                        if (temp.getName().equals("cambro_personalize.jpg")) {
//                            fileCapture = temp;
//                            break;
//                        }
//                    }
                    try {

                        fOut = new FileOutputStream(fileCapture);
                        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
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
                rotatedBitmap = scaledBitmap;
                ///////////
                OutputStream fOut = null;
//                File f1 = new File(Environment.getExternalStorageDirectory().toString().trim());
                fileSelected = new File(Environment.getExternalStorageDirectory().getAbsolutePath() , "cambro_personalize.jpg");
//                for (File temp : f1.listFiles()) {
//                    if (temp.getName().equals("cambro_personalize.jpg")) {
//                        fileSelected = temp;
//                        break;
//                    }
//                }
                try {
                    fOut = new FileOutputStream(fileSelected);
                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                f1.delete();
            }
            Reg.ptCapturedImage = rotatedBitmap;
            onButtonPressed("8", true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        Runtime.getRuntime().gc();
    }
}
