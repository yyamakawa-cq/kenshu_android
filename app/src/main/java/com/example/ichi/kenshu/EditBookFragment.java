package com.example.ichi.kenshu;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditBookFragment extends Fragment {

    private static final String ARGS_POSITION = "args_position";
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
        Book book = (Book)bundle.getSerializable("book");
        editTextName.setText(book.getName());
        editTextPrice.setText(String.valueOf(book.getPrice()));
        textViewPurchaseDate.setText(book.getPurchaseDate());
        Glide.with(view).load(book.getImage()).into(imageViewUpload);

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
                List<String> errorList = new ArrayList<>();
                String name = editTextName.getText().toString();
                String price = editTextPrice.getText().toString();
                String date = textViewPurchaseDate.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    errorList.add(getString(R.string.form_name) + getString(R.string.validation_isEmpty));
                }
                if (TextUtils.isEmpty(price)) {
                    errorList.add(getString(R.string.form_price) + getString(R.string.validation_isEmpty));
                }
                if (TextUtils.isEmpty(date)) {
                    errorList.add(getString(R.string.form_purchase_date) + getString(R.string.validation_isEmpty));
                }
                if (errorList.size() > 0 ) {
                    ErrorDialogFragment errorDialog = ErrorDialogFragment.newInstance(errorList);
                    errorDialog.show(getActivity().getFragmentManager(), "errorDialog");
                } else {
                    String imageData = convertToString(imageViewUpload);
                    String purchaseDate = date.replaceAll("/","-");
                    Integer intPrice = Integer.valueOf(price);
                    Book book = new Book(name, intPrice, purchaseDate,imageData);
                    editBook(book);
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

    public static EditBookFragment newInstance(Integer position, Book item) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS_POSITION, position);
        bundle.putSerializable("book", item);
        EditBookFragment editBookFragment = new EditBookFragment();
        editBookFragment.setArguments(bundle);
        return editBookFragment;
    }

    //あとでクラスファイルを作って移動
    private void editBook(Book book) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface service = retrofit.create(ApiInterface.class);
        //book_idを変数にあとで変更
        service.editBook(01,book.getName(), book.getPrice(),book.getPurchaseDate(),book.getImage()).enqueue(new Callback<Book>() {
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

    //あとでクラスファイルをつくって移動
    private String convertToString(ImageView imageView) {
        Bitmap bitmapImage = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        Bitmap resizedImage = Bitmap.createScaledBitmap(bitmapImage, 100, 100, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        resizedImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toString();
    }
}
