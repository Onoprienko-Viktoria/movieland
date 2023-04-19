package com.onoprienko.movieland.mapper;

import com.onoprienko.movieland.common.request.AddMovieRequest;
import com.onoprienko.movieland.dto.MovieDto;
import com.onoprienko.movieland.dto.MovieWithDetailsDto;
import com.onoprienko.movieland.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovieMapper {
    MovieDto mapToMovieDto(Movie movie);

    List<MovieDto> mapToMovieDtoList(List<Movie> movie);

    Movie mapToMovie(AddMovieRequest request);

    MovieWithDetailsDto mapToMovieWithDetailsDto(Movie movie);
}
