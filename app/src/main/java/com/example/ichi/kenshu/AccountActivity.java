package com.example.ichi.kenshu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity {
    public Menu actionMenu;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private List<String> errorList = new ArrayList<>();

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
                editTextEmail.selectAll();
                String email = editTextEmail.getText().toString();
                editTextPassword.selectAll();
                String password = editTextPassword.getText().toString();
                editTextConfirmPassword.selectAll();
                String confirmPass = editTextConfirmPassword.getText().toString();
                ShowErrorDialog showErrorDialog = new ShowErrorDialog();

                if (email.isEmpty()) {
                    errorList.add(getString(R.string.form_email) + getString(R.string.validation_isEmpty));
                }
                if (password.isEmpty()) {
                    errorList.add(getString(R.string.form_password) + getString(R.string.validation_isEmpty));
                } else if (!password.equals(confirmPass)) {
                    errorList.add(getString(R.string.form_confirm_password) + getString(R.string.validation_notEqual));
                }
                if (errorList.size() > 0 ) {
                    showErrorDialog.showError(errorList,this);
                    errorList.clear();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
