package com.cambro.app.adapter;

import java.util.ArrayList;
import java.util.List;

import com.cambro.app.R;
import com.cambro.app.FitFactoryActivity;
import com.cambro.app.fragment.fitfactory.FitFactoryLidFragment;
import com.cambro.app.model.FresFit;
import com.cambro.app.utils.image.ImageLoader;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FitFacotyLidListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    private List<FresFit> lstLids = null;
    private ArrayList<FresFit> arraylist;
    FresFit selectedLid;
    ImageLoader imgLoader;
    FitFactoryLidFragment fragment;

    public FitFacotyLidListViewAdapter(Context context, FitFactoryLidFragment lidFragment, List<FresFit> lstFresh) {
        this.context = context;
        this.lstLids = lstFresh;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<FresFit>();
        this.arraylist.addAll(lstFresh);
        imgLoader = new ImageLoader(context);
        fragment = lidFragment;
    }

    public class ViewHolder {

        TextView ffr_lst_txt_lidcode,ffr_lst_txt_description;
        ImageView ffr_lst_iv_lid;
        LinearLayout ffr_lst_ll;
    }

    @Override
    public int getCount() {
        return lstLids.size();
    }

    @Override
    public Object getItem(int position) {
        return lstLids.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();

            view = inflater.inflate(R.layout.list_ff_cell, parent, false);
            holder.ffr_lst_ll = (LinearLayout) view.findViewById(R.id.ffr_lst_ll);
            holder.ffr_lst_iv_lid = (ImageView) view.findViewById(R.id.ffr_lst_iv_lid);
            holder.ffr_lst_txt_lidcode = (TextView) view.findViewById(R.id.ffr_lst_txt_lidcode);
            holder.ffr_lst_txt_description = (TextView) view.findViewById(R.id.ffr_lst_txt_description);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        selectedLid = lstLids.get(position);
        String imgName = "fitfactory_" + selectedLid.getLidCode().toLowerCase().trim().replace(" ", "_");
        try {
            Drawable drawable = context.getResources().getDrawable(context.getResources()
                    .getIdentifier(imgName, "drawable", context.getPackageName()));
            holder.ffr_lst_iv_lid.setImageDrawable(drawable);
        }catch (Exception e)
        {
            holder.ffr_lst_iv_lid.setImageDrawable(context.getResources().getDrawable(R.drawable.trans));
        }

        holder.ffr_lst_txt_lidcode.setText("Cambro " + selectedLid.getLidCode());
        holder.ffr_lst_txt_description.setText(selectedLid.getLidDescription());
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ((FitFactoryActivity) context).setSelectedLid(lstLids.get(position));
                fragment.onButtonPressed("5", true);
            }
        });
        return view;
    }

    private void loadImages(ParseFile thumbnail, final ImageView img) {

        if (thumbnail != null) {
            thumbnail.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (e == null) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                        img.setImageBitmap(bmp);
                    } else {
                    }
                }
            });
        } else {
            img.setImageResource(R.drawable.trans);
        }
    }

}
