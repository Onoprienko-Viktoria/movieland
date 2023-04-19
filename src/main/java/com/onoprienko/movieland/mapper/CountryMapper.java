package com.onoprienko.movieland.mapper;

import com.onoprienko.movieland.dto.CountryDto;
import com.onoprienko.movieland.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CountryMapper {
    CountryDto mapToCountryDto(Country country);

    List<CountryDto> mapToCountryDtoList(List<Country> countries);

}
