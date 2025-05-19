package com.pulkitBuilds.QuickLink.Dto;

import java.time.LocalDateTime;

public class ShortenUrlResponseDto {
    private String shortUrl;
    private String OriginalUrl;
    private LocalDateTime createdAt;

    public ShortenUrlResponseDto(String shortUrl) {
        this.shortUrl = shortUrl;

    }

    public ShortenUrlResponseDto(String shortUrl, String originalUrl) {
        this.shortUrl = shortUrl;
        OriginalUrl = originalUrl;
    }



    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getOriginalUrl() {
        return OriginalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        OriginalUrl = originalUrl;
    }
}
