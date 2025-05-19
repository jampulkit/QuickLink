package com.pulkitBuilds.QuickLink.Service;

import com.pulkitBuilds.QuickLink.Dto.ShortenUrlRequestDto;
import com.pulkitBuilds.QuickLink.Dto.ShortenUrlResponseDto;

public interface UrlShorteningService {
    ShortenUrlResponseDto shortenUrl(ShortenUrlRequestDto requestDto);

    String getLondUrlFromShortcode(String shortCode);
}
