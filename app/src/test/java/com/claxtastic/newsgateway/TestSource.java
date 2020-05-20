package com.claxtastic.newsgateway;

import android.text.SpannableString;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestSource {

    @Test
    public void testSourceConstructor() {
        Source s = new Source("", "", "", "","");
        assertEquals("", s.toString());
    }

    @Test
    public void testSourceGetters() {
        Source s = new Source("NYT", "New York Times", "General", "English", "United States");
        assertEquals("NYT", s.getId());
        assertEquals("New York Times", s.getName());
        assertEquals("General", s.getCategory());
        assertEquals("English", s.getLanguage());
        assertEquals("United States", s.getCountry());
        // TODO: Mock SpannableString
        // assertEquals(new SpannableString(s.getName()), s.getColoredName());
    }

    @Test
    public void testSourceSetters() {
        Source s = new Source("NYT", "New York Times", "General", "English", "United States");
        s.setId("ESPN");
        s.setName("ESPN");
        s.setCategory("Sports");
        s.setLanguage("English (America)");
        s.setCountry("United States of America");
        s.setColoredName(new SpannableString(s.getName()));

        assertEquals("ESPN", s.getId());
        assertEquals("ESPN", s.getName());
        assertEquals("Sports", s.getCategory());
        assertEquals("English (America)", s.getLanguage());
        assertEquals("United States of America", s.getCountry());
        // TODO: Mock SpannableString
        // assertEquals(new SpannableString(s.getName()), s.getColoredName());
    }
}
