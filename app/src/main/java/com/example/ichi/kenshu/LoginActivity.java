package com.example.ichi.kenshu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.AppLaunchChecker;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;

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
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                List<String> errorList = new ArrayList<>();

                if (TextUtils.isEmpty(email)) {
                    errorList.add(getString(R.string.form_email) + getString(R.string.validation_isEmpty));
                }
                if (TextUtils.isEmpty(password)) {
                    errorList.add(getString(R.string.form_password) + getString(R.string.validation_isEmpty));
                }
                if (errorList.size() > 0 ) {
                    ErrorDialogUtil.showError(errorList, LoginActivity.this);
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
}
