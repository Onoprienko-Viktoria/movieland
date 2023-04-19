package com.onoprienko.movieland.mapper;

import com.onoprienko.movieland.dto.GenreDto;
import com.onoprienko.movieland.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GenreMapper {
    GenreDto mapToGenreDto(Genre genre);

    List<GenreDto> mapToGenreDtoList(List<Genre> genres);
}
