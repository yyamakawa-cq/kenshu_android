package com.example.ichi.kenshu;

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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                List<String> errors = FormValidationUtil.validateAccountValues(email, password);
                if (!errors.isEmpty()) {
                    ErrorDialogFragment errorDialog = ErrorDialogFragment.newInstance(errors);
                    errorDialog.show(getFragmentManager(), "errorDialog");
                } else {
                    login(email, password);
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
                    ErrorDialogFragment errorDialog = ErrorDialogFragment.newInstance(response.code());
                    errorDialog.show(getFragmentManager(), "errorDialog");
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("api", "fail");
                ErrorDialogFragment errorDialog = ErrorDialogFragment.newInstance(-1);
                errorDialog.show(getFragmentManager(), "errorDialog");
            }
        });
    }

    private void updateUserId(int userId, String token) {
        SharedPreferences data = getSharedPreferences(SharedPreferencesConstants.USER_DATA, Context.MODE_PRIVATE);
        String oldRequestToken = data.getString(SharedPreferencesConstants.REQUEST_TOKEN,"none");
        Integer oldUserId = data.getInt(SharedPreferencesConstants.USER_ID, 0);
        if (!TextUtils.equals(oldRequestToken, token) || !oldUserId.equals(userId)) {
            SharedPreferences.Editor editor = data.edit();
            editor.putInt(SharedPreferencesConstants.USER_ID, userId);
            editor.putString(SharedPreferencesConstants.REQUEST_TOKEN, token);
            editor.apply();
        }
    }
}
