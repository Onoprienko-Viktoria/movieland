package com.onoprienko.movieland.service;

import com.onoprienko.movieland.dto.CountryDto;
import com.onoprienko.movieland.entity.Country;

import java.util.List;

public interface CountryService {
    List<CountryDto> findAll();

    List<Country> findAllByIdIn(List<Integer> countries);

    List<CountryDto> findByMovieId(Long movieId);
}
