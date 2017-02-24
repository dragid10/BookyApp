package com.alexoladele.testingshit;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

import java.io.IOException;
import java.util.List;


public class SearchResultsActivity extends Activity {
    private static final String TAG = "SearchResultsActivity";
    final String KEY = "AIzaSyDW7fdN32AjoHbZTPbnC0vbuV_Kj4Rup_Q";
    final private String cx = "005041979895276442722:rxn0rzgsnug";
    private String qry;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

//        Handle the queryString
        handleIntent(getIntent());

    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(TAG, "handleIntent: " + query);
            //use the query to search your data somehow
            /* URL url = new URL("https://www.googleapis.com/customsearch/v1?key=" + KEY + "&amp;cx=" + cx + "&amp;q=" + query);
             HttpURLConnection connection = (HttpURLConnection) url.openConnection();
             connection.setRequestMethod("GET");
             connection.setRequestProperty("Accept", "applicaiton/json");
             BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             Gson results = new Gson().fromJson(br, Gson.class);
             connection.disconnect();*/

            HttpRequestInitializer httpRequestInitializer = new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) throws IOException {

                }
            };
            JsonFactory jsonFactory = new JacksonFactory();
            HttpTransport httpTransport = new NetHttpTransport();
            Customsearch customsearch = new Customsearch.Builder(httpTransport, jsonFactory, httpRequestInitializer)
                    .setApplicationName("BookyApp")
                    .build();
            qry = query;

            try {
                Customsearch.Cse.List list = customsearch.cse().list(qry);
                list.setKey(KEY);
                list.setCx(cx);
                Search results = list.execute();
                List<Result> items = results.getItems();

                for (Result item : items) {
                    Log.d("Response", item.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }




            /*   HttpRequestInitializer httpRequestInitializer = new HttpRequestInitializer() {

                @Override
                public void initialize(HttpRequest request) throws IOException {

                }
            };
            //Instantiate a Customsearch object with a transport mechanism and json parser
            Customsearch customsearch = new Customsearch(new NetHttpTransport(), new JacksonFactory(), httpRequestInitializer);
            customsearch.*/
        }
    }

    public String getQry() {
        return qry;
    }
}
