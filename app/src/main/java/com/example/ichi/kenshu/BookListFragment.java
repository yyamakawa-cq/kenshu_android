package com.example.ichi.kenshu;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private static final int LIMIT = 500;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_booklist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = view.findViewById(R.id.ListView_Books);
        listView.addFooterView(getFooterView());

        getBook(getOffset(),LIMIT);

        Button buttonLoad = getActivity().findViewById(R.id.button_load);
        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int loadLimit = getOffset() + LIMIT;
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

    public int getOffset() {
        int offset ;
        if (listView.getCount() != 0) {
            offset = listView.getCount();
        } else {
            offset = 0;
        }
        return offset;
    }

    private void getBook(int offset, int limit) {
        String page = (String.valueOf(offset)) + "-" + (String.valueOf(limit));
        SharedPreferences data = getActivity().getSharedPreferences(SharedPreferencesConstants.USER_DATA, Context.MODE_PRIVATE);
        int userId = data.getInt(SharedPreferencesConstants.USER_ID, 0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface service = retrofit.create(ApiInterface.class);
        service.getBook(page,userId).enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("api", "success");
                    if (response.body().getResult().size() > 0 ){
                        List<Book> items = new ArrayList<>(response.body().getResult());
                        setBookItem(items);
                    }
                } else {
                    Log.d("api", "error");
                    ErrorDialogFragment errorDialog = ErrorDialogFragment.newInstance(response.code(), getContext());
                    errorDialog.show(getActivity().getFragmentManager(), "errorDialog");
                }
            }
            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                Log.d("api", "fail");
                ErrorDialogFragment errorDialog = ErrorDialogFragment.newInstance(-1, getContext());
                errorDialog.show(getActivity().getFragmentManager(), "errorDialog");
            }
        });
    }

    public void setBookItem(List<Book> items) {
        CustomBookListAdapter adapter = new CustomBookListAdapter(getContext(), R.layout.custom_booklist, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book item = (Book)parent.getItemAtPosition(position);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, EditBookFragment.newInstance(position, item));

                transaction.addToBackStack("list");
                transaction.commit();
            }
        });
    }
}