package com.example.ichi.kenshu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public Menu actionMenu;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_list:
                    actionMenu.getItem(0).setVisible(true);
                    BookListFragment bookListFragment = new BookListFragment();
                    FragmentTransaction bookListTransaction = getSupportFragmentManager().beginTransaction();
                    bookListTransaction.replace(R.id.content, bookListFragment);
                    bookListTransaction.commit();
                    return true;
                case R.id.navigation_setting:
                    actionMenu.getItem(0).setVisible(false);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void moveToAccountActivity() {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
}
