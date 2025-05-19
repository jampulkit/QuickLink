package com.pulkitBuilds.QuickLink.Controllers;

import com.pulkitBuilds.QuickLink.Dto.ShortenUrlRequestDto;
import com.pulkitBuilds.QuickLink.Dto.ShortenUrlResponseDto;
import com.pulkitBuilds.QuickLink.Service.UrlShorteningServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/url")
@Validated
public class UrlShorteningController {
    private final UrlShorteningServiceImpl urlShorteningService;
    private final String baseUrl;

    public UrlShorteningController(UrlShorteningServiceImpl urlShorteningService, @Value("${app.baseUrl:http://localhost:8080}") String baseUrl) {
        this.urlShorteningService = urlShorteningService;
        this.baseUrl = baseUrl;
    }


    @PostMapping("/short")
    public ResponseEntity<ShortenUrlResponseDto> shotenUrl(@RequestBody ShortenUrlRequestDto shortenUrlRequestDto) {
        ShortenUrlResponseDto response = urlShorteningService.shortenUrl(shortenUrlRequestDto);
        String fullShortUrl = baseUrl + "/s/" + response.getShortUrl().substring(response.getShortUrl().lastIndexOf('/') + 1);
        response.setShortUrl(fullShortUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/s/{shortCode}")
    public void RedirectToOriginalUrl(@PathVariable String shortCode, HttpServletResponse response) throws IOException {
        String originalUrl = urlShorteningService.getLondUrlFromShortcode(shortCode);
        if (originalUrl != null) {
            response.sendRedirect(originalUrl);
        } else {
           response.sendError(HttpServletResponse.SC_NOT_FOUND, "Short URL not found");
        }
    }
}
