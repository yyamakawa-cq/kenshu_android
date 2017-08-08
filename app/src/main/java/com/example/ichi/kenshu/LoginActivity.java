package com.example.ichi.kenshu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private List<String> errorList = new ArrayList<>();

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
                Activity activity = getParent();
                if (validateValues(email, password)) {
                    login(email, password);
                } else {
                    ErrorDialogFragment errorDialog = ErrorDialogFragment.newInstance(errorList);
                    errorDialog.show(getFragmentManager(), "errorDialog");
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

    private boolean validateValues(String email, String password) {
        errorList.clear();
        if (TextUtils.isEmpty(email)) {
            errorList.add(getString(R.string.form_email) + getString(R.string.validation_isEmpty));
        }
        if (TextUtils.isEmpty(password)) {
            errorList.add(getString(R.string.form_password) + getString(R.string.validation_isEmpty));
        }
        return errorList.size() == 0;
    }

    private void login(String email, final String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface service = retrofit.create(ApiInterface.class);

        service.login(new User(email, password)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Log.d("api", "success");
                    updateUserId(response.body().getUserId(), response.body().getRequestToken());
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Log.d("api","error");
                    showApiError(response.code());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("api", "fail");
                showApiError(null);
            }
        });
    }

    private void updateUserId(Integer userId, String token) {
        SharedPreferences data = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String oldRequestToken = data.getString("token","none");
        Integer oldUserId = data.getInt("user_id", 0);
        if (!TextUtils.equals(oldRequestToken, token) || !oldUserId.equals(userId)) {
            SharedPreferences.Editor editor = data.edit();
            editor.putInt("user_id",userId);
            editor.putString("token", token);
            editor.apply();
        }
    }

    private void showApiError(Integer errorCode) {
        List<String> errorText = new ArrayList<>();
        errorText.add(getString(R.string.api_error));
        if (errorCode != null) {
            errorText.add("Error Code:" + String.valueOf(errorCode));
        }
        ErrorDialogFragment errorDialog = ErrorDialogFragment.newInstance(errorText);
        errorDialog.show(getFragmentManager(), "errorDialog");
    }
}
