package com.claxtastic.newsgateway;

import org.junit.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TestUtilities {

    @Test
    public void testGetLanguageCode() {
        File languageJson = new File("src/main/res/raw/language_codes.json");
        try {
            String languageCode = Utilities.getLanguageCode("English", new FileInputStream(languageJson));
            assertTrue("en".equals(languageCode));
        } catch (FileNotFoundException e) {
            System.out.println("File not found exception for path: \nsrc/main/res/raw/language_codes.json");
        }
    }

    @Test(expected = NullPointerException.class)
    public void testGetLanguageCodeException() {
        Utilities.getLanguageCode("United States", null);
    }

    @Test
    public void testGetCountryCode() {
        File countryJson = new File("src/main/res/raw/country_codes.json");
        try {
            String countryCode = Utilities.getCountryCode("United States", new FileInputStream(countryJson));
            assertTrue("us".equals(countryCode));
        } catch (FileNotFoundException e) {
            System.out.println("File not found exception for path: \nsrc/main/res/raw/language_codes.json");
        }
    }

    @Test(expected = NullPointerException.class)
    public void testGetCountryCodeException() {
        Utilities.getCountryCode("United States", null);
    }

    @Test
    public void testFilterOnTopic() {
        Source s1 = new Source("", "CNN", "General", "", "");
        Source s2 = new Source("", "ESPN", "Sports", "", "");
        ArrayList<Source> unfilteredSources = new ArrayList<>();
        unfilteredSources.add(s1);
        unfilteredSources.add(s2);
        ArrayList<Source> expectedList = new ArrayList<>();
        expectedList.add(s1);
        assertEquals(expectedList, Utilities.filterOnTopic(unfilteredSources, "General"));
    }

    @Test
    public void testFilterOnCountry() {
        Source s1 = new Source("", "CNN", "", "", "United States");
        Source s2 = new Source("", "BBC", "", "", "United Kingdom");
        ArrayList<Source> unfilteredSources = new ArrayList<>();
        unfilteredSources.add(s1);
        unfilteredSources.add(s2);
        ArrayList<Source> expectedList = new ArrayList<>();
        expectedList.add(s2);
        assertEquals(expectedList, Utilities.filterOnCountry(unfilteredSources, "United Kingdom"));
    }

    @Test
    public void testFilterOnLanguage() {
        Source s1 = new Source("", "CNN", "", "English", "");
        Source s2 = new Source("", "Wired.de", "", "German", "");
        ArrayList<Source> unfilteredSources = new ArrayList<>();
        unfilteredSources.add(s1);
        unfilteredSources.add(s2);
        ArrayList<Source> expectedList = new ArrayList<>();
        expectedList.add(s2);
        assertEquals(expectedList, Utilities.filterOnLanguage(unfilteredSources, "German"));
    }
}
