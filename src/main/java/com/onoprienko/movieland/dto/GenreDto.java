package com.onoprienko.movieland.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GenreDto {
    private int id;
    private String name;
}
