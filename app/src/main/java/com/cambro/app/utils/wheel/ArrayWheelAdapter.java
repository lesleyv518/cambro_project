package com.cambro.app.utils.wheel;

import android.content.Context;

import com.cambro.app.model.FresFit;

import java.util.List;

public class ArrayWheelAdapter<T> extends AbstractWheelTextAdapter {
	   
    // items
    private T items[];
    private List<FresFit> lstFresh;

    /**
     * Constructor
     * @param context the current context
     * @param items the items
     */
    public ArrayWheelAdapter(Context context, T items[]) {
        super(context);
       
        //setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
        this.items = items;
    }

    public ArrayWheelAdapter(Context context, List<FresFit> listFresh) {
        super(context);

        //setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
        this.lstFresh = listFresh;
    }
   
    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < items.length) {
            T item = items[index];
            if (item instanceof CharSequence) {
                return (CharSequence) item;
            }
            return item.toString();
        }
        return null;
    }

    @Override
    protected FresFit getItemClass(int index) {
        if (index >= 0 && index < lstFresh.size()) {
            FresFit item = lstFresh.get(index);
            if (item instanceof FresFit) {
                return item;
            }
            return item;
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        if (lstFresh.size() > 0)
            return lstFresh.size();
        else
            return items.length;
    }
}

