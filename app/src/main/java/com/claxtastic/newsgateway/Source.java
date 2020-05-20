package com.claxtastic.newsgateway;

import android.text.SpannableString;

/* Represents a news source returned from API */
public class Source  {
    private String id;
    private String name;
    private String category;
    private String language;
    private String country;
    private SpannableString coloredName;

    Source(String id, String name, String category, String language, String country) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.language = language;
        this.country = country;

        this.coloredName = new SpannableString(name);
    }

    /* Getters */
    public String getId() { return this.id; }
    public String getName() { return this.name; }
    public String getCategory() { return this.category; }
    public String getLanguage() { return this.language; }
    public String getCountry() { return this.country; }
    public SpannableString getColoredName() { return this.coloredName; }

    /* Setters */
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setLanguage(String language) { this.language = language; }
    public void setCountry(String country) { this.country = country; }
    public void setColoredName(SpannableString coloredName) { this.coloredName = coloredName; }

    @Override
    public String toString() {
        return this.getName();
    }
}
