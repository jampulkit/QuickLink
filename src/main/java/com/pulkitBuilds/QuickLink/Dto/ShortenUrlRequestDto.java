package com.pulkitBuilds.QuickLink.Dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class ShortenUrlRequestDto {
    @NotBlank(message = "URL cannot be empty or blank.")
    @URL(protocol = "", host = "", port = -1, message = "Invalid URL format. Please provide a valid URL (e.g., http://example.com).") // You can also specify protocol, host, etc. for stricter checks if needed. The basic @URL is often enough.
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
