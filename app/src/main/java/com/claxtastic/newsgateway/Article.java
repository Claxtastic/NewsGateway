package com.claxtastic.newsgateway;

public class Article {
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    Article(String author, String title, String description, String url, String urlToImage, String publishedAt) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    /* Getters */
    public String getAuthor() { return this.author; }
    public String getTitle() { return this.title; }
    public String getDescription() { return this.description; }
    public String getUrl() { return this.url; }
    public String getUrlToImage() { return this.urlToImage; }
    public String getPublishedAt() { return this.publishedAt; }

    /* Setters */
    public void setAuthor(String author) { this.author = author; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setUrl(String url) { this.url = url; }
    public void setUrlToImage(String urlToImage) { this.urlToImage = urlToImage; }
    public void setPublishedAt(String publishedAt) { this.publishedAt = publishedAt; }

}
