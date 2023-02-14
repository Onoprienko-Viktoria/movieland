package com.onoprienko.movieland.service.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PageRequest {
    private Integer page;
    private Integer capacity;
}
