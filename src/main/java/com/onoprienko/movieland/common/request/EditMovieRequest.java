package com.onoprienko.movieland.common.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class EditMovieRequest {
    private Long movieId;
    private String nameRussian;
    private String nameNative;
    private String picturePath;
    private List<Integer> countries;
    private List<Long> genres;
}
