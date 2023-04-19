package com.onoprienko.movieland.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserDto {
    private Long id;
    private String nickname;
}
