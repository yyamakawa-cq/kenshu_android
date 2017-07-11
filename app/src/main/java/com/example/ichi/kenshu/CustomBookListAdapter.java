package com.example.ichi.kenshu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomBookListAdapter extends ArrayAdapter<CustomBookListItem> {
    private int mResource;
    private List<CustomBookListItem> mItems;
    private LayoutInflater mInflater;

    public CustomBookListAdapter(Context context, int resource, List<CustomBookListItem> items) {
        super(context, resource, items);

        mResource = resource;
        mItems = items;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView != null) {
            view = convertView;
        } else {
            view = mInflater.inflate(mResource, null);
        }
        CustomBookListItem item = mItems.get(position);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView_book);
        imageView.setImageBitmap(item.getImageView());

        TextView title = (TextView) view.findViewById(R.id.textView_name);
        title.setText(item.getTitle());

        TextView price = (TextView) view.findViewById(R.id.textView_price);
        price.setText(item.getPrice());

        TextView purchaseDate = (TextView) view.findViewById(R.id.textView_purchaseDate);
        purchaseDate.setText(item.getPurchaseDate());

        return view;
    }
}
