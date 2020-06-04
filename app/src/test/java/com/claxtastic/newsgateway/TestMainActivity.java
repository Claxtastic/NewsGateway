package com.claxtastic.newsgateway;

import android.view.MenuItem;
import android.widget.ListView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.fakes.RoboMenuItem;

import static junit.framework.TestCase.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class TestMainActivity {

    /* These constants are for testing the filtering options
    *  These are assuming the NewsAPI does not add or remove sources */
    private final int NUM_ALL_SOURCES = 128;
    private final int NUM_GENERAL_SOURCES = 75;
    private final int NUM_ENGLISH_SOURCES = 81;
    private final int NUM_CANADA_SOURCES = 4;

    private MainActivity mainActivity;

    /* Create the mock MainActivity */
    @Before
    public void createActivity() {
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
    }

    /***************** Tests for onOptionsItemSelected() *****************/
    @Test
    public void testTopicsSelected() {
        MenuItem topicsItem = new RoboMenuItem(R.id.item_topics);
        mainActivity.onOptionsItemSelected(topicsItem);

        assertEquals("topics", mainActivity.getCategoryExpanded());
    }

    @Test
    public void testLanguagesSelected() {
        MenuItem languagesMenu = new RoboMenuItem(R.id.item_languages);
        mainActivity.onOptionsItemSelected(languagesMenu);

        assertEquals("languages", mainActivity.getCategoryExpanded());
    }

    @Test
    public void testCountriesSelected() {
        MenuItem countriesItem = new RoboMenuItem(R.id.item_countries);
        mainActivity.onOptionsItemSelected(countriesItem);

        assertEquals("countries", mainActivity.getCategoryExpanded());
    }

    @Test
    public void testOnClickTopic() {
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
        MenuItem topicsItem = new RoboMenuItem(R.id.item_topics);
        mainActivity.onOptionsItemSelected(topicsItem);
        MenuItem topicItem = new RoboMenuItem().setTitle("general");
        mainActivity.onOptionsItemSelected(topicItem);

        assertEquals(NUM_GENERAL_SOURCES, mainActivity.getCurrentSources().size());
    }

    @Test
    public void testOnClickLanguage() {
        MenuItem languagesItem = new RoboMenuItem(R.id.item_languages);
        mainActivity.onOptionsItemSelected(languagesItem);
        MenuItem languageItem = new RoboMenuItem().setTitle("English");
        mainActivity.onOptionsItemSelected(languageItem);

        assertEquals(NUM_ENGLISH_SOURCES, mainActivity.getCurrentSources().size());
    }

    @Test
    public void testOnClickCountry() {
        MenuItem countriesItem = new RoboMenuItem(R.id.item_countries);
        mainActivity.onOptionsItemSelected(countriesItem);
        MenuItem countryItem = new RoboMenuItem().setTitle("Canada");
        mainActivity.onOptionsItemSelected(countryItem);

        assertEquals(NUM_CANADA_SOURCES, mainActivity.getCurrentSources().size());
    }

    @Test
    public void testFilterReset() {
        MenuItem topicsItem = new RoboMenuItem(R.id.item_topics);
        mainActivity.onOptionsItemSelected(topicsItem);
        MenuItem allItem = new RoboMenuItem().setTitle("all");
        mainActivity.onOptionsItemSelected(allItem);

        assertEquals(NUM_ALL_SOURCES, mainActivity.getCurrentSources().size());
    }

    @Test
    public void testInvalidFilter() {
        /* Mock a set of filters that produces a 0 size list */
        MenuItem topicsItem = new RoboMenuItem(R.id.item_topics);
        mainActivity.onOptionsItemSelected(topicsItem);
        MenuItem topicItem = new RoboMenuItem().setTitle("entertainment");
        mainActivity.onOptionsItemSelected(topicItem);
        MenuItem countriesItem = new RoboMenuItem(R.id.item_countries);
        mainActivity.onOptionsItemSelected(countriesItem);
        MenuItem countryItem = new RoboMenuItem().setTitle("Israel");
        mainActivity.onOptionsItemSelected(countryItem);

        /* This should return 8 sources in the "entertainment" category, and display the invalid filter parameters dialog */
        assertEquals(8, mainActivity.getCurrentSources().size());
    }

    /***************** End of onOptionsItemSelected() tests *****************/

    @Test
    public void testDrawerList() {
        ListView drawerListView = mainActivity.findViewById(R.id.drawer_list);
        /* Perform click on the source "ABC News" */
        Shadows.shadowOf(drawerListView).performItemClick(0);
    }

    @Test
    public void testDrawerListOnClick() {
        ListView drawerListView = mainActivity.findViewById(R.id.drawer_list);
        Shadows.shadowOf(drawerListView).performItemClick(0);
    }
}
