package com.example.ichi.kenshu;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class BookListFragment extends Fragment {


    public BookListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booklist, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = (ListView) view.findViewById(R.id.ListView_Books);
        ArrayList<CustomBookListItem> listItems = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            CustomBookListItem item = new CustomBookListItem(bmp, "title" + String.valueOf(i), "price" + String.valueOf(i), "PurchaseDate" + String.valueOf(i));
            listItems.add(item);
        }

        CustomBookListAdapter adapter = new CustomBookListAdapter(this.getContext(), R.layout.custom_booklist, listItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                CustomBookListItem item = (CustomBookListItem) listView.getItemAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Tap No." + String.valueOf(position));
                builder.setMessage(item.getTitle());
                builder.show();
            }
        });
    }
}
