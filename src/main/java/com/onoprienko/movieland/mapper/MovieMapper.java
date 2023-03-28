package com.onoprienko.movieland.mapper;

import com.onoprienko.movieland.dto.MovieDto;
import com.onoprienko.movieland.entity.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    List<MovieDto> mapToMovieDtoList(List<Movie> movie);
}
