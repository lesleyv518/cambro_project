package com.cambro.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cambro.app.R;
import com.cambro.app.TransportActivity;
import com.cambro.app.fragment.transport.TransportProductFragment;
import com.cambro.app.model.Transport;
import com.cambro.app.utils.image.ImageLoader;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class TransportListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    private List<Transport> lstTpPrd = null;
    private ArrayList<Transport> arraylist;
    Transport selectedPrd;
    ImageLoader imgLoader;
    TransportProductFragment fragment;

    public TransportListViewAdapter(Context context, TransportProductFragment lidFragment, List<Transport> lstFresh) {
        this.context = context;
        this.lstTpPrd = lstFresh;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<Transport>();
        this.arraylist.addAll(lstFresh);
        imgLoader = new ImageLoader(context);
        fragment = lidFragment;
    }

    public class ViewHolder {

        TextView lst_txt_tp_prd, lst_txt_tp_prdcode;
        ImageView lst_iv_tp_prd;
        LinearLayout lst_ll_tp;
    }

    @Override
    public int getCount() {
        return lstTpPrd.size();
    }

    @Override
    public Object getItem(int position) {
        return lstTpPrd.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();

            view = inflater.inflate(R.layout.list_tp_cell, parent, false);
            holder.lst_ll_tp = (LinearLayout) view.findViewById(R.id.lst_ll_tp);
            holder.lst_iv_tp_prd = (ImageView) view.findViewById(R.id.lst_iv_tp_prd);
            holder.lst_txt_tp_prdcode = (TextView) view.findViewById(R.id.lst_txt_tp_prdcode);
            holder.lst_txt_tp_prd = (TextView) view.findViewById(R.id.lst_txt_tp_prd);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        selectedPrd = lstTpPrd.get(position);
        String imgName = "tp_prd_" + selectedPrd.getProductImageName().toLowerCase().trim().replace(" ", "_");
        try {
            Drawable drawable = context.getResources().getDrawable(context.getResources().getIdentifier(imgName, "drawable", context.getPackageName()));
            holder.lst_iv_tp_prd.setImageDrawable(drawable);
        }catch (Exception e)
        {
            holder.lst_iv_tp_prd.setImageDrawable(context.getResources().getDrawable(R.drawable.trans));
        }
        holder.lst_txt_tp_prdcode.setText(selectedPrd.getProductCode());
        holder.lst_txt_tp_prd.setText(selectedPrd.getProductDescription());
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ((TransportActivity) context).setTransport(lstTpPrd.get(position));
                ((TransportActivity)context).setTPTitle(6);
                Bitmap bitmap = ((BitmapDrawable)holder.lst_iv_tp_prd.getDrawable()).getBitmap();
                saveImageToInternalStorage(bitmap);
                fragment.onButtonPressed("6", true);
            }
        });
        return view;
    }

    public boolean saveImageToInternalStorage(Bitmap image) {

        try {
            File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "cambro_trans_product.jpg");
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

}
