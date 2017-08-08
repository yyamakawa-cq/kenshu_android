package com.example.ichi.kenshu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private List<String> errorList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPassword = (EditText) findViewById(R.id.editText_password);
        editTextConfirmPassword = (EditText) findViewById(R.id.editText_confirmPassword);
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
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPass = editTextConfirmPassword.getText().toString();
                if (validateValues(email, password, confirmPass)) {
                    SharedPreferences data = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    if (data.getInt("user_id", 0) == 0) {
                        signUp(email, password);
                   } else {
                        finish();
                    }
                } else {
                    ErrorDialogFragment errorDialog = ErrorDialogFragment.newInstance(errorList);
                    errorDialog.show(getFragmentManager(), "errorDialog");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean validateValues(String email, String password, String confirmPass) {
        errorList.clear();
        if (TextUtils.isEmpty(email)) {
            errorList.add(getString(R.string.form_email) + getString(R.string.validation_isEmpty));
        }
        if (TextUtils.isEmpty(password)) {
            errorList.add(getString(R.string.form_password) + getString(R.string.validation_isEmpty));
        } else if (!TextUtils.equals(password, confirmPass)) {
            errorList.add(getString(R.string.form_confirm_password) + getString(R.string.validation_notEqual));
        }
        return errorList.size() == 0;
    }

    private void signUp(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface service = retrofit.create(ApiInterface.class);
        service.createUser(new User(email,password)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Log.d("api","success");
                    saveUserId(response.body().getUserId(),response.body().getRequestToken());
                    finish();
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

    private void saveUserId(Integer userId, String token) {
        SharedPreferences data = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.putInt("user_id", userId);
        editor.putString("token", token);
        editor.apply();
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
