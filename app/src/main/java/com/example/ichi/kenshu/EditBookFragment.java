package com.example.ichi.kenshu;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
                    ErrorDialogUtil.showError(errorList, getContext());
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

    public static EditBookFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS_POSITION, position);
        EditBookFragment editBookFragment = new EditBookFragment();
        editBookFragment.setArguments(bundle);
        return editBookFragment;
    }
}
