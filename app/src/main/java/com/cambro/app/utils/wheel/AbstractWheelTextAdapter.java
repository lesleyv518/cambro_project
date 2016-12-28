package com.cambro.app.utils.wheel;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cambro.app.R;
import com.cambro.app.model.FresFit;
import com.cambro.app.utils.Utils;
import com.cambro.app.utils.image.ImageLoader;

/**
 * Abstract wheel adapter provides common functionality for adapters.
 */
public abstract class AbstractWheelTextAdapter extends AbstractWheelAdapter {

    /**
     * Text view resource. Used as a default view for adapter.
     */
    public static final int TEXT_VIEW_ITEM_RESOURCE = -1;

    /**
     * No resource constant.
     */
    protected static final int NO_RESOURCE = 0;

    /**
     * Default text color
     */
    public static final int DEFAULT_TEXT_COLOR = 0xFF101010;

    /**
     * Default text color
     */
    public static final int LABEL_COLOR = 0xFF700070;

    /**
     * Default text size
     */
    public static final int DEFAULT_TEXT_SIZE = 24;

    // Text settings
    private int textColor = DEFAULT_TEXT_COLOR;
    private int textSize = DEFAULT_TEXT_SIZE;

    // Current context
    protected Context context;
    // Layout inflater
    protected LayoutInflater inflater;

    // Items resources
    protected int itemResourceId;
    protected int itemTextResourceId;

    // Empty items resources
    protected int emptyItemResourceId;
    ImageLoader imgLoader;

    /**
     * Constructor
     *
     * @param context the current context
     */
    protected AbstractWheelTextAdapter(Context context) {
        this(context, TEXT_VIEW_ITEM_RESOURCE);
        imgLoader = new ImageLoader(context);
    }

    /**
     * Constructor
     *
     * @param context      the current context
     * @param itemResource the resource ID for a layout file containing a TextView to use when instantiating items views
     */
    protected AbstractWheelTextAdapter(Context context, int itemResource) {
        this(context, itemResource, NO_RESOURCE);
        imgLoader = new ImageLoader(context);
    }

    /**
     * Constructor
     *
     * @param context          the current context
     * @param itemResource     the resource ID for a layout file containing a TextView to use when instantiating items views
     * @param itemTextResource the resource ID for a text view in the item layout
     */
    protected AbstractWheelTextAdapter(Context context, int itemResource, int itemTextResource) {
        this.context = context;
        itemResourceId = itemResource;
        itemTextResourceId = itemTextResource;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imgLoader = new ImageLoader(context);
    }

    /**
     * Gets text color
     *
     * @return the text color
     */
    public int getTextColor() {
        return textColor;
    }

    /**
     * Sets text color
     *
     * @param textColor the text color to set
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    /**
     * Gets text size
     *
     * @return the text size
     */
    public int getTextSize() {
        return textSize;
    }

    /**
     * Sets text size
     *
     * @param textSize the text size to set
     */
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    /**
     * Gets resource Id for items views
     *
     * @return the item resource Id
     */
    public int getItemResource() {
        return itemResourceId;
    }

    /**
     * Sets resource Id for items views
     *
     * @param itemResourceId the resource Id to set
     */
    public void setItemResource(int itemResourceId) {
        this.itemResourceId = itemResourceId;
    }

    /**
     * Gets resource Id for text view in item layout
     *
     * @return the item text resource Id
     */
    public int getItemTextResource() {
        return itemTextResourceId;
    }

    /**
     * Sets resource Id for text view in item layout
     *
     * @param itemTextResourceId the item text resource Id to set
     */
    public void setItemTextResource(int itemTextResourceId) {
        this.itemTextResourceId = itemTextResourceId;
    }

    /**
     * Gets resource Id for empty items views
     *
     * @return the empty item resource Id
     */
    public int getEmptyItemResource() {
        return emptyItemResourceId;
    }

    /**
     * Sets resource Id for empty items views
     *
     * @param emptyItemResourceId the empty item resource Id to set
     */
    public void setEmptyItemResource(int emptyItemResourceId) {
        this.emptyItemResourceId = emptyItemResourceId;
    }


    /**
     * Returns text for specified item
     *
     * @param index the item index
     * @return the text of specified items
     */
    protected abstract CharSequence getItemText(int index);

    protected abstract FresFit getItemClass(int index);

    @Override
    public View getItem(int index, View view, ViewGroup parent) {
        if (index >= 0 && index < getItemsCount()) {

//            if (getItemClass(index) != null)
//            {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();

                view = inflater.inflate(R.layout.list_fruit_cell, parent, false);
                holder.iv_fv_img = (ImageView) view.findViewById(R.id.lst_iv_fruit);
                holder.txt_name = (TextView) view.findViewById(R.id.lst_txt_fruit);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            FresFit selectedFresh = getItemClass(index);
            holder.txt_name.setText(selectedFresh.getName());
            if (selectedFresh.getTxtColor() != null && selectedFresh.getTxtColor().equals("red"))
                holder.txt_name.setTextColor(Color.RED);

            if (selectedFresh.getImage() != null) {

                String s = selectedFresh.getImage().getName().toLowerCase().trim();
                Utils.loadFreshImageByDrawableName(context, holder.iv_fv_img, s);
                //imgLoader.DisplayImage(selectedFresh.getImage().getUrl(),holder.iv_fv_img);
                holder.iv_fv_img.invalidate();
            } else
            {
                holder.iv_fv_img.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0f));
                holder.txt_name.setPadding((int)context.getResources().getDimension(R.dimen.font_textview), 0, 0, 0);
            }

            return view;
        }
        return null;
    }

    public class ViewHolder {
        ImageView iv_fv_img;
        TextView txt_name;
    }

    @Override
    public View getEmptyItem(View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getView(emptyItemResourceId, parent);
        }
        if (emptyItemResourceId == TEXT_VIEW_ITEM_RESOURCE && convertView instanceof TextView) {
            configureTextView((TextView) convertView);
        }

        return convertView;
    }

    /**
     * Configures text view. Is called for the TEXT_VIEW_ITEM_RESOURCE views.
     *
     * @param view the text view to be configured
     */
    protected void configureTextView(TextView view) {
        view.setTextColor(textColor);
        view.setGravity(Gravity.CENTER);
        view.setTextSize(textSize);
        view.setLines(1);
        view.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
    }

    /**
     * Loads a text view from view
     *
     * @param view         the text view or layout containing it
     * @param textResource the text resource Id in layout
     * @return the loaded text view
     */
    private TextView getTextView(View view, int textResource) {
        TextView text = null;
        try {
            if (textResource == NO_RESOURCE && view instanceof TextView) {
                text = (TextView) view;
            } else if (textResource != NO_RESOURCE) {
                text = (TextView) view.findViewById(textResource);
            }
        } catch (ClassCastException e) {

            throw new IllegalStateException(
                    "AbstractWheelAdapter requires the resource ID to be a TextView", e);
        }

        return text;
    }

    /**
     * Loads view from resources
     *
     * @param resource the resource Id
     * @return the loaded view or null if resource is not set
     */
    private View getView(int resource, ViewGroup parent) {
        switch (resource) {
            case NO_RESOURCE:
                return null;
            case TEXT_VIEW_ITEM_RESOURCE:
                return new TextView(context);
            default:
                return inflater.inflate(resource, parent, false);
        }
    }
}

