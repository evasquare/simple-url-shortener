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
    public ResponseEntity<UrlData> addUrlData(@RequestParam String url) {
        UrlData addedUrl = repository.add(url);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedUrl);
    }

    @GetMapping("find")
    public ResponseEntity<UrlData> getUrlData(@RequestParam String url) {
        UrlData foundUrl = repository.findByUrl(url);

        if (foundUrl == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(foundUrl);
    }
}
