package com.pulkitBuilds.QuickLink.Dto;

public class ShortenUrlRequestDto {
    private String longUrl;

    public ShortenUrlRequestDto(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
