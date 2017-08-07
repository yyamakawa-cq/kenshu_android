package com.example.ichi.kenshu;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

public class AddBookActivity extends AppCompatActivity {
    private ImageView imageViewUpload;
    private EditText editTextName;
    private EditText editTextPrice;
    private TextView textViewPurchaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        imageViewUpload = (ImageView) findViewById(R.id.imageView_upload);
        Button button = (Button) findViewById(R.id.button_upload);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        });

        editTextName = (EditText) findViewById(R.id.editText_name);
        editTextPrice = (EditText) findViewById(R.id.editText_price);
        textViewPurchaseDate = (TextView) findViewById(R.id.textView_purchaseDate);
        textViewPurchaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PurchaseDatePickerFragment datePicker = new PurchaseDatePickerFragment();
                datePicker.show(getFragmentManager(), "datePicker");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save_close, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_close:
                finish();
                return true;
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
                    errorDialog.show(getFragmentManager(), "errorDialog");
                } else {
                    String imageData = ImageConverterUtil.convertToString(imageViewUpload);
                    String purchaseDate = date.replaceAll("/","-");
                    Integer intPrice = Integer.valueOf(price);
                    addBook(name, intPrice, purchaseDate,imageData);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0){
            if (resultCode == Activity.RESULT_OK) {
                ContentResolver contentResolver = getContentResolver();
                InputStream inputStream = null;
                try {
                    inputStream = contentResolver.openInputStream(data.getData());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                try {
                    assert inputStream != null;
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageViewUpload.setImageBitmap(bitmap);
            }
        }
    }

    //あとでクラスファイルをつくって移動
    private void addBook(String name, Integer price, String purchaseDate, String imageData) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface service = retrofit.create(ApiInterface.class);
        SharedPreferences data = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String requestToken = data.getString("token","none");
        Integer userId = data.getInt("user_id", 0);
        Book addBook = new Book(name, price, purchaseDate, imageData, userId);

        service.addBook(requestToken,addBook).enqueue(new Callback<Book>() {
            List<String> errorList = new ArrayList<>();
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Log.d("api", "success");
                    finish();
                } else {
                    Log.d("api","error");
                    errorList.add(getString(R.string.api_error));
                    errorList.add("Error Code:" + String.valueOf(response.code()));
                    ErrorDialogFragment errorDialog = ErrorDialogFragment.newInstance(errorList);
                    errorDialog.show(getFragmentManager(), "errorDialog");
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.d("api", "fail");
                errorList.add(getString(R.string.api_error));
                ErrorDialogFragment errorDialog = ErrorDialogFragment.newInstance(errorList);
                errorDialog.show(getFragmentManager(), "errorDialog");
            }
        });
    }
}
