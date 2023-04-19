package com.onoprienko.movieland.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ReviewDto {
    private Long id;
    private String text;
    private UserDto userDto;
}
