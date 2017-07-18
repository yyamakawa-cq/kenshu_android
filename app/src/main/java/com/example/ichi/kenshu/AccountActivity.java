package com.example.ichi.kenshu;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity {
    public Menu actionMenu;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private ArrayList<String> errorList = new ArrayList<>();

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
        inflater.inflate(R.menu.action_menu, menu);
        this.actionMenu = menu;
        actionMenu.getItem(0).setVisible(false);//追加:非表示
        actionMenu.getItem(1).setVisible(true);//保存:表示
        actionMenu.getItem(2).setVisible(true);//閉じる:表示
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

                if (email.isEmpty()) {
                    errorList.add(getString(R.string.form_email) + getString(R.string.validation_isEmpty));
                }
                if (password.isEmpty()) {
                    errorList.add(getString(R.string.form_password) + getString(R.string.validation_isEmpty));
                } else if (!password.equals(confirmPass)) {
                    errorList.add(getString(R.string.form_confirm_password) + getString(R.string.validation_notEqual));
                }
                if (errorList.size() > 0 ) {
                    showError(errorList);
                    errorList.clear();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    void showError(ArrayList error) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getString(R.string.validation_error));
        alertDialog.setPositiveButton(getString(R.string.button_ok), null);
        StringBuilder messages = new StringBuilder();
        for (int i = 0; i < error.size(); i++) {
            messages.append("\n");
            String text = (String) error.get(i);
            messages.append(text);
        }
        alertDialog.setMessage(messages);
        alertDialog.show();
    }
}
