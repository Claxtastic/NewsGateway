package com.claxtastic.newsgateway;

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
}
