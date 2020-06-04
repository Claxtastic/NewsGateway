package com.claxtastic.newsgateway;

import android.view.MenuItem;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.fakes.RoboMenuItem;

import static junit.framework.TestCase.assertEquals;

@RunWith(RobolectricTestRunner.class)
@FixMethodOrder
public class TestMainActivity {

    /* These constants are for testing the filtering options
    *  These are assuming the NewsAPI does not add or remove sources */
    private final int NUM_ALL_SOURCES = 128;
    private final int NUM_GENERAL_SOURCES = 75;
    private final int NUM_ENGLISH_SOURCES = 81;
    private final int NUM_CANADA_SOURCES = 0;

    // Tests for onOptionsItemSelected()
    @Test
    public void testTopicsSelected() {
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
        MenuItem topicsItem = new RoboMenuItem(R.id.item_topics);
        mainActivity.onOptionsItemSelected(topicsItem);

        assertEquals("topics", mainActivity.getCategoryExpanded());
    }

    @Test
    public void testLanguagesSelected() {
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
        MenuItem languagesMenu = new RoboMenuItem(R.id.item_languages);
        mainActivity.onOptionsItemSelected(languagesMenu);

        assertEquals("languages", mainActivity.getCategoryExpanded());
    }

    @Test
    public void testCountriesSelected() {
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
        MenuItem countriesItem = new RoboMenuItem(R.id.item_countries);
        mainActivity.onOptionsItemSelected(countriesItem);

        assertEquals("countries", mainActivity.getCategoryExpanded());
    }

    @Test
    public void testOnClickTopic() {
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
        MenuItem topicsItem = new RoboMenuItem(R.id.item_topics);
        mainActivity.onOptionsItemSelected(topicsItem);
        MenuItem topicItem = new RoboMenuItem().setTitle("general");
        mainActivity.onOptionsItemSelected(topicItem);

        assertEquals(NUM_GENERAL_SOURCES, mainActivity.getCurrentSources().size());
    }

    @Test
    public void testOnClickLanguage() {
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
        MenuItem languagesItem = new RoboMenuItem(R.id.item_languages);
        mainActivity.onOptionsItemSelected(languagesItem);
        MenuItem languageItem = new RoboMenuItem().setTitle("English");
        mainActivity.onOptionsItemSelected(languageItem);

        assertEquals(NUM_ENGLISH_SOURCES, mainActivity.getCurrentSources().size());
    }

    @Test
    public void testOnClickCountry() {
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
        MenuItem countriesItem = new RoboMenuItem(R.id.item_countries);
        mainActivity.onOptionsItemSelected(countriesItem);
        MenuItem countryItem = new RoboMenuItem().setTitle("Canada");
        mainActivity.onOptionsItemSelected(countryItem);

        assertEquals(NUM_CANADA_SOURCES, mainActivity.getCurrentSources());
    }

    @Test
    public void testFilterReset() {
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
        MenuItem topicsItem = new RoboMenuItem(R.id.item_topics);
        mainActivity.onOptionsItemSelected(topicsItem);
        MenuItem allItem = new RoboMenuItem().setTitle("all");
        mainActivity.onOptionsItemSelected(allItem);

        assertEquals(NUM_ALL_SOURCES, mainActivity.getCurrentSources().size());
    }
}
