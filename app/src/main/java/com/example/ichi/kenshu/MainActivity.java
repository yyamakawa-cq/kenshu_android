package com.example.ichi.kenshu;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.AppLaunchChecker;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public Menu actionMenu;
    public Bitmap imageViewUpload;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_list:
                    //actionMenu.getItem(0).setVisible(true);
                    BookListFragment bookListFragment = new BookListFragment();
                    changeFragment(bookListFragment);
                    return true;
                case R.id.navigation_setting:
                    //actionMenu.getItem(0).setVisible(false);
                    SettingFragment settingFragment = new SettingFragment();
                    changeFragment(settingFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BookListFragment bookListFragment = new BookListFragment();
        FragmentTransaction bookListTransaction = getSupportFragmentManager().beginTransaction();
        bookListTransaction.add(R.id.content, bookListFragment);
        bookListTransaction.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (AppLaunchChecker.hasStartedFromLauncher(this)) {
            Log.d("AppLaunchChecker", "2回目以降起動");
        } else {
            Log.d("AppLaunchChecker", "初回起動");
            moveToAccountActivity();
        }
        AppLaunchChecker.onActivityCreate(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        this.actionMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_addBook:
                Intent intent = new Intent(this, AddBookActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_back:
                getSupportFragmentManager().popBackStack();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }

    void moveToAccountActivity() {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    void pickImageView(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(resultCode == Activity.RESULT_OK) {
                ContentResolver contentResolver = getContentResolver();
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
                imageViewUpload = bitmap;
            }
        }
    }

    public void showDatePickerDialog(View view) {
        PurchaseDatePicker datePicker = new PurchaseDatePicker();
        datePicker.show(getFragmentManager(), "datePicker");
    }
}
