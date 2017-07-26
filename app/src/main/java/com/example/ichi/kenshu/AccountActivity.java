package com.example.ichi.kenshu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        editTextEmail = (EditText)findViewById(R.id.editText_email);
        editTextPassword = (EditText)findViewById(R.id.editText_password);
        editTextConfirmPassword = (EditText)findViewById(R.id.editText_confirmPassword);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save_close, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        List<String> errorList = new ArrayList<>();
        switch (item.getItemId()) {
            case R.id.menu_close:
                finish();
                return true;
            case R.id.menu_save:
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPass = editTextConfirmPassword.getText().toString();
                ErrorDialogUtil errorDialogUtil = new ErrorDialogUtil();

                if (TextUtils.isEmpty(email)) {
                    errorList.add(getString(R.string.form_email) + getString(R.string.validation_isEmpty));
                }
                if (TextUtils.isEmpty(password)) {
                    errorList.add(getString(R.string.form_password) + getString(R.string.validation_isEmpty));
                }
                else if (!TextUtils.equals(password,confirmPass)) {
                    errorList.add(getString(R.string.form_confirm_password) + getString(R.string.validation_notEqual));
                }
                if (errorList.size() > 0 ) {
                    errorDialogUtil.showError(errorList,this);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
