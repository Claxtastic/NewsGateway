package com.claxtastic.newsgateway;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class AsyncArticleDownloader extends AsyncTask<String, Void, String> {
    private static final String TAG = "AsyncArticleDownloader";
    private final String BASE_URL = "https://newsapi.org/v2/top-headlines?sources=";
    private final String API_PARAM = "&apikey=";
    private final String KEY = "30b6e774879741cb982dbd39a3153b17";

    private MainActivity mainActivity;
    private String sourceName;

    AsyncArticleDownloader(MainActivity mainActivity, String sourceName) {
        this.mainActivity = mainActivity;
        this.sourceName = sourceName;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            if (sourceName == null) {
                throw new NullPointerException();
            }

            Uri dataUri = Uri.parse(BASE_URL + sourceName + API_PARAM + KEY);
            String urlToUse = dataUri.toString();

            StringBuilder sb = new StringBuilder();
            URL url = new URL(urlToUse);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            return sb.toString();
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: ", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String articleJsonString) {
        ArrayList<Article> articleList = parseArticleJson(articleJsonString);
        if (articleList != null) {
            mainActivity.handleArticlesAPIResponse(articleList);
        }
    }

    private ArrayList<Article> parseArticleJson(String jsonString) {
        ArrayList<Article> articleList = new ArrayList<>();
        try {
            JSONObject rootJson = new JSONObject(jsonString);
            JSONArray articlesJsonArray = rootJson.getJSONArray("articles");
            for (int i = 0; i < articlesJsonArray.length(); i++) {
                JSONObject articleJsonObject = articlesJsonArray.getJSONObject(i);
                String author = articleJsonObject.getString("author");
                String title = articleJsonObject.getString("title");
                String description = articleJsonObject.getString("description");
                String url = articleJsonObject.getString("url");
                String urlToImage = articleJsonObject.getString("urlToImage");

                String publishedAt = "";
                try {
                    String[] parts = articleJsonObject.getString("publishedAt").split("-");
                    String year = parts[0];
                    String month = parts[1];
                    String[] otherParts = parts[2].split("T");
                    String day = otherParts[0];
                    String[] hourMin = otherParts[1].split(":");
                    String hour = hourMin[0];
                    String min = hourMin[1];
                    publishedAt = month + "-" + day + "-" + year + " " + hour + ":" + min;
                } catch (Exception e) {
                    /* Default to whatever json says */
                    publishedAt = articleJsonObject.getString("publishedAt");
                }

                articleList.add(new Article(author, title, description, url, urlToImage, publishedAt));
            }
        } catch (Exception e) {
            Log.e(TAG, "parseArticleJson: ", e);
        }
        return articleList;
    }
}
