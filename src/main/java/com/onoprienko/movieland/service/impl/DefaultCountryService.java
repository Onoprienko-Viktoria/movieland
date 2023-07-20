package com.onoprienko.movieland.service.impl;

import com.onoprienko.movieland.dto.CountryDto;
import com.onoprienko.movieland.entity.Country;
import com.onoprienko.movieland.mapper.CountryMapper;
import com.onoprienko.movieland.repository.jpa.JpaCountryRepository;
import com.onoprienko.movieland.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultCountryService implements CountryService {
    private final JpaCountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    public List<CountryDto> findAll() {
        List<Country> all = countryRepository.findAll();
        return countryMapper.mapToCountryDtoList(all);
    }

    @Override
    public List<Country> findAllByIdIn(List<Integer> countries) {
        return countryRepository.findByIdIn(countries);
    }

    @Override
    public List<CountryDto> findByMovieId(Long movieId) {
        return findByMovieId(movieId);
    }
}
