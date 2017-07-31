package com.example.ichi.kenshu;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class BookListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_booklist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = view.findViewById(R.id.ListView_Books);
        listView.addFooterView(getFooterView());

        final List<Book> listItems = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            String imageUrl = "http://cat.sc/pic/wp-content/uploads/2016/10/cat-sc-neko_02422-360x360.jpg";
            Book item = new Book(imageUrl, "title" + String.valueOf(i), 100+i, "2017/05/0" + String.valueOf(i));
            listItems.add(item);
        }

        CustomBookListAdapter adapter = new CustomBookListAdapter(this.getContext(), R.layout.custom_booklist, listItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book item = (Book)parent.getItemAtPosition(position);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, EditBookFragment.newInstance(position, item));

                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.menu_add:
                Intent intent = new Intent(getContext(), AddBookActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public View getFooterView() {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        return layoutInflater.inflate(R.layout.listview_footer,null);
    }


}