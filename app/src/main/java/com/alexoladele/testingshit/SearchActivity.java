package com.alexoladele.testingshit;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends ListActivity {

    private static final String TAG = "SearchActivity";
    private String qry;
    private String fileName;
    private String siteSearchURL;
    private String stringResponse;
    private Request request;
    private Response response;
    private ArrayList<Integer> idList = new ArrayList<>();

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
        fileName = qry.toLowerCase().trim().replace(" ", "_");
        Log.d(TAG, "doSearch: LibGen Site URL query = " + siteSearchURL);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    setStringResponse(runReq(siteSearchURL));
//                    Write stringResponse to Phone storage
                    File file = new File(getApplicationContext().getCacheDir(), fileName);
                    FileWriter writer = new FileWriter(file);
                    writer.flush();
                    writer.append(getStringResponse());
                    writer.close();
                    Log.i(TAG, "run: File successfully written to");

//                    Gets Book ID's and adds them to arraylist
                    Log.i(TAG, "run: Reading File HTML");
                    getIDS();


//                    GET request to get JSON
                    String getFinalURL = getJsonData();

//                    Gets URL and saves to var
                    String jsonResult = runReq(getFinalURL);
                    Log.d(TAG, "run: jsonResult is: " + jsonResult);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        // TODO: 3/2/17 Work on parsing through HTML and getting relevant info perhaps using jSOUP?


    }

    @NonNull
    private String getJsonData() throws IOException {
        Log.i(TAG, "run: Getting JSON Data");
        String getURL1 = "http://libgen.io/json.php?ids=",
                getURL2 = "&fields=Title,Author,MD5",
                getURLID = "",
                getFinalURL = "";


//                    Better gets the ID nums to put into var
        StringBuilder builder = new StringBuilder();
        for (int i : idList) {
            if (i == idList.get(idList.size() - 1)) {
                builder.append(i);
            } else {
                builder.append(i).append(",");
            }
        }
//                    Builds final GET url
        getURLID = builder.toString();
        getFinalURL = getURL1 + getURLID + getURL2;
        return getFinalURL;
    }

    private void getIDS() {
        try {
            File file = new File(getCacheDir(), fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = reader.readLine()) != null) {
                String searchString = "?id=";
                if (line.contains(searchString)) {
                    String word = line.substring(line.indexOf(searchString) + 4, line.indexOf("\'", 17));
                    int tempIDStorage = Integer.parseInt(word);
                    idList.add(tempIDStorage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String runReq(String url) throws IOException {
        request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient httpClient = new OkHttpClient();
        response = httpClient.newCall(request).execute();
        return response.body().string();
    }

    public String getStringResponse() {
        return stringResponse;
    }

    public void setStringResponse(String stringResponse) {
        this.stringResponse = stringResponse;
    }


}
