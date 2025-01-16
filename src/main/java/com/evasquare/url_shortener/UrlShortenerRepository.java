package com.evasquare.url_shortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

        String randomHash = Utils.generateRandomHash();

        while (findByShortenedUrl(randomHash) != null) {
            randomHash = Utils.generateRandomHash();
        }

        jdbcTemplate.update("""
                INSERT INTO url_data (url, shortened_url)
                VALUES(?, ?)
                """,
                url, randomHash);

        return new UrlData(url, randomHash);
    }

    public UrlData findByUrl(String url) {
        try {
            return jdbcTemplate.queryForObject("""
                    SELECT * FROM url_data
                    WHERE url = ?;
                    """, new BeanPropertyRowMapper<>(UrlData.class), url);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public UrlData findByShortenedUrl(String shortenedUrl) {
        try {
            return jdbcTemplate.queryForObject("""
                    SELECT * FROM url_data
                    WHERE shortened_url = ?;
                    """, new BeanPropertyRowMapper<>(UrlData.class), shortenedUrl);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
