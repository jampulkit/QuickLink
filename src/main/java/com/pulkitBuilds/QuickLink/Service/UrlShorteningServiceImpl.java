package com.pulkitBuilds.QuickLink.Service;

import com.pulkitBuilds.QuickLink.Dto.ShortenUrlRequestDto;
import com.pulkitBuilds.QuickLink.Dto.ShortenUrlResponseDto;
import com.pulkitBuilds.QuickLink.Entity.UrlMapping;
import com.pulkitBuilds.QuickLink.Repo.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pulkitBuilds.QuickLink.exception.ShortCodeNotFoundException;


import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class UrlShorteningServiceImpl implements UrlShorteningService {
    private static int shortCodeLength = 7;


    UrlMappingRepository urlMappingRepository;
    @Autowired
    public UrlShorteningServiceImpl(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    @Override
    public ShortenUrlResponseDto shortenUrl(ShortenUrlRequestDto requestDto) {
        String longUrl = requestDto.getLongUrl();
        if (urlMappingRepository.existsByLongUrl(longUrl)) {
            return new ShortenUrlResponseDto(urlMappingRepository.findByLongUrl(longUrl).getShortCode());
        }
        String shortCode;
        do{
            shortCode = generateRandomShortCode(longUrl);
        }while (urlMappingRepository.existsByShortCode(shortCode));
        UrlMapping newMapping = new UrlMapping(shortCode, longUrl);
        UrlMapping saveMapping = urlMappingRepository.save(newMapping);
        String shortUrl = saveMapping.getShortCode();
        saveMapping.setCreatedAt(LocalDateTime.now());

        return new ShortenUrlResponseDto(shortUrl, longUrl);

    }

    private String generateRandomShortCode(String longUrl) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[shortCodeLength*3/4+1];
        secureRandom.nextBytes(bytes);
        String randomChars = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        return randomChars.substring(0, shortCodeLength);
    }

    @Override
    public String getLondUrlFromShortcode(String shortCode) {

        return urlMappingRepository.findByShortCode(shortCode).map(UrlMapping::getLongUrl).orElseThrow(() ->
             new ShortCodeNotFoundException("Short code not found: " + shortCode));
    }
}
