package com.onoprienko.movieland.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class GenreDto {
    private int id;
    private String name;
}
