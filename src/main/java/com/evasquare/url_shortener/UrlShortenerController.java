package com.evasquare.url_shortener;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UrlShortenerController {
    @Autowired
    UrlShortenerRepository repository;

    /** Example: http://localhost:8080/add?url=http://example.com */
    @PostMapping("add")
    public ResponseEntity<UrlData> add(@RequestParam String url) {
        if (!Utils.isValidUrl(url)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if (repository.findByUrl(url) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        UrlData addedUrl = repository.add(url);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedUrl);
    }

    /** Example: http://localhost:8080/link/1404cf6b */
    @GetMapping("link/{hash}")
    @ResponseBody
    public ResponseEntity<UrlData> getMethodName(@PathVariable String hash,
            HttpServletResponse response) {
        UrlData foundShortenedUrl = repository.findByShortenedUrl(hash);

        if (foundShortenedUrl == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        try {
            response.sendRedirect(foundShortenedUrl.getUrl());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok(foundShortenedUrl);
    }
}
