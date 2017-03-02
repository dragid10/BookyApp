package com.alexoladele.testingshit;

import android.Manifest;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import layout.WelcomeScreenFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SearchView searchView;
    private FragmentManager fm;
    private FragmentTransaction manager;

    final static String[] NEEDED_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

//        Permission Requests
        if (ContextCompat.checkSelfPermission(getApplicationContext(), NEEDED_PERMISSIONS[0])
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), NEEDED_PERMISSIONS[1])
                        != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), NEEDED_PERMISSIONS[2])
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, 808);
            Log.i(TAG, "onCreate: Requesting Permissions");

        }

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            Log.i(TAG, "onCreate: First Run- Welcome displayed");
            switchToWelcomeFrag();
        } else {
            Log.i(TAG, "onCreate: Skipping Welcome Screen");
            switchToMainFrag();
        }
    }

    private void switchToMainFrag() {
        fm = getSupportFragmentManager();
        manager = fm.beginTransaction();
        manager
                .add(R.id.root_layout, MainScreenFragment.newInstance(), "mainScreen")
                .commit();
    }

    private void switchToWelcomeFrag() {
        fm = getSupportFragmentManager();
        manager = fm.beginTransaction();
        manager
                .add(R.id.root_layout, WelcomeScreenFragment.newInstance(), "welcomeScreen")
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (808) {
            case 808:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permissions GRANTED", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permissions NOT GRANTED", Toast.LENGTH_SHORT).show();


                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

//        VARIBLES TO USE
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

//      Set Up the search Function
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        ComponentName componentName = new ComponentName(getApplicationContext(), SearchActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        searchView.setIconifiedByDefault(false);
        return true;
    }
}
