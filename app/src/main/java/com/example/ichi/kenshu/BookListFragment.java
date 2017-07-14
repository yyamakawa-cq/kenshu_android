package com.example.ichi.kenshu;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class BookListFragment extends Fragment {

    private MainActivity parent;

    public BookListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booklist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = (ListView) view.findViewById(R.id.ListView_Books);
        ArrayList<Book> listItems = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            Book item = new Book(bmp, "title" + String.valueOf(i), "price" + String.valueOf(i), "PurchaseDate" + String.valueOf(i));
            listItems.add(item);
        }

        CustomBookListAdapter adapter = new CustomBookListAdapter(this.getContext(), R.layout.custom_booklist, listItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditBookFragment editBookFragment = new EditBookFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("selected", position);
                editBookFragment.setArguments(bundle);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, editBookFragment);

                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (parent.actionMenu != null) {
            parent.actionMenu.getItem(0).setVisible(true);//追加:表示
            parent.actionMenu.getItem(1).setVisible(false);//保存:非表示
            parent.actionMenu.getItem(3).setVisible(false);//戻る:非表示
        }
    }

    @Override
    public void onAttach(Context context) {
        parent = (MainActivity) context;
        super.onAttach(context);
    }
}
