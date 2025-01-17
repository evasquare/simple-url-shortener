package com.evasquare.url_shortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UrlShortenerController {
    @Autowired
    UrlShortenerRepository repository;

    @PostMapping("add")
    public ResponseEntity<UrlData> add(@RequestParam String url) {
        if (!Utils.isValidUrl(url)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        UrlData addedUrl = repository.add(url);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedUrl);
    }

    @GetMapping("retrieve")
    public ResponseEntity<UrlData> retrieve(@RequestParam String shortenedUrl) {
        UrlData foundShortenedUrl = repository.findByShortenedUrl(shortenedUrl);

        if (foundShortenedUrl == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(foundShortenedUrl);
    }
}
