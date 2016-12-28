package com.cambro.app.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cambro.app.PersonalizeToolActivity;
import com.cambro.app.R;
import com.cambro.app.fragment.persnalize.PersonalizeCategoryFragment;
import com.cambro.app.model.PersonalizeCategory;
import com.cambro.app.utils.image.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class PersonalizeCategoryAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    private List<PersonalizeCategory> lstCategory = null;
    private ArrayList<PersonalizeCategory> arraylist;
    PersonalizeCategory selectedCategory;
    ImageLoader imgLoader;
    PersonalizeCategoryFragment fragment;

    public PersonalizeCategoryAdapter(Context context, PersonalizeCategoryFragment lidFragment, List<PersonalizeCategory> lstCat) {
        this.context = context;
        this.lstCategory = lstCat;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<PersonalizeCategory>();
        this.arraylist.addAll(lstCat);
        imgLoader = new ImageLoader(context);
        fragment = lidFragment;
    }

    public class ViewHolder {

        TextView lst_txt_category;
        ImageView lst_iv_round;
    }

    @Override
    public int getCount() {
        return lstCategory.size();
    }

    @Override
    public Object getItem(int position) {
        return lstCategory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();

            view = inflater.inflate(R.layout.list_ps_cell, parent, false);
            holder.lst_txt_category = (TextView) view.findViewById(R.id.lst_txt_category);
            holder.lst_iv_round = (ImageView) view.findViewById(R.id.lst_iv_round);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        selectedCategory = lstCategory.get(position);
        String imgName = "ps_" + selectedCategory.getCategory().toLowerCase().trim().replace(" ", "_") + "_tray_table";

        try {
            Drawable drawable = context.getResources().getDrawable(context.getResources()
                    .getIdentifier(imgName, "drawable", context.getPackageName()));
            holder.lst_iv_round.setImageDrawable(drawable);
        }catch (Exception e)
        {
            holder.lst_iv_round.setImageDrawable(context.getResources().getDrawable(R.drawable.trans));
        }

        holder.lst_txt_category.setText(selectedCategory.getCategory().toUpperCase());
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ((PersonalizeToolActivity) context).setPsCategory(lstCategory.get(position));
                if (selectedCategory.getKind().equals("tray"))
                    fragment.onButtonPressed("3", true);
                else
                    fragment.onButtonPressed("4", true);
            }
        });
        return view;
    }

}
