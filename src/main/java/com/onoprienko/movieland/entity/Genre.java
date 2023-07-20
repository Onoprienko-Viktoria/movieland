package com.onoprienko.movieland.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

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

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "genre_movies",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "movies_id"))
    private Set<Movie> movies = new LinkedHashSet<>();
}
