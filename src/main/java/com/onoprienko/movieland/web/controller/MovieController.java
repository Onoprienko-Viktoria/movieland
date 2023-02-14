package com.onoprienko.movieland.web.controller;

import com.onoprienko.movieland.entity.Movie;
import com.onoprienko.movieland.service.MovieService;
import com.onoprienko.movieland.service.entity.SortEnum;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movie/")
@RequiredArgsConstructor
public class MovieController {
    Logger log = LoggerFactory.getLogger(MovieController.class);
    private final MovieService movieService;

    @GetMapping()
    public ResponseEntity<List<Movie>> findAll(@RequestParam int page,
                                               @RequestParam(required = false, defaultValue = "none") String rating,
                                               @RequestParam(required = false, defaultValue = "none") String price) {
        try {
            return ResponseEntity.ok(movieService.findAll(page,
                    SortEnum.valueOf(rating.toUpperCase()),
                    SortEnum.valueOf(price.toUpperCase())));
        } catch (Exception e) {
            log.error("Exception while find all movies", e);
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/genre/{genreId}")
    public ResponseEntity<List<Movie>> findByGenre(@PathVariable int genreId,
                                                   @RequestParam int page) {
        try {
            return ResponseEntity.ok(movieService.findAllByGenre(genreId, page));
        } catch (Exception e) {
            log.error("Exception while find movies by genre", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/random")
    public ResponseEntity<List<Movie>> findRandom() {
        try {
            return ResponseEntity.ok(movieService.findRandom());
        } catch (Exception e) {
            log.error("Exception while find movies by genre", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
