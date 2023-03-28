package com.onoprienko.movieland.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class MovieDto {
    private Long id;
    private String nameRussian;
    private String nameNative;
    private Integer yearOfRelease;
    private Double rating;
    private Double price;
    private String picturePath;
}
