package com.claxtastic.newsgateway;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

public class AsyncSourcesDownloader extends AsyncTask<String, Void, String> {
    private static final String TAG = "AsyncSourcesDownloader";
    private final String BASE_URL = "https://newsapi.org/v2/sources?apiKey=";
    private final String KEY = "30b6e774879741cb982dbd39a3153b17";

    private MainActivity mainActivity;

    AsyncSourcesDownloader(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        Uri dataUri = Uri.parse(BASE_URL + KEY);
        String urlToUse = dataUri.toString();

        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(inputStream)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }

    @Override protected void onPostExecute(String sourcesJsonString) {
        ArrayList<Source> sourceList = parseSourcesJson(sourcesJsonString);
        if (sourceList != null) {
            ArrayList<HashSet<String>> sets = getTopicsLanguagesCountries(sourceList);
            mainActivity.handleSourcesAPIResponse(sourceList, sets);
        }
    }

    private ArrayList<Source> parseSourcesJson(String jsonString) {
        ArrayList<Source> sourceList = new ArrayList<>();
        try {
            JSONObject rootJson = new JSONObject(jsonString);
            JSONArray sourcesJsonArray = rootJson.getJSONArray("sources");
            for (int i = 0; i < sourcesJsonArray.length(); i++) {
                JSONObject sourceJsonObject = sourcesJsonArray.getJSONObject(i);
                String id = sourceJsonObject.getString("id");
                String name = sourceJsonObject.getString("name");
                String category = sourceJsonObject.getString("category");
                String language = sourceJsonObject.getString("language");
                String country = sourceJsonObject.getString("country");

                sourceList.add(new Source(id, name, category, language, country));
            }
        } catch (JSONException e) {
            Log.e(TAG, "parseSourcesJSON: ", e);
        }
        return sourceList;
    }

    private ArrayList<HashSet<String>> getTopicsLanguagesCountries(ArrayList<Source> sources) {
        ArrayList<HashSet<String>> sets = new ArrayList<>();
        HashSet<String> topics = new HashSet<String>();
        HashSet<String> languages = new HashSet<String>();
        HashSet<String> countries = new HashSet<String>();
        for (Source source : sources) {
            topics.add(source.getCategory());
            languages.add(source.getLanguage());
            countries.add(source.getCountry());
        }
        sets.add(topics);
        sets.add(languages);
        sets.add(countries);

        return sets;
    }
}
