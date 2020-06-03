package com.claxtastic.newsgateway;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestArticle {

    // TODO: Make these assertALl

    @Test
    public void testArticleConstructor() {
        Article a = new Article("", "", "","","","");
        assertEquals(a.toString(), a.toString());
    }

    @Test
    public void testArticleGetters() {
        Article a = new Article("Author", "Title", "Description", "http://google.com", "http://google.com", "10/12/2019");
        assertEquals("Author", a.getAuthor());
        assertEquals("Title", a.getTitle());
        assertEquals("Description", a.getDescription());
        assertEquals("http://google.com", a.getUrl());
        assertEquals("http://google.com", a.getUrlToImage());
        assertEquals("10/12/2019", a.getPublishedAt());
    }

    @Test
    public void testArticleSetters() {
        Article a = new Article("Author", "Title", "Description", "http://google.com", "http://google.com", "10/12/2019");
        a.setAuthor("A1");
        a.setTitle("T1");
        a.setDescription("D1");
        a.setUrl("U1");
        a.setUrlToImage("U2");
        a.setPublishedAt("D2");

        assertEquals("A1", a.getAuthor());
        assertEquals("T1", a.getTitle());
        assertEquals("D1", a.getDescription());
        assertEquals("U1", a.getUrl());
        assertEquals("U2", a.getUrlToImage());
        assertEquals("D2", a.getPublishedAt());
    }
}