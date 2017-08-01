package com.example.ichi.kenshu;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookListFragment extends Fragment {

    private static final Integer LIMIT = 100;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_booklist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final List<Book> listItems = new ArrayList<>();

        listView = view.findViewById(R.id.ListView_Books);
        listView.addFooterView(getFooterView());

        getBook(getOffset(),LIMIT);

        //あとで消すココから
        for(int i = 0; i < 10; i++) {
            String imageUrl = "http://cat.sc/pic/wp-content/uploads/2016/10/cat-sc-neko_02422-360x360.jpg";
            Book item = new Book(imageUrl, "title" + String.valueOf(i), 100+i, "2017/05/0" + String.valueOf(i));
            listItems.add(item);
        }
        //あとで消すここまで

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

        Button buttonLoad = getActivity().findViewById(R.id.button_load);
        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer loadLimit = getOffset() + LIMIT;
                getBook(getOffset(),loadLimit);
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

    public Integer getOffset() {
        Integer offset ;
        if (listView.getCount() != 0) {
            offset = listView.getCount();
        } else {
            offset = 0;
        }
        return offset;
    }

    //あとでクラスファイル作って移動
    private void getBook(Integer offset, Integer limit) {
        String page = (String.valueOf(offset)) + "-" + (String.valueOf(limit));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface service = retrofit.create(ApiInterface.class);
        //user_idを変数に変更
        service.getBook(page,90).enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                Log.d("api", "success");
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.d("api", "fail");
            }
        });
    }
}