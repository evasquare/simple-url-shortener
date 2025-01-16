package com.evasquare.url_shortener;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UrlShortenerRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public UrlData add(String url) {
        if (findByUrl(url) != null) {
            return null;
        }

        String randomHash = generateRandomHash();

        while (findByShortenedUrl(randomHash) != null) {
            randomHash = generateRandomHash();
        }

        jdbcTemplate.update("""
                INSERT INTO url_data (url, shortened_url)
                VALUES(?, ?)
                """,
                url, randomHash);

        return new UrlData(url, randomHash);
    }

    public UrlData findByUrl(String url) {
        return jdbcTemplate.queryForObject("""
                SELECT * FROM url_data
                WHERE url = ?;
                """, new BeanPropertyRowMapper<>(UrlData.class), url);
    }

    public UrlData findByShortenedUrl(String shortenedUrl) {
        return jdbcTemplate.queryForObject("""
                SELECT * FROM url_data
                WHERE shortened_url = ?;
                """, new BeanPropertyRowMapper<>(UrlData.class), shortenedUrl);
    }

    private String generateRandomHash() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
