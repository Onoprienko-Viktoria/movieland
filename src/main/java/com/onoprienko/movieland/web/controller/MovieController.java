package com.onoprienko.movieland.web.controller;

import com.onoprienko.movieland.common.CurrencyCode;
import com.onoprienko.movieland.common.SortDirection;
import com.onoprienko.movieland.common.request.AddMovieRequest;
import com.onoprienko.movieland.common.request.EditMovieRequest;
import com.onoprienko.movieland.common.request.MovieByIdRequest;
import com.onoprienko.movieland.common.request.MoviesRequest;
import com.onoprienko.movieland.dto.MovieDto;
import com.onoprienko.movieland.dto.MovieWithDetailsDto;
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
                                  @RequestParam(name = "rating", required = false) SortDirection ratingDirection,
                                  @RequestParam(name = "price", required = false) SortDirection priceDirection) {
        return movieService.findAll(MoviesRequest.builder()
                .page(page)
                .priceDirection(priceDirection)
                .ratingDirection(ratingDirection)
                .build());

    }

    @GetMapping("/genre/{genreId}")
    public List<MovieDto> findAllByGenre(@RequestParam int page,
                                         @PathVariable(required = false) int genreId,
                                         @RequestParam(name = "rating", required = false) SortDirection ratingDirection,
                                         @RequestParam(name = "price", required = false) SortDirection priceDirection) {
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

    @GetMapping("/{movieId}")
    public MovieWithDetailsDto findById(@PathVariable Long movieId,
                                        @RequestParam(name = "currency", required = false) CurrencyCode currencyCode) {
        MovieByIdRequest request = MovieByIdRequest.builder()
                .movieId(movieId)
                .currencyCode(currencyCode).build();
        return movieService.findById(request);

    }

    @PostMapping()
    public void add(@RequestBody AddMovieRequest request) {
        movieService.add(request);
    }

    @PutMapping("/{id}")
    public void edit(@RequestBody EditMovieRequest request,
                     @PathVariable long id) {
        request.setMovieId(id);
        movieService.edit(request);
    }
}
