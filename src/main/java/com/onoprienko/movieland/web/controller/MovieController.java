package com.onoprienko.movieland.web.controller;

import com.onoprienko.movieland.common.MoviesRequest;
import com.onoprienko.movieland.common.SortDirectionEnum;
import com.onoprienko.movieland.dto.MovieDto;
import com.onoprienko.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movie/")
@RequiredArgsConstructor
@Slf4j
public class MovieController {
    private final MovieService movieService;

    @GetMapping()
    public List<MovieDto> findAll(@RequestParam int page,
                                  @RequestParam(name = "rating", required = false) SortDirectionEnum ratingDirection,
                                  @RequestParam(name = "price", required = false) SortDirectionEnum priceDirection) {
        return movieService.findAll(MoviesRequest.builder()
                .page(page)
                .priceDirection(priceDirection)
                .ratingDirection(ratingDirection)
                .build());

    }

    @GetMapping("/genre/{genreId}")
    public List<MovieDto> findAllByGenre(@RequestParam int page,
                                         @PathVariable(required = false) int genreId,
                                         @RequestParam(name = "rating", required = false) SortDirectionEnum ratingDirection,
                                         @RequestParam(name = "prive", required = false) SortDirectionEnum priceDirection) {
        return movieService.findByGenre(MoviesRequest.builder()
                .page(page)
                .priceDirection(priceDirection)
                .ratingDirection(ratingDirection)
                .genreId(genreId)
                .build());

    }

    @GetMapping("/random")
    public List<MovieDto> findRandom() {
        return movieService.findRandom();
    }
}
