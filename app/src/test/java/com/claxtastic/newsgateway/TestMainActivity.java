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

    }

    @Test
    public void testOnClickLanguage() {

    }

    @Test
    public void testOnClickCountry() {

    }
}
