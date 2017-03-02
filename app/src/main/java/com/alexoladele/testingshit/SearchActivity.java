package com.alexoladele.testingshit;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.api.services.customsearch.Customsearch;


public class SearchActivity extends ListActivity {
    private static final String TAG = "SearchActivity";
    final String KEY = "AIzaSyDW7fdN32AjoHbZTPbnC0vbuV_Kj4Rup_Q";
    final private String cx = "005041979895276442722:rxn0rzgsnug";
    private String qry;
    private Customsearch customsearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Log.i(TAG, "onCreate: ContentView was set to activity_search");

        Intent intent = getIntent();
        Log.i(TAG, "onCreate: Got Intent");

        Log.i(TAG, "onCreate: Handling Intent!");
        handleIntent(intent);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // Call detail activity for clicked entry
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            qry = intent.getStringExtra(SearchManager.QUERY);
            Log.i(TAG, "handleIntent: QUERY: " + qry + "\n Starting the doSearch Method!");
            doSearch(qry);
        } else {
            Log.i(TAG, "handleIntent: Intent WAS NOT search");
        }
    }

    private void doSearch(String query) {
        Log.i(TAG, "doSearch: In doSearch Method!");
    }
}
