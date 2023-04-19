package com.onoprienko.movieland.web.controller;

import com.onoprienko.movieland.dto.CountryDto;
import com.onoprienko.movieland.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/country/")
@RequiredArgsConstructor
@Slf4j
public class CountryController {
    private final CountryService countryService;

    @GetMapping()
    public List<CountryDto> findAll() {
        return countryService.findAll();
    }

}
