package com.onoprienko.movieland.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class CacheGenreDto {
    private Long id;
    private String name;
    private Long movieId;
}