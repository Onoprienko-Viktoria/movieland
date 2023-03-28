package com.onoprienko.movieland.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Movie {
    @Id
    @SequenceGenerator(name = "movie_sequence",
            sequenceName = "movie_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "movie_sequence")
    private Long id;
    private String nameRussian;
    private String nameNative;
    private Integer yearOfRelease;
    private Double rating;
    private Double price;
    private String picturePath;
    private String description;
    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres = new ArrayList<>();

}