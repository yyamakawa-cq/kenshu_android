package com.example.ichi.kenshu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CustomBookListAdapter extends ArrayAdapter<Book> {
    private List<Book> books;
    private LayoutInflater inflater;

    public CustomBookListAdapter(Context context, int resource, List<Book> items) {
        super(context, resource, items);
        books = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = inflater.inflate(R.layout.custom_booklist, null);
        }
        Book item = books.get(position);

        ImageView imageView = view.findViewById(R.id.imageView_book);
        Glide.with(view).load(item.getImage()).into(imageView);

        TextView title = view.findViewById(R.id.textView_name);
        title.setText(item.getName());

        TextView price = view.findViewById(R.id.textView_price);
        price.setText(String.valueOf(item.getPrice()));

        TextView purchaseDate = view.findViewById(R.id.textView_purchaseDate);
        purchaseDate.setText(item.getPurchaseDate());

        return view;
    }
}
