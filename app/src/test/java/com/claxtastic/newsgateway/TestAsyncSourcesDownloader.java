package com.claxtastic.newsgateway;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class TestAsyncSourcesDownloader {

    private MainActivity mainActivity;

    @Before
    public void setupActivity() {
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
    }

    /* This test covers all of the AsyncSourcesDownloader class
    as well as the parseLanguageJson and parseCountryJson
    methods in the Utilities class
    * */
    @Test
    public void testAsyncSourcesDownloader() {
        AsyncSourcesDownloader downloaderThread = new AsyncSourcesDownloader(mainActivity);
        downloaderThread.execute();

        try {
            JSONObject response = new JSONObject(downloaderThread.get());
            String responseCode = response.getString("status");
            assertEquals("ok", responseCode.toLowerCase());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
