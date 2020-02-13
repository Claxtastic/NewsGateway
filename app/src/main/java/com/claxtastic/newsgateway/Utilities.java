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
        Log.d(TAG, "parseLanguageJson: here");
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
}
