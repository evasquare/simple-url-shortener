package com.evasquare.url_shortener;

public class UrlData {
    private String url;
    private String shortenedUrl;

    public UrlData() {}

    public UrlData(String url, String shortenedUrl) {
        this.url = url;
        this.shortenedUrl = shortenedUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    public void setShortenedUrl(String shortenedUrl) {
        this.shortenedUrl = shortenedUrl;
    }

    @Override
    public String toString() {
        return "UrlData [url=" + url + ", shortenedUrl=" + shortenedUrl + "]";
    }
}
