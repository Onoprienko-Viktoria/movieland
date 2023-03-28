package com.onoprienko.movieland.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class Genre {
    @Id
    @SequenceGenerator(name = "genre_sequence",
            sequenceName = "genre_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "genre_sequence")
    private Long id;
    private String name;
}
