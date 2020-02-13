package com.claxtastic.newsgateway;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import android.util.Log;

public class Utilities {
    private static final String TAG = "Utilities";

    public static ArrayList<String> parseLanguageJson(HashSet<String> languageCodes, InputStream inputStream) {
        ArrayList<String> fullLanguageStrings = new ArrayList<>();
        try {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);

            inputStream.close();
            JSONObject languageJsonObject = new JSONObject(new String(buffer, "UTF-8"));
            JSONArray languageJsonArray = languageJsonObject.getJSONArray("languages");
            for (int i = 0; i < languageJsonArray.length(); i++) {
                JSONObject languagePairJsonObject = languageJsonArray.getJSONObject(i);
                String code = languagePairJsonObject.getString("code");
                for (String languageCode : languageCodes)
                    if (languageCode.toUpperCase().equals(code))
                        fullLanguageStrings.add(languagePairJsonObject.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fullLanguageStrings;
    }

    public static ArrayList<String> parseCountryJson(HashSet<String> countryCodes, InputStream inputStream) {
        ArrayList<String> fullCountryStrings = new ArrayList<>();
        try {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);

            inputStream.close();
            JSONObject countryJsonObject = new JSONObject(new String(buffer, "UTF-8"));
            JSONArray countryJsonArray = countryJsonObject.getJSONArray("countries");
            for (int i = 0; i < countryJsonArray.length(); i++) {
                JSONObject countryPairJsonObject = countryJsonArray.getJSONObject(i);
                String code = countryPairJsonObject.getString("code");
                for (String countryCode : countryCodes)
                    if (countryCode.toUpperCase().equals(code))
                        fullCountryStrings.add(countryPairJsonObject.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fullCountryStrings;
    }

    public static ArrayList<Source> filterOnTopic(ArrayList<Source> unfilteredSources, String topic) {
        ArrayList<Source> filteredSources = new ArrayList<>();
        for (Source source : unfilteredSources)
            if (source.getCategory().equals(topic))
                filteredSources.add(source);
        return filteredSources;
    }

    /* Return lowercase language code given language name */
    public static String getLanguageCode(String language, InputStream inputStream) {
        try {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);

            inputStream.close();
            JSONObject languageJsonObject = new JSONObject(new String(buffer, "UTF-8"));
            JSONArray languageJsonArray = languageJsonObject.getJSONArray("languages");
            for (int i = 0; i < languageJsonArray.length(); i++) {
                JSONObject languagePairJsonObject = languageJsonArray.getJSONObject(i);
                if (languagePairJsonObject.getString("name").equals(language))
                    return languagePairJsonObject.getString("code").toLowerCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* Return lowercase country code given country name */
    public static String getCountryCode(String country, InputStream inputStream) {
        try {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);

            inputStream.close();
            JSONObject countryJsonObject = new JSONObject(new String(buffer, "UTF-8"));
            JSONArray countryJsonArray = countryJsonObject.getJSONArray("countries");
            for (int i = 0; i < countryJsonArray.length(); i++) {
                JSONObject countryPairJsonObject = countryJsonArray.getJSONObject(i);
                if (countryPairJsonObject.getString("name").equals(country))
                    return countryPairJsonObject.getString("code").toLowerCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Source> filterOnLanguage(ArrayList<Source> unfilteredSources, String language) {
        ArrayList<Source> filteredSources = new ArrayList<>();
        for (Source source : unfilteredSources)
            if (source.getLanguage().equals(language))
                filteredSources.add(source);
        return filteredSources;
    }

    public static ArrayList<Source> filterOnCountry(ArrayList<Source> unfilteredSources, String country) {
        ArrayList<Source> filteredSources = new ArrayList<>();
        for (Source source : unfilteredSources)
            if (source.getCountry().equals(country))
                filteredSources.add(source);
        return filteredSources;
    }
}
