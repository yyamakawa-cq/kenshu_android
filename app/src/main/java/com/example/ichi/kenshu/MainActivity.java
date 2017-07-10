package com.example.ichi.kenshu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_list:
                    BookListFragment bookListFragment = new BookListFragment();
                    FragmentTransaction bookListTransaction = getSupportFragmentManager().beginTransaction();
                    bookListTransaction.replace(R.id.content, bookListFragment);
                    bookListTransaction.commit();
                    return true;
                case R.id.navigation_setting:
                    SettingFragment settingFragment = new SettingFragment();
                    FragmentTransaction settingTransaction = getSupportFragmentManager().beginTransaction();
                    settingTransaction.replace(R.id.content, settingFragment);
                    settingTransaction.commit();
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
    }

}
