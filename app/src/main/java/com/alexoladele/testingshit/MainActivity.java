package com.alexoladele.testingshit;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import layout.WelcomeScreenFragment;

import static com.alexoladele.testingshit.R.id.search;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 0);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

//        Makes sure that the root_layout view is not null before doing anything
        if (findViewById(R.id.root_layout) != null) {

//            Makes sure that there's no saved instance before proceeding
            if (savedInstanceState == null) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction manager = fm.beginTransaction();
                manager
                        .add(R.id.root_layout, WelcomeScreenFragment.newInstance(), "welcomeScreen")
                        .commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

//        Associate Searchable config with SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(search).getActionView();
        if (searchView != null) {
            Log.d(TAG, "onCreateOptionsMenu: SearchView Not null!");
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        } else {
            Log.d(TAG, "onCreateOptionsMenu: SearchView was null!");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }
}
