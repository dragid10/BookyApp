package com.alexoladele.testingshit;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends ListActivity {

    private static final String TAG = "SearchActivity";
    private String qry;
    private String siteSearchURL;
    String response;

    private OkHttpClient httpClient;

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
            qry = qry.trim();
            Log.i(TAG, "handleIntent: QUERY: " + qry + "\n Starting the doSearch Method!");
            doSearch(qry);
        } else {
            Log.i(TAG, "handleIntent: Intent WAS NOT search");
        }
    }

    private void doSearch(String query) {
        Log.i(TAG, "doSearch: In doSearch Method!");

        String libGenSiteURLPT1 = "http://gen.lib.rus.ec/search.php?req=", libGenSiteURLPT2 = "&lg_topic=libgen&open=0&view=simple&res=25&phrase=1&column=def";
        siteSearchURL = libGenSiteURLPT1 + query.replace(" ", "+") + libGenSiteURLPT2;
        Log.d(TAG, "doSearch: LibGen Site URL query = " + siteSearchURL);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    setResponse(runReq(siteSearchURL));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        // TODO: 3/2/17 Work on parsing through HTML and getting relevant info perhaps using jSOUP?

    }

    public String runReq(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        httpClient = new OkHttpClient();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
