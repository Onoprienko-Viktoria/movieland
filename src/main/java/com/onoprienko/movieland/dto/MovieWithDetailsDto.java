package com.onoprienko.movieland.dto;

import com.onoprienko.movieland.common.CurrencyCode;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class MovieWithDetailsDto {
    private Long id;
    private String nameRussian;
    private String nameNative;
    private String yearOfRelease;
    private String description;
    private Double rating;
    private CurrencyCode currencyCode;
    private Double price;
    private String picturePath;
    private List<CountryDto> countries;
    private List<GenreDto> genres;
    private List<ReviewDto> reviews;
}
