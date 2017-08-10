package com.example.ichi.kenshu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

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
                List<String> errors = FormValidationUtil.validateAccountValues(email, password, confirmPass);
                if (!errors.isEmpty()) {
                    ErrorDialogFragment errorDialog = ErrorDialogFragment.newInstance(errors);
                    errorDialog.show(getFragmentManager(), "errorDialog");
                } else {
                    SharedPreferences data = getSharedPreferences(SharedPreferencesConstants.USER_DATA, Context.MODE_PRIVATE);
                    if (data.getInt(SharedPreferencesConstants.USER_ID, 0) != 0) {
                        finish();
                    } else {
                        signUp(email, password);
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                    ErrorDialogFragment errorDialog = ErrorDialogFragment.newInstance(response.code(),getApplicationContext());
                    errorDialog.show(getFragmentManager(), "errorDialog");
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("api", "fail");
                ErrorDialogFragment errorDialog = ErrorDialogFragment.newInstance(-1,getApplicationContext());
                errorDialog.show(getFragmentManager(), "errorDialog");
            }
        });
    }

    private void saveUserId(int userId, String token) {
        SharedPreferences data = getSharedPreferences(SharedPreferencesConstants.USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.putInt(SharedPreferencesConstants.USER_ID, userId);
        editor.putString(SharedPreferencesConstants.REQUEST_TOKEN, token);
        editor.apply();
    }
}
