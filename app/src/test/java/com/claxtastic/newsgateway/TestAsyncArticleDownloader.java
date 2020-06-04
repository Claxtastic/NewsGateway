package com.claxtastic.newsgateway;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricTestRunner.class)
public class TestAsyncArticleDownloader {

    @Test
    public void testAsyncArticleDownloader()
            throws ExecutionException, InterruptedException, JSONException {
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        AsyncArticleDownloader downloaderThread = new AsyncArticleDownloader(mainActivity, "CNN");
        downloaderThread.execute();

        JSONObject response = new JSONObject(downloaderThread.get());
        String responseCode = response.getString("status");
        assertEquals("ok", responseCode.toLowerCase());
    }

    @Test
    public void testAsyncArticleDownloaderEmptyString()
            throws ExecutionException, InterruptedException {
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        AsyncArticleDownloader downloaderThread = new AsyncArticleDownloader(mainActivity, "");
        downloaderThread.execute();

        assertNull(downloaderThread.get());
    }

    @Test
    public void testAsyncArticleDownloaderNull()
            throws ExecutionException, InterruptedException {
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        AsyncArticleDownloader downloaderThread = new AsyncArticleDownloader(mainActivity, null);
        downloaderThread.execute();

        assertNull(downloaderThread.get());
    }

    @Test
    public void testArticleFragment() {
        /* We need an article to create an ArticleFragment */
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        AsyncArticleDownloader downloaderThread = new AsyncArticleDownloader(mainActivity, "CNN");
        downloaderThread.execute();

        try {
            JSONObject articlesJson = new JSONObject(downloaderThread.get());
            JSONArray articlesJsonArray = articlesJson.getJSONArray("articles");
            JSONObject articleObjectJson = articlesJsonArray.getJSONObject(0);
            String author = articleObjectJson.getString("author");
            String title = articleObjectJson.getString("title");
            String description = articleObjectJson.getString("description");
            String url = articleObjectJson.getString("url");
            String urlToImage = articleObjectJson.getString("urlToImage");
            String publishedAt = articleObjectJson.getString("publishedAt");
            Article article = new Article(author, title, description, url, urlToImage, publishedAt);
            ArticleFragment articleFragment = ArticleFragment.newInstance(article, 0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
