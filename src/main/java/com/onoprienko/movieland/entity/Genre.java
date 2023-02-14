package com.onoprienko.movieland.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class Genre {
    private Long id;
    private String name;
}
