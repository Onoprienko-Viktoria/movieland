package com.onoprienko.movieland.web.controller;

import com.onoprienko.movieland.dto.GenreDto;
import com.onoprienko.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genre/")
@Slf4j
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping()
    public List<GenreDto> findAll() {
        return genreService.findAll();
    }

}
