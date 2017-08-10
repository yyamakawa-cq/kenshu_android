package com.example.ichi.kenshu;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditBookFragment extends Fragment {
    private static final String ARGS_POSITION = "args_position";
    private static final String BOOK = "book";
    private int bookId;
    private ImageView imageViewUpload;
    private EditText editTextName;
    private EditText editTextPrice;
    private TextView textViewPurchaseDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_edit_book, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextName = view.findViewById(R.id.editText_name);
        editTextPrice = view.findViewById(R.id.editText_price);
        textViewPurchaseDate = view.findViewById(R.id.textView_purchaseDate);
        imageViewUpload = view.findViewById(R.id.imageView_upload);

        Bundle bundle = getArguments();
        Book book = (Book)bundle.getSerializable(BOOK);
        bookId = book.getBookId();
        editTextName.setText(book.getName());
        editTextPrice.setText(String.valueOf(book.getPrice()));
        textViewPurchaseDate.setText(DateFormatterUtil.changeDateFormat(book.getPurchaseDate()));

        Glide.with(view).load(book.getImageUrl()).into(imageViewUpload);

        Button button = view.findViewById(R.id.button_upload);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        });

        textViewPurchaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PurchaseDatePickerFragment datePicker = new PurchaseDatePickerFragment();
                datePicker.show(getActivity().getFragmentManager(),"datePicker");
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_save_back, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_save:
                String name = editTextName.getText().toString();
                String price = editTextPrice.getText().toString();
                String date = textViewPurchaseDate.getText().toString();
                List<String> errors = FormValidationUtil.validateBookValues(name, price, date);
                if (!errors.isEmpty()) {
                    ErrorDialogFragment errorDialog = ErrorDialogFragment.newInstance(errors);
                    errorDialog.show(getActivity().getFragmentManager(), "errorDialog");
                } else {
                    String imageData = ImageConverterUtil.convertToString(imageViewUpload);
                    String purchaseDate = date.replaceAll("/","-");
                    int intPrice = Integer.valueOf(price);
                    editBook(bookId, name, intPrice, purchaseDate, imageData);
                }
                return true;
            case R.id.menu_back:
                getFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                ContentResolver contentResolver = getActivity().getContentResolver();
                InputStream inputStream = null;
                try {
                    inputStream = contentResolver.openInputStream(data.getData());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageViewUpload.setImageBitmap(bitmap);
            }
        }
    }

    public static EditBookFragment newInstance(int position, Book item) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS_POSITION, position);
        bundle.putSerializable(BOOK, item);
        EditBookFragment editBookFragment = new EditBookFragment();
        editBookFragment.setArguments(bundle);
        return editBookFragment;
    }

    private void editBook(int bookId, String name, int price, String purchaseDate, String imageData) {
        SharedPreferences data = getActivity().getSharedPreferences(SharedPreferencesConstants.USER_DATA, Context.MODE_PRIVATE);
        String requestToken = data.getString(SharedPreferencesConstants.REQUEST_TOKEN,"none");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface service = retrofit.create(ApiInterface.class);
        Book book = new Book(name, price, purchaseDate, imageData);
        service.editBook(bookId,requestToken,book).enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Log.d("api", "success");
                    getFragmentManager().popBackStack();
                } else {
                    Log.d("api","error");
                    ErrorDialogFragment errorDialog = ErrorDialogFragment.newInstance(response.code(), getContext());
                    errorDialog.show(getActivity().getFragmentManager(), "errorDialog");
                }
            }
            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.d("api", "fail");
                ErrorDialogFragment errorDialog = ErrorDialogFragment.newInstance(-1, getContext());
                errorDialog.show(getActivity().getFragmentManager(), "errorDialog");
            }
        });
    }
}
