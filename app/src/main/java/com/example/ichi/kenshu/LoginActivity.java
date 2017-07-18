package com.example.ichi.kenshu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.AppLaunchChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private ArrayList<String> errorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText)findViewById(R.id.editText_email);
        editTextPassword = (EditText)findViewById(R.id.editText_password);

        Button button = (Button) findViewById(R.id.button_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextEmail.selectAll();
                String email = editTextEmail.getText().toString();
                editTextPassword.selectAll();
                String password = editTextPassword.getText().toString();

                if (email.isEmpty()) {
                    errorList.add(getString(R.string.form_email) + getString(R.string.validation_isEmpty));
                }
                if (password.isEmpty()) {
                    errorList.add(getString(R.string.form_password) + getString(R.string.validation_isEmpty));
                }
                if (errorList.size() > 0 ) {
                    showError(errorList);
                    errorList.clear();
                } else {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
                }
            }
        });

        if (AppLaunchChecker.hasStartedFromLauncher(this)) {
            Log.d("AppLaunchChecker", "2回目以降起動");
        } else {
            Log.d("AppLaunchChecker", "初回起動");
            Intent intent = new Intent(getApplication(), AccountActivity.class);
            startActivity(intent);
        }
        AppLaunchChecker.onActivityCreate(this);
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
