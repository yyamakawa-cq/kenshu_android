package com.example.ichi.kenshu;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class AddBookActivity extends AppCompatActivity {
    public Menu actionMenu;
    private ImageView imageViewUpload;
    private EditText editTextName;
    private EditText editTextPrice;
    private TextView textViewPurchaseDate;
    private List<String> errorList = new ArrayList<>();

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save_close, menu);
        this.actionMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_close:
                finish();
                return true;
            case R.id.menu_save:
                editTextName.selectAll();
                String name = editTextName.getText().toString();
                editTextPrice.selectAll();
                String price = editTextPrice.getText().toString();
                String date = textViewPurchaseDate.getText().toString();
                ShowErrorDialogUtil showErrorDialogUtil = new ShowErrorDialogUtil();

                if (name.isEmpty()) {
                    errorList.add(getString(R.string.form_name) + getString(R.string.validation_isEmpty));
                }
                if (price.isEmpty()) {
                    errorList.add(getString(R.string.form_price) + getString(R.string.validation_isEmpty));
                }
                if (date.isEmpty()) {
                    errorList.add(getString(R.string.form_purchase_date) + getString(R.string.validation_isEmpty));
                }
                if (errorList.size() > 0 ) {
                   showErrorDialogUtil.showError(errorList, this);
                    errorList.clear();
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
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageViewUpload.setImageBitmap(bitmap);
            }
        }
    }

    public void showDatePickerDialog(View view) {
        PurchaseDatePickerUtil datePicker = new PurchaseDatePickerUtil();
        datePicker.show(getFragmentManager(), "datePicker");
    }
}
