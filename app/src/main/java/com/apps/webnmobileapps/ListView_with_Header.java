/*package com.apps.webnmobileapps;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.apps.webnmobileapps.MainActivity;
import com.apps.webnmobileapps.R;

import java.util.ArrayList;

public class ListView_with_Header extends BaseAdapter {

    private ArrayList<Object> mData;
    private LayoutInflater mInflater;

    private static final int NORMAL_ITEM = 0;
    private static final int HEADER_ITEM = 1;
    private static final int FOOTER_ITEM = 2;

    public ListView_with_Header(Context context, ArrayList<Object> data){
        mData = data;
        mInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount(){
        return mData.size();
    }

    @Override
    public Object getItem(int position){
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        Object mObject = getItem(position);
        if (mObject instanceof String){
            return NORMAL_ITEM;
        } else if (mObject instanceof MainActivity.Header) {
            return HEADER_ITEM;
        } else {
            return IGNORE_ITEM_VIEW_TYPE;
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        int rowType = getItemViewType(position);
        Object object = getItem(position);

        View view;
        TextView textView;

        // In this simple case all row items are inflated as Textview's to contain the string
        // With the background color changed after inflation
        // But each object could use a different layout for each object type
        // e.g An ImageView, etc
        switch (rowType) {
            case NORMAL_ITEM:
                view = mInflater.inflate(R.layout.list_item, parent, false);
                textView = (TextView) view.findViewById(R.id.list_item);
                textView.setText((String) object);
                break;
            case HEADER_ITEM:
                view = mInflater.inflate(R.layout.list_item, parent, false);
                textView = (TextView) view.findViewById(R.id.list_item);
                // Set the background to red because it is a Header row
                textView.setBackgroundResource(R.color.greyHeader);
                textView.setTextSize(20);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setAllCaps(false);
                // Cast the object from generic list to Header type
                MainActivity.Header header = (MainActivity.Header) object;
                textView.setText(header.itemText);
                break;

            default:
                return convertView;

        }

        return view;
    }

}
*/