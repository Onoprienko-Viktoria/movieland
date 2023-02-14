package com.onoprienko.movieland.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class Movie {
    private Long id;
    private String nameRussian;
    private String nameNative;
    private Integer yearOfRelease;
    private Double rating;
    private Double price;
    private String picturePath;
}